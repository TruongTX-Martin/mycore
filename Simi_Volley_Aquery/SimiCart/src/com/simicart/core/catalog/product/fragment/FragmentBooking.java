package com.simicart.core.catalog.product.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.product.block.CalendarView;
import com.simicart.core.catalog.product.controller.ProductDetailParentController;
import com.simicart.core.catalog.product.entity.CacheBooking;
import com.simicart.core.catalog.product.entity.Product;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;

public class FragmentBooking extends SimiFragment {


	private Context mContext;
	private Product mProduct;
	private ArrayList<String> listExcludeday = new ArrayList<String>();

	private CacheBooking cacheBooking;
	private CalendarView calendar;

	public static FragmentBooking newInstance() {
		FragmentBooking fragment = new FragmentBooking();
		return fragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(
				Rconfig.getInstance().layout("custom_booking_fragment"),
				container, false);
		mContext = SimiManager.getIntance().getCurrentContext();
		listExcludeday = mProduct.getCacheBooking().getList_excludeday();
		HashSet<Date> events = new HashSet<>();
		events.add(new Date());
		listExcludeday.add("2016-02-07");
		listExcludeday.add("2016-02-09");
		listExcludeday.add("2016-02-13");
		calendar = (CalendarView) rootView.findViewById(Rconfig.getInstance().id("calendar_view"));
		calendar.setStartDate("2016-02-04 00:00:00");
		calendar.setEndDate("2016-04-28 00:00:00");
		calendar.setListExcludeDays(listExcludeday);
		calendar.updateCalendar(events);
		handleEvent();
		handleCache();
		return rootView;
	}

	public void setProduct(Product mProduct) {
		this.mProduct = mProduct;
	}


	private void handleCache() {
		if (isCacheBooking()) {
			for (CacheBooking booking : Config.cacheBookings) {
				if (booking.getProductId().equals(mProduct.getId())) {
					cacheBooking = booking;
				}
			}
		} else {
			cacheBooking = new CacheBooking();
			cacheBooking.setProductId(mProduct.getId());
		}
	}

	private boolean isCacheBooking() {
		if (Config.cacheBookings.size() > 0 && mProduct != null) {
			for (CacheBooking booking : Config.cacheBookings) {
				if (booking.getProductId().equals(mProduct.getId())) {
					return true;
				}
			}
		}
		return false;
	}

	private void handleEvent() {
		rootView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});
	}

	private void showDatePicker(TextView textDate, int flag) {
		DialogFragment picker = new DatePickerFragment(textDate, flag);
		picker.show(getFragmentManager(), "datePicker");

	}

	private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {

		}
	};

	private void showTimePicker(TextView view, int flag) {
		DialogFragment newFragment = new TimePickerFragment(view, flag);
		newFragment.show(getFragmentManager(), "timePicker");
	}

	private void updateCacheBooking() {
		if (isCacheBooking()) {
			for (CacheBooking booking : Config.cacheBookings) {
				if (booking.getProductId().equals(mProduct.getId())) {
					Config.cacheBookings.remove(booking);
				}
			}
		}
		Config.cacheBookings.add(cacheBooking);
	}

	public class DatePickerFragment extends DialogFragment implements
			OnDateSetListener {
		private TextView textview;
		private int flag;

		public DatePickerFragment(TextView text, int flag) {
			this.textview = text;
			this.flag = flag;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			DatePickerDialog dialog = new DatePickerDialog(getActivity(), this,
					year, month, day);
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String date_from = "2016-02-04 00:00:00";
			String date_to = "2016-02-28 00:00:00";
			// String date_from =
			// mProduct.getCacheBooking().getBooking_date_from();
			// String date_to = mProduct.getCacheBooking().getBooking_date_to();
			try {
				if (date_from != null) {
					dialog.getDatePicker().setMinDate(
							format.parse(date_from).getTime());
				}
				if (date_to != null) {
					dialog.getDatePicker().setMaxDate(
							format.parse(date_to).getTime());
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return dialog;
		}

		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			String datechoose = year + "-" + month + "-" + day;
			if (listExcludeday.size() > 0) {
				for (String days : listExcludeday) {
					if (days.contains(datechoose)) {
						// show warning and open datePicker again
					}
				}
			} else {
				textview.setText(datechoose);
				if (flag == 1) {
					cacheBooking.setDateFrom(datechoose);
				} else {
					cacheBooking.setDateTo(datechoose);
				}
				updateCacheBooking();
			}
		}

	}

	public class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {
		TextView textView;
		int flag;

		public TimePickerFragment(TextView view, int flag) {
			this.textView = view;
			this.flag = flag;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hour, int minute) {
			String timeChoose = hour + ":" + minute;
			if (flag == 1) {
				cacheBooking.setTimeFrom(timeChoose);
				textView.setText(timeChoose);
			} else {
				cacheBooking.setTimeTo(timeChoose);
				textView.setText(timeChoose);
			}
			updateCacheBooking();
		}

	}
}
