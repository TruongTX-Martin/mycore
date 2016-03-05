package com.simicart.core.catalog.product.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.simicart.core.config.Constants;

public class Booking {

	private String booking_quantity = "";
	private String booking_range_type = "";
	private String booking_time_from = "";
	private String booking_time_to = "";
	private String booking_date_from = "";
	private String booking_date_to = "";
	private ArrayList<EntityExcludedday> list_excludeday = new ArrayList<EntityExcludedday>();

	private JSONObject jsonObject;

	public Booking() {
	}

	public String getBooking_quantity() {
		booking_quantity = getData(Constants.BOOKING_QUANTITY);
		return booking_quantity;
	}

	public void setBooking_quantity(String booking_quantity) {
		this.booking_quantity = booking_quantity;
	}

	public String getBooking_range_type() {
		booking_range_type = getData(Constants.BOOKING_RANGE_TYPE);
		return booking_range_type;
	}

	public void setBooking_range_type(String booking_range_type) {
		this.booking_range_type = booking_range_type;
	}

	public String getBooking_time_from() {
		booking_time_from = getData(Constants.BOOKING_TIME_FROM);
		return booking_time_from;
	}

	public void setBooking_time_from(String booking_time_from) {
		this.booking_time_from = booking_time_from;
	}

	public String getBooking_time_to() {
		booking_time_to = getData(Constants.BOOKING_TIME_TO);
		return booking_time_to;
	}

	public void setBooking_time_to(String booking_time_to) {
		this.booking_time_to = booking_time_to;
	}

	public String getBooking_date_from() {
		booking_date_from = getData(Constants.BOOKING_DATE_FROM);
		return booking_date_from;
	}

	public void setBooking_date_from(String booking_date_from) {
		this.booking_date_from = booking_date_from;
	}

	public String getBooking_date_to() {
		booking_date_to = getData(Constants.BOOKING_DATE_TO);
		return booking_date_to;
	}

	public void setBooking_date_to(String booking_date_to) {
		this.booking_date_to = booking_date_to;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setList_excludeday(ArrayList<EntityExcludedday> list_excludeday) {
		this.list_excludeday = list_excludeday;
	}

	public ArrayList<EntityExcludedday> getList_excludeday() {
		if (list_excludeday.size() == 0) {
			if (jsonObject.has("booking_excludeddays")) {
				try {
					JSONArray array = jsonObject
							.getJSONArray("booking_excludeddays");
					if (array.length() > 0) {
						for(int i=0; i< array.length() ; i++){
							JSONObject object = array.getJSONObject(i);
							EntityExcludedday excludedday = new EntityExcludedday();
							excludedday.setJsonObject(object);
							list_excludeday.add(excludedday);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return list_excludeday;
	}

	private ArrayList<String> getListDates(String str_date, String end_date) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			ArrayList<Date> dates = new ArrayList<Date>();

			DateFormat formatter;

			formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = (Date) formatter.parse(str_date);
			Date endDate = (Date) formatter.parse(end_date);
			long interval = 24 * 1000 * 60 * 60; // 1 hour in millis
			long endTime = endDate.getTime(); // create your endtime here,
												// possibly
												// using Calendar or Date
			long curTime = startDate.getTime();
			while (curTime <= endTime) {
				dates.add(new Date(curTime));
				curTime += interval;
			}
			for (int i = 0; i < dates.size(); i++) {
				Date lDate = (Date) dates.get(i);
				list.add(formatter.format(lDate));
			}
			return list;
		} catch (Exception e) {
		}
		return list;
	}

	public String getData(String key) {
		if (jsonObject != null && jsonObject.has(key)) {
			try {
				return this.jsonObject.getString(key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
