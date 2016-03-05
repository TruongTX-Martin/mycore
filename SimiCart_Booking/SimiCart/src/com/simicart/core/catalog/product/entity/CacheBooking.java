package com.simicart.core.catalog.product.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class CacheBooking {

	private String productId;
	private String dateFrom;
	private String dateTo;
	//hour-minutes-daypart
	private String timeFrom;
	private String timeTo;
	
	
	private String param_timeTo;
	private String param_timeFrom;
	
	private int position_start = 0;
	private int position_end = 0;
	private int flag_click = 0;
	
	private String param_updatePrice_dateFrom;
	private String param_updatePrice_dateTo;

	public CacheBooking() {
	}
	
	
	
	public void setParam_timeFrom(String param_timeFrom) {
		this.param_timeFrom = param_timeFrom;
	}
	public void setParam_timeTo(String param_timeTo) {
		this.param_timeTo = param_timeTo;
	}
	public void setPosition_start(int position_start) {
		this.position_start = position_start;
	}
	public int getPosition_start() {
		return position_start;
	}
	public void setPosition_end(int position_end) {
		this.position_end = position_end;
	}
	public int getPosition_end() {
		return position_end;
	}
	public void setFlag_click(int flag_click) {
		this.flag_click = flag_click;
	}
	public int getFlag_click() {
		return flag_click;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}

	public String getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}
	
	public String getParamTimeTo() {
		if(timeTo != null) {
			
		}
		return param_timeTo;
	}
	public String getParamTimeFrom() {
		return param_timeFrom;
	}
	
	public String getParam_updatePrice_dateFrom() {
		//{"daypart":"pm","hours":"9","minutes":"0"}
		try {
			JSONObject object = new JSONObject(param_timeFrom);
			param_updatePrice_dateFrom = dateFrom + " " + object.getString("hours") +":" + object.getString("minutes") + ":00";  
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return param_updatePrice_dateFrom;
	}
	public String getParam_updatePrice_dateTo() {
		try {
			JSONObject object = new JSONObject(param_timeTo);
			param_updatePrice_dateFrom = dateFrom + " " + object.getString("hours") +":" + object.getString("minutes") + ":00";  
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return param_updatePrice_dateTo;
	}

}
