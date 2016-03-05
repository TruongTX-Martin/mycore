package com.simicart.core.catalog.product.entity;

import java.util.ArrayList;

public class InstanceBooking {
	
	
	private ArrayList<CacheBooking> mListCacheBooking = new ArrayList<CacheBooking>();
	public static InstanceBooking instance;
	
	
	private String DATE_FROM = "date_from";
	private String DATE_TO = "date_to";
	private String TIME_FROM = "time_from";
	private String TIME_TO = "time_to";
	
	public static InstanceBooking getInstance(){
		if(instance == null){
			instance = new InstanceBooking();
		}
		return instance;
	}
	
	public String getDATE_FROM() {
		return DATE_FROM;
	}
	public String getDATE_TO() {
		return DATE_TO;
	}
	public String getTIME_FROM() {
		return TIME_FROM;
	}
	public String getTIME_TO() {
		return TIME_TO;
	}
	
	public void setListCacheBooking(ArrayList<CacheBooking> listCacheBooking) {
		this.mListCacheBooking = listCacheBooking;
	}
	public ArrayList<CacheBooking> getListCacheBooking() {
		return mListCacheBooking;
	}
	
	//get ObjectCacheBooking from Listcache
	public CacheBooking getCacheBookingFromProductId(String productID) {
		CacheBooking booking = null;
		if(mListCacheBooking.size() > 0){
			for(CacheBooking cacheBooking : mListCacheBooking) {
				if(cacheBooking.getProductId().equals(productID)){
					booking = cacheBooking;
					break;
				}
			}
		}
		return booking;
	}
	
	public void addBookingToCache(CacheBooking booking) {
		CacheBooking cacheBooking = getCacheBookingFromProductId(booking.getProductId());
		if(cacheBooking!= null){
			mListCacheBooking.remove(cacheBooking);
		}
		mListCacheBooking.add(booking);
	}

}
