package com.iphonmusic.config;

import java.util.Map;

import android.graphics.Color;

public class Config {

	private static Config instance;

	private String mThemeColor = "#3498DB";
	private Map<String, String> mLanguages;

	public static Config getInstance() {
		if (null == instance) {
			instance = new Config();
		}

		return instance;
	}

	public int getColorMain() {
		return Color.parseColor(mThemeColor);
	}



}
