package com.simicart.core.catalog.product.block;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import com.magestore.simicart.R;
import com.simicart.core.catalog.product.Instance;
import com.simicart.core.catalog.product.entity.CacheBooking;
import com.simicart.core.catalog.product.entity.EntityExcludedday;
import com.simicart.core.catalog.product.entity.InstanceBooking;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by a7med on 28/06/2015.
 */
@SuppressLint("SimpleDateFormat")
public class CalendarView extends LinearLayout {
	// for logging
	private static final String LOGTAG = "Calendar View";

	// how many days to show, defaults to six weeks, 42 days
	private static final int DAYS_COUNT = 42;

	// default date format
	private static final String DATE_FORMAT = "MMM yyyy";

	// date format
	private String dateFormat;

	// current displayed month
	private Calendar currentDate = Calendar.getInstance();

	// event handling
	private EventHandler eventHandler = null;

	// internal components
	private LinearLayout header;
	private ImageView btnPrev;
	private ImageView btnNext;
	private TextView txtDate;
	private GridView grid;

//	private ArrayList<Date> listExcludeDays = new ArrayList<Date>();
	private Date startDate, endDate;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat formatDateToString = new SimpleDateFormat("yyyy-MM-dd");
	private int positionStart, positionEnd;
	private ArrayList<Integer> listPosition = new ArrayList<Integer>();
	private boolean haveExcludayBetween;
	
	private ArrayList<EntityExcludedday> mListExcludeeday = new ArrayList<EntityExcludedday>();

	private int flagClick = 0;
	// seasons' rainbow
	int[] rainbow = new int[] { R.color.summer, R.color.fall, R.color.winter,
			R.color.spring };

	// month-season association (northern hemisphere, sorry australia :)
	int[] monthSeason = new int[] { 2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2 };

	private String mProductId = "";
	private CacheBooking booking;

	public CalendarView(Context context) {
		super(context);
	}

	public CalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initControl(context, attrs);
	}

	public void setListExcludeeday(
			ArrayList<EntityExcludedday> mListExcludeeday) {
		this.mListExcludeeday = mListExcludeeday;
	}
	public void setBooking(CacheBooking booking) {
		this.booking = booking;
	}

	@SuppressLint("NewApi")
	public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initControl(context, attrs);
	}

	public void setProductId(String mProductId) {
		this.mProductId = mProductId;
	}

//	public void setListExcludeDays(ArrayList<String> listExcludeDays) {
//		try {
//			if (listExcludeDays.size() > 0) {
//				for (String date : listExcludeDays) {
//					Date date2 = formatDateToString.parse(date);
//					this.listExcludeDays.add(date2);
//				}
//			}
//		} catch (Exception e) {
//		}
//	}

	public void setStartDate(String startDate) {
		try {
			if(Utils.validateString(startDate)){
				this.startDate = format.parse(startDate);
			}else{
				this.startDate = new Date();
			}
			if (this.startDate.before(new Date())) {
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(new Date());
//				cal.add(Calendar.DAY_OF_YEAR, -1);
//				Date oneDayBefore = cal.getTime();
				this.startDate = new Date();
			}
		} catch (Exception e) {
		}
	}

	public void setEndDate(String endDate) {
		try {
			this.endDate = format.parse(endDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(this.endDate);
			calendar.add(Calendar.DAY_OF_YEAR, +1);
			this.endDate = calendar.getTime();
		} catch (Exception e) {
		}
	}

	/**
	 * Load control xml layout
	 */
	private void initControl(Context context, AttributeSet attrs) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.control_calendar, this);

		loadDateFormat(attrs);
		assignUiElements();
		assignClickHandlers();

		updateCalendar();
	}

	private void loadDateFormat(AttributeSet attrs) {
		TypedArray ta = getContext().obtainStyledAttributes(attrs,
				R.styleable.CalendarView);

		try {
			// try to load provided date format, and fallback to default
			// otherwise
			dateFormat = ta.getString(R.styleable.CalendarView_dateFormat);
			if (dateFormat == null)
				dateFormat = DATE_FORMAT;
		} finally {
			ta.recycle();
		}
	}

	private void assignUiElements() {
		// layout is inflated, assign local variables to components
		header = (LinearLayout) findViewById(R.id.calendar_header);
		header.setVisibility(View.GONE);
		btnPrev = (ImageView) findViewById(R.id.calendar_prev_button);
		btnNext = (ImageView) findViewById(R.id.calendar_next_button);
		txtDate = (TextView) findViewById(R.id.calendar_date_display);
		grid = (GridView) findViewById(R.id.calendar_grid);
	}

	private void assignClickHandlers() {
		// add one month and refresh UI
		btnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				currentDate.add(Calendar.MONTH, 1);
				updateCalendar();
			}
		});

		// subtract one month and refresh UI
		btnPrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				currentDate.add(Calendar.MONTH, -1);
				updateCalendar();
			}
		});

		// long-pressing a day
		grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> view, View cell,
					int position, long id) {
				// handle long-press
				if (eventHandler == null)
					return false;

				eventHandler.onDayLongPress((Date) view
						.getItemAtPosition(position));
				return true;
			}
		});
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				View viewChild = parent.getChildAt(position);
				Date dateChild = (Date) parent.getItemAtPosition(position);
				if (checkDate(dateChild)) {
					if (flagClick == 2) {
						if (position < positionStart || position > positionEnd) {
							for (int i = positionStart; i <= positionEnd; i++) {
								View view2 = parent.getChildAt(i);
								view2.setBackgroundColor(0);
							}
							positionStart = position;
							parent.getChildAt(positionStart)
									.setBackgroundColor(
											Color.parseColor("#007FFF"));
							flagClick = 0;
							positionEnd = 0;
						}
						if (position >= positionStart
								&& position <= positionEnd) {
							for (int i = positionStart; i <= positionEnd; i++) {
								View view2 = parent.getChildAt(i);
								view2.setBackgroundColor(0);
							}
							positionStart = position;
							positionEnd = 0;
							parent.getChildAt(positionStart)
									.setBackgroundColor(
											Color.parseColor("#007FFF"));
							flagClick = 1;

						}
					}
					if (flagClick == 1) {
						if (position < positionStart) {
							parent.getChildAt(positionStart)
									.setBackgroundColor(0);
							positionStart = position;
							flagClick = 0;
							viewChild.setBackgroundColor(Color
									.parseColor("#007FFF"));
						} else if (position == positionStart) {
							viewChild.setBackgroundColor(0);
							positionStart = position;
							flagClick = 0;
						} else {
							flagClick++;
							positionEnd = position;
							for (int i = positionStart; i <= positionEnd; i++) {
								Date date2 = (Date) parent.getItemAtPosition(i);
								if (!checkDate(date2)) {
									haveExcludayBetween = true;
									break;
								}
							}
							if (!haveExcludayBetween) {
								for (int i = positionStart; i <= positionEnd; i++) {
									View view2 = parent.getChildAt(i);
									Date date2 = (Date) parent
											.getItemAtPosition(i);
									view2.setBackgroundColor(Color
											.parseColor("#007FFF"));
								}
							} else {
								flagClick = 1;
								parent.getChildAt(positionStart)
										.setBackgroundColor(0);
								positionStart = position;
								parent.getChildAt(position).setBackgroundColor(
										Color.parseColor("#007FFF"));
								positionEnd = 0;
							}
						}
					}
					if (flagClick == 0) {
						flagClick++;
						viewChild.setBackgroundColor(Color
								.parseColor("#007FFF"));
						positionStart = position;
					}
					haveExcludayBetween = false;
					updateCacheBooking(parent, positionStart, positionEnd,
							flagClick);
				}
			}
		});

	}

	private void updateCacheBooking(AdapterView<?> parent, int positionStart,
			int positionEnd, int flagClick) {
		String dateStart = formatDateToString.format((Date) parent
				.getItemAtPosition(positionStart));
		booking.setDateFrom(dateStart);
		if (positionEnd > positionStart) {
			String dateEnd = formatDateToString.format((Date) parent
					.getItemAtPosition(positionEnd));
			booking.setDateTo(dateEnd);
		} else {
			booking.setDateTo(dateStart);
		}
		booking.setPosition_start(positionStart);
		booking.setPosition_end(positionEnd);
		booking.setFlag_click(flagClick);
		InstanceBooking.getInstance().addBookingToCache(booking);
	}

	private boolean isCheckExitPosition(int postion) {
		if (listPosition.size() > 0) {
			for (int index : listPosition) {
				if (index == postion) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkDate(Date date) {
		if (startDate != null && date.before(startDate)) {
			return false;
		}
		if (endDate != null && date.after(endDate)) {
			return false;
		}
//		if (listExcludeDays.size() > 0) {
//			for (Date date2 : listExcludeDays) {
//				if (formatDateToString.format(date).equals(
//						formatDateToString.format(date2))) {
//					return false;
//				}
//			}
//		}
		return true;
	}

	/**
	 * Display dates correctly in grid
	 */
	public void updateCalendar() {
		updateCalendar(null);
	}

	/**
	 * Display dates correctly in grid
	 */
	public void updateCalendar(HashSet<Date> events) {
		if (currentDate.get(Calendar.MONTH) == Instance.currenDate
				.get(Calendar.MONTH)) {
			if(booking != null){ 
			positionStart = booking.getPosition_start();
			positionEnd = booking.getPosition_end();
			flagClick = booking.getFlag_click();
			}
		}

		ArrayList<Date> cells = new ArrayList<>();
		Calendar calendar = (Calendar) currentDate.clone();

		// determine the cell for current month's beginning
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

		// move calendar backwards to the beginning of the week
		calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

		// fill cells
		while (cells.size() < DAYS_COUNT) {
			cells.add(calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}

		// update grid
		grid.setAdapter(new CalendarAdapter(getContext(), cells, events));

		// update title
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		txtDate.setText(sdf.format(currentDate.getTime()));

		// set header color according to current season
		int month = currentDate.get(Calendar.MONTH);
		int season = monthSeason[month];
		int color = rainbow[season];

		header.setBackgroundColor(getResources().getColor(color));
	}

	private class CalendarAdapter extends ArrayAdapter<Date> {
		// days with events
		private HashSet<Date> eventDays;

		// for view inflation
		private LayoutInflater inflater;

		public CalendarAdapter(Context context, ArrayList<Date> days,
				HashSet<Date> eventDays) {
			super(context, R.layout.control_calendar_day, days);
			this.eventDays = eventDays;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			// day in question
			Date date = getItem(position);
			int day = date.getDate();
			int month = date.getMonth();
			int year = date.getYear();

			// today
			Date today = new Date();

			// inflate item if it does not exist yet
			if (view == null)
				view = inflater.inflate(R.layout.control_calendar_day, parent,
						false);

			// if this day has an event, specify event image
			view.setBackgroundResource(0);
			if (eventDays != null) {
				for (Date eventDate : eventDays) {
					if (eventDate.getDate() == day
							&& eventDate.getMonth() == month
							&& eventDate.getYear() == year) {
						// mark this day for event
						view.setBackgroundResource(R.drawable.reminder);
						break;
					}
				}
			}

			// clear styling
			((TextView) view).setTypeface(null, Typeface.NORMAL);
			((TextView) view).setTextColor(Color.BLACK);
			// set text
			((TextView) view).setText(String.valueOf(date.getDate()));
			view.setBackgroundResource(0);
			if (startDate != null && date.before(startDate)) {
				((TextView) view).setTextColor(getResources().getColor(
						R.color.greyed_out));
			}
			if (endDate != null && date.after(endDate)) {
				((TextView) view).setTextColor(getResources().getColor(
						R.color.greyed_out));
			}
			// if (startDate != null && endDate != null && date.after(startDate)
			// && date.before(endDate)) {
//			if (listExcludeDays.size() > 0) {
//				for (Date dateExclude : listExcludeDays) {
//					if (formatDateToString.format(dateExclude).equals(
//							formatDateToString.format(date))) {
//						((TextView) view).setTextColor(getResources().getColor(
//								R.color.greyed_out));
//					}
//				}
//			}
			// }
			if (currentDate.get(Calendar.MONTH) == Instance.currenDate
					.get(Calendar.MONTH)){
				if (positionStart > 0 && positionEnd > 0) {
					if (position >= positionStart && position <= positionEnd) {
						 view.setBackgroundColor(Color.parseColor("#007FFF"));
					}
				} else if (positionStart > 0) {
					if (position == positionStart) {
						 view.setBackgroundColor(Color.parseColor("#007FFF"));
					}
				}
			}
			return view;
		}
	}

	// update cache booking when user choose date and time, it will save it to
	// cache
	/**
	 * Assign event handler to be passed needed events
	 */
	public void setEventHandler(EventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

	/**
	 * This interface defines what events to be reported to the outside world
	 */
	public interface EventHandler {
		void onDayLongPress(Date date);
	}
}
