package com.iphonmusic.config;

import android.content.Context;

import com.iphonmusic.base.manager.BaseManager;

public class Rconfig {
	public Context mContext;
	private String mThemeColor = "#3498DB";
	private static Rconfig instance;
	
	private Rconfig() {
		mContext = BaseManager.getIntance().getCurrentContext();
	}
	
	public static Rconfig getInstance(){
		if (instance == null) {
			instance = new Rconfig();
		}
	return 	instance;
	}
	
	public String getPackageName() {
		return mContext.getPackageName();
	}
	
	public int layout(String name) {
		return mContext.getResources().getIdentifier(name, "layout",
				getPackageName());
	}
	
	public int getId(Context context, String name, String res) {
		return context.getResources()
				.getIdentifier(name, res, getPackageName());
	}
	
	public int id(String name) {
		return mContext.getResources().getIdentifier(name, "id",
				getPackageName());
	}
	public int drawable(String name) {
		return mContext.getResources().getIdentifier(name, "drawable",
				getPackageName());
	}
	public int string(String name) {
		return mContext.getResources().getIdentifier(name, "string",
				getPackageName());
	}
	public int getId(String name, String res) {
		return mContext.getResources().getIdentifier(name, res,
				getPackageName());
	}
	}
