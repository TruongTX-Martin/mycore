package com.simicart.core.catalog.product.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class EntityExcludedday {

	private String product_id ="";
	private String period_type = "";
	private String period_from = "";
	private String period_to = "";
	private String period_recurrence_type = "";
	
	
	private String PRODUCT_ID ="product_id";
	private String PERIOD_TYPE = "period_type";
	private String PERIOD_FROM = "period_from";
	private String PERIOD_TO = "period_to";
	private String PERIOD_RECURRENCE_TYPE = "period_recurrence_type";
	
	private JSONObject mJSON = new JSONObject();
	
	
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_id() {
		product_id = getData(PRODUCT_ID);
		return product_id;
	}
	
	public void setJsonObject(JSONObject object) {
		this.mJSON = object;
	}
	public JSONObject getJsonObject() {
		return mJSON;
	}

	public String getPeriod_type() {
		period_type = getData(PERIOD_TYPE);
		return period_type;
	}

	public void setPeriod_type(String period_type) {
		this.period_type = period_type;
	}

	public String getPeriod_from() {
		period_from = getData(PERIOD_FROM);
		return period_from;
	}

	public void setPeriod_from(String period_from) {
		this.period_from = period_from;
	}

	public String getPeriod_to() {
		period_to = getData(PERIOD_TO);
		return period_to;
	}

	public void setPeriod_to(String period_to) {
		this.period_to = period_to;
	}

	public String getPeriod_recurrence_type() {
		period_recurrence_type = getData(PERIOD_RECURRENCE_TYPE);
		return period_recurrence_type;
	}

	public void setPeriod_recurrence_type(String period_recurrence_type) {
		this.period_recurrence_type = period_recurrence_type;
	}
	
	public String getData(String key) {
		if (mJSON != null && mJSON.has(key)) {
			try {
				return this.mJSON.getString(key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
