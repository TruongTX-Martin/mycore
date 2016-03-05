package com.simicart.core.base.network.androidquery.util;

public class AqueryUtil {

	private static AqueryUtil instance;

	public static AqueryUtil getInstance() {
		if (null == instance) {
			instance = new AqueryUtil();
		}

		return instance;
	}

	private String cookie = "";

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getCookie() {
		return cookie;
	}
	
	
}
