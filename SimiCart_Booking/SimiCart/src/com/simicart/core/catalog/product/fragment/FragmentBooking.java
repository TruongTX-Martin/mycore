package com.simicart.core.catalog.product.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TimePicker.OnTimeChangedListener;

import com.google.android.gms.internal.ca;
import com.google.gson.JsonObject;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.androidquery.util.Constants;
import com.simicart.core.catalog.product.block.CalendarView;
import com.simicart.core.catalog.product.controller.ProductController;
import com.simicart.core.catalog.product.delegate.ProductDelegate;
import com.simicart.core.catalog.product.entity.CacheBooking;
import com.simicart.core.catalog.product.entity.EntityExcludedday;
import com.simicart.core.catalog.product.entity.InstanceBooking;
import com.simicart.core.catalog.product.entity.Product;
import com.simicart.core.catalog.product.model.ModelUpdatePrice;
import com.simicart.core.common.Utils;
import com.simicart.core.common.price.ProductPriceViewV03;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;

public class FragmentBooking extends SimiFragment {

	private Context mContext;
	private Product mProduct;
	private ArrayList<EntityExcludedday> listExcludeday = new ArrayList<EntityExcludedday>();
	private ArrayList<String> mExcludeDayString = new ArrayList<String>();
	private CacheBooking cacheBooking;
	private CalendarView calendar;
	private TextView txt_cancel;
	private TextView txt_done;
	private LinearLayout ll_time_picker;
	private TimePicker time_from;
	private TimePicker time_to;
	private View view;

	public String SHOW_DATE = "date_fromto";
	private String SHOW_TIME = "time_fromto";
	private String SHOW_DATE_TIME = "datetime_fromto";
	SimpleDateFormat formatDateToString = new SimpleDateFormat("yyyy-MM-dd");

	private boolean addCart;

	private ProductDelegate mDelegate;
	private ProductController productController;

	public static FragmentBooking newInstance() {
		FragmentBooking fragment = new FragmentBooking();
		return fragment;
	}

	public void setAddCart(boolean addCart) {
		this.addCart = addCart;
	}

	public void setProductController(ProductController productController) {
		this.productController = productController;
	}

	public void setDelegate(ProductDelegate mDelegate) {
		this.mDelegate = mDelegate;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(
				Rconfig.getInstance().layout("custom_booking_fragment"),
				container, false);
		mContext = SimiManager.getIntance().getCurrentContext();
		txt_cancel = (TextView) rootView.findViewById(Rconfig.getInstance().id(
				"txt_cancel"));
		txt_done = (TextView) rootView.findViewById(Rconfig.getInstance().id(
				"txt_done"));
		ll_time_picker = (LinearLayout) rootView.findViewById(Rconfig
				.getInstance().id("layout_timepicker"));
		time_from = (TimePicker) rootView.findViewById(Rconfig.getInstance()
				.id("picker_from"));
		time_to = (TimePicker) rootView.findViewById(Rconfig.getInstance().id(
				"picker_to"));
		view = rootView.findViewById(Rconfig.getInstance().id("view"));
		String range_type = mProduct.getBooking().getBooking_range_type();
		CacheBooking booking = InstanceBooking.getInstance()
				.getCacheBookingFromProductId(mProduct.getId());
		if (booking != null) {
			cacheBooking = booking;
		} else {
			cacheBooking = new CacheBooking();
			cacheBooking.setProductId(mProduct.getId());
			cacheBooking.setDateFrom(mProduct.getBooking()
					.getBooking_date_from());
			cacheBooking.setDateTo(mProduct.getBooking().getBooking_date_to());
			cacheBooking.setTimeFrom(mProduct.getBooking()
					.getBooking_time_from());
			cacheBooking.setTimeTo(mProduct.getBooking().getBooking_time_to());
			String pr_time_from = getParamTimeStart(mProduct.getBooking()
					.getBooking_time_from());
			cacheBooking.setParam_timeFrom(pr_time_from);
			String pr_time_to = getParamTimeStart(mProduct.getBooking()
					.getBooking_time_to());
			cacheBooking.setParam_timeTo(pr_time_to);
			cacheBooking.setPosition_start(0);
			cacheBooking.setPosition_end(0);
		}

		listExcludeday = mProduct.getBooking().getList_excludeday();
		HashSet<Date> events = new HashSet<>();
		events.add(new Date());
		calendar = (CalendarView) rootView.findViewById(Rconfig.getInstance()
				.id("calendar_view"));
		if (mProduct != null) {
			calendar.setProductId(mProduct.getId());
		}
		
		ArrayList<String> listExcluday = getExcludeddaysFromData(listExcludeday);
		String dateFrom = mProduct.getBooking().getBooking_date_from();
		String dateTo = mProduct.getBooking().getBooking_date_to();
		Log.e("Date From ==================> ", dateFrom);
		Log.e("Date To ====================>", dateTo);
		calendar.setBooking(cacheBooking);
		calendar.setStartDate(mProduct.getBooking().getBooking_date_from());
		calendar.setEndDate(mProduct.getBooking().getBooking_date_to());
		calendar.setListExcludeDays(listExcluday);
		Log.e("TimeFrom===========>", mProduct.getBooking()
				.getBooking_time_from());
		Log.e("TimeTo===========>", mProduct.getBooking().getBooking_time_to());
		calendar.updateCalendar(events);
		handleEvent();
		checkHideShowComponent(range_type);
		handleChooseTime();
		return rootView;
	}

	private ArrayList<String> getExcludeddaysFromData(
			ArrayList<EntityExcludedday> listExcludeday) {
		if (listExcludeday.size() > 0) {
			for (int i = 0; i < listExcludeday.size(); i++) {
				EntityExcludedday entity = listExcludeday.get(i);
				if (entity.getPeriod_recurrence_type() != null) {
					if (entity.getPeriod_recurrence_type().equals("monthly")) {
						ArrayList<String> dateMonthly = getPeriodDate(
								entity.getPeriod_from(),
								entity.getPeriod_to());
						mExcludeDayString.addAll(getDateMonthly(dateMonthly));
					}
					if (entity.getPeriod_recurrence_type().equals("yearly")) {
						ArrayList<String> dateYearly = getPeriodDate(
								entity.getPeriod_from(),
								entity.getPeriod_to());
						mExcludeDayString.addAll(getDateYearly(dateYearly));
					}

				} else {
					if (entity.getPeriod_type().equals("single")
							|| entity.getPeriod_type().equals("period")) {
						ArrayList<String> singsandperiod = getPeriodDate(
								entity.getPeriod_from(),
								entity.getPeriod_to());
						mExcludeDayString.addAll(singsandperiod);
					}
					if (entity.getPeriod_type().equals("recurrent_day")) {
						ArrayList<String> recurrentDays = getPeriodDate(
								entity.getPeriod_from(),
								entity.getPeriod_to());
						//lặp lại ngày
						mExcludeDayString.addAll(getDateMonthly(recurrentDays));
					}
					if (entity.getPeriod_type().equals("recurrent_date")) {
						ArrayList<String> resucrrentDates = getPeriodDate(
								entity.getPeriod_from(),
								entity.getPeriod_to());
						//lặp lại thứ 
						mExcludeDayString.addAll(getDateRepeatDate(resucrrentDates));
					}
				}
			}
		}
		return mExcludeDayString;
	}
	
	private ArrayList<String> getDateRepeatDate(ArrayList<String> dateMonthly) {
		ArrayList<String> list = new ArrayList<String>();
		if(dateMonthly.size() > 0){
			for(String date : dateMonthly) {
				int year = getYearFromDate(date);
				int month = getMonthFromDate(date);
				int day = getDayFromDate(date);
				Calendar calendar = Calendar.getInstance();
				try {
					list.add(date);
					calendar.setTime(formatDateToString.parse(date));
					for (int i =1; i< 20 ; i++) {
						calendar.add(Calendar.DAY_OF_YEAR, +7);
						list.add(formatDateToString.format(calendar.getTime()));
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	private ArrayList<String> getDateMonthly(ArrayList<String> dateMonthly) {
		ArrayList<String> list = new ArrayList<String>();
		if(dateMonthly.size() > 0){
			for(String date : dateMonthly) {
				int year = getYearFromDate(date);
				int month = getMonthFromDate(date);
				int day = getDayFromDate(date);
				for(int i=1; i < 13; i++) {
					list.add(year + "-" + i + "-" + day);
				}
			}
		}
		return list;
	}
	private ArrayList<String> getDateYearly(ArrayList<String> dateMonthly) {
		ArrayList<String> list = new ArrayList<String>();
		if(dateMonthly.size() > 0){
			for(String date : dateMonthly) {
				int year = getYearFromDate(date);
				int month = getMonthFromDate(date);
				int day = getDayFromDate(date);
				for(int i=year; i < year + 5; i++) {
					list.add(i + "-" + month + "-" + day);
				}
			}
		}
		return list;
	}

	private ArrayList<String> getPeriodDate(String periodFrom, String periodTo) {
		ArrayList<String> DATE = new ArrayList<String>();
		if (!Utils.validateString(periodTo) && Utils.validateString(periodFrom)) {
			DATE.add(periodFrom);
		} else if (Utils.validateString(periodFrom) && Utils.validateString(periodTo)) {
			int dayFrom = getDayFromDate(periodFrom);
			int dayTo = getDayFromDate(periodTo);
			if (dayTo > dayFrom +1) {
				DATE.add(periodFrom);
				DATE.add(periodTo);
				try {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(formatDateToString.parse(periodFrom));
					for (int i = 1;; i++) {
						calendar.add(Calendar.DAY_OF_YEAR, +1);
						if (calendar.getTime().before(
								formatDateToString.parse(periodTo))) {
							DATE.add(formatDateToString.format(calendar
									.getTime()));
						} else {
							break;
						}

					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else if (dayTo == dayFrom) {
				DATE.add(periodFrom);
			}
		}
		return DATE;
	}

	private int getYearFromDate(String date) {
		int year = 0;
		if (date != null && date.contains("-")) {
			String[] array = date.split("-");
			year = Integer.parseInt(array[0]);
		}
		return year;
	}

	private int getMonthFromDate(String date) {
		int month = 0;
		if (date != null && date.contains("-")) {
			String[] array = date.split("-");
			month = Integer.parseInt(array[1]);
		}
		return month;
	}

	private int getDayFromDate(String date) {
		int day = 0;
		if (date != null && date.contains("-")) {
			String[] array = date.split("-");
			day = Integer.parseInt(array[2]);
		}
		return day;

	}

	public String getParamTimeStart(String inputTime) {
		String am_pm = "";
		int hour = getCurrentHour(inputTime);
		int minute = getCurrentMinute(inputTime);
		if (hour < 12) {
			if (hour == 0)
				hour = 12;
			am_pm = "am";
		} else {
			if (hour != 12)
				hour -= 12;
			am_pm = "pm";
		}
		JsonObject object = new JsonObject();
		object.addProperty("daypart", am_pm);
		object.addProperty("hours", hour + "");
		object.addProperty("minutes", minute + "");
		return object.toString();
	}

	private void handleChooseTime() {
		// input have pickerfrom and pickerTo
		time_from.setCurrentHour(getCurrentHour(mProduct.getBooking()
				.getBooking_time_from()));
		time_from.setCurrentMinute(getCurrentMinute(mProduct.getBooking()
				.getBooking_time_from()));
		time_from.setOnTimeChangedListener(new OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hour, int minute) {
				String am_pm = getPartDay(hour, minute);
				JsonObject time_from = new JsonObject();
				time_from.addProperty("daypart", am_pm);
				if (hour > 12) {
					time_from.addProperty("hours", hour % 12 + "");
				} else {
					time_from.addProperty("hours", hour + "");
				}
				time_from.addProperty("minutes", minute + "");
				updateCacheBooking(time_from.toString(), null);
			}
		});

		time_to.setCurrentHour(getCurrentHour(mProduct.getBooking()
				.getBooking_time_to()));
		time_to.setCurrentMinute(getCurrentMinute(mProduct.getBooking()
				.getBooking_time_to()));
		time_to.setOnTimeChangedListener(new OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hour, int minute) {
				String am_pm = getPartDay(hour, minute);
				JsonObject time_to = new JsonObject();
				time_to.addProperty("daypart", am_pm);
				if (hour > 12) {
					time_to.addProperty("hours", hour % 12 + "");
				} else {
					time_to.addProperty("hours", hour + "");
				}
				time_to.addProperty("minutes", minute + "");
				updateCacheBooking(null, time_to.toString());
			}
		});
	}

	public String getPartDay(int hour, int minute) {
		String am_pm = "";
		if (hour < 12) {
			if (hour == 0)
				hour = 12;
			am_pm = "am";
		} else {
			if (hour != 12)
				hour -= 12;
			am_pm = "pm";
		}
		return am_pm;
	}

	private void updateCacheBooking(String timeFrom, String timeTo) {
		CacheBooking booking = InstanceBooking.getInstance()
				.getCacheBookingFromProductId(mProduct.getId());
		if (booking != null) {
			if (timeFrom != null) {
				booking.setParam_timeFrom(timeFrom);
			}
			if (timeTo != null) {
				booking.setParam_timeTo(timeTo);
			}
		} else {
			booking = new CacheBooking();
			booking.setProductId(mProduct.getId());
			booking.setDateFrom(mProduct.getBooking().getBooking_date_from());
			booking.setDateTo(mProduct.getBooking().getBooking_date_to());
			if (timeFrom != null) {
				booking.setParam_timeFrom(timeFrom);
			}
			if (timeTo != null) {
				booking.setParam_timeTo(timeTo);
			}
		}
		InstanceBooking.getInstance().addBookingToCache(booking);
	}

	private int getCurrentHour(String time) {
		int currentHour = 0;
		if (Utils.validateString(time)) {
			if (time.contains(":")) {
				String[] array = time.split(":");
				if (array.length > 0) {
					currentHour = Integer.parseInt(array[0].trim());
				}
			}

		}
		return currentHour;
	}

	private int getCurrentMinute(String time) {
		int currentMinute = 0;
		if (Utils.validateString(time)) {
			if (time.contains(":")) {
				String[] array = time.split(":");
				if (array.length > 0) {
					currentMinute = Integer.parseInt(array[1].trim());
				}
			}
		}
		return currentMinute;
	}

	private void checkHideShowComponent(String rangeType) {
		if (Utils.validateString(rangeType)) {
			if (rangeType.equals(SHOW_DATE)) {
				calendar.setVisibility(View.VISIBLE);
				ll_time_picker.setVisibility(View.GONE);
				view.setVisibility(View.GONE);
			}
			if (rangeType.equals(SHOW_DATE_TIME)) {
				calendar.setVisibility(View.VISIBLE);
				ll_time_picker.setVisibility(View.VISIBLE);
				view.setVisibility(View.VISIBLE);
			}
			if (rangeType.equals(SHOW_TIME)) {
				calendar.setVisibility(View.GONE);
				ll_time_picker.setVisibility(View.VISIBLE);
				view.setVisibility(View.GONE);
			}
		}
	}

	public void setProduct(Product mProduct) {
		this.mProduct = mProduct;
	}

	private void handleEvent() {
		rootView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});

		txt_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SimiManager.getIntance().backPreviousFragment();
			}
		});

		txt_done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				requestUpdatePrice();
				SimiManager.getIntance().backPreviousFragment();
			}
		});

	}

	private void requestUpdatePrice() {
		final ModelUpdatePrice mModel = new ModelUpdatePrice();
		mModel.setDelegate(new ModelDelegate() {

			@Override
			public void callBack(String message, boolean isSuccess) {
				Product product = null;
				JSONObject result = mModel.getJSON();
				if (result.has("data")) {
					try {
						JSONArray array = result.getJSONArray("data");
						if (array.length() > 0) {
							product = new Product();
							product.setJSONObject(array.getJSONObject(0));
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				if (product != null) {
					View view = onShowPriceView(product);
					if (null != view) {
						mDelegate.onUpdatePriceView(view);
					}
				}
				if (addCart) {
					// addToCart
					if (productController != null) {
						productController.addtoCart();
					}
				}
			}
		});
		mModel.addParam("product_id", mProduct.getId());
		if (Utils.validateString(DataLocal.getUsername())) {
			mModel.addParam("user_email", DataLocal.getUsername());
		}
		if (Utils.validateString(DataLocal.getPassword())) {
			mModel.addParam("user_password", DataLocal.getPassword());
		}
		CacheBooking booking = InstanceBooking.getInstance()
				.getCacheBookingFromProductId(mProduct.getId());
		if (booking != null) {
			mModel.addParam("date_from",
					booking.getParam_updatePrice_dateFrom());
			mModel.addParam("date_to", booking.getParam_updatePrice_dateTo());
			mModel.request();
		}
	}

	protected View onShowPriceView(Product product) {
		LinearLayout ll_price = new LinearLayout(SimiManager.getIntance()
				.getCurrentContext());
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
		ProductPriceViewV03 mPriceViewZTheme = new ProductPriceViewV03(product);
		View view = mPriceViewZTheme.getViewPrice();
		if (null != view) {
			ll_price.addView(view, params);
		}
		return ll_price;
	}

}
