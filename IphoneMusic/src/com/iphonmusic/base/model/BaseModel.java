package com.iphonmusic.base.model;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.iphonmusic.base.delegate.ModelDelegate;
import com.iphonmusic.base.delegate.NetworkDelegate;
import com.iphonmusic.base.network.request.BaseRequest;

public class BaseModel {

	private NetworkDelegate mDelegate;
	private BaseRequest baseRequest;
	private ModelDelegate modelDelegate;

	private HashMap<String, Object> mHashMap;

	public BaseModel() {
		mHashMap = new HashMap<String, Object>();
	}

	public void addParam(String tag, String value) {
		mHashMap.put(tag, value);

	}

	public void addParam(String tag, JSONArray value) {
		mHashMap.put(tag, value);
	}

	public void addParam(String tag, JSONObject value) {
		mHashMap.put(tag, value);
	}
	
	public void request(){
		initRequest();
	}
	
	private void initRequest(){
	}

}
