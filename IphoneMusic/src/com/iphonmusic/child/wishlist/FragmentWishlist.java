package com.iphonmusic.child.wishlist;

import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iphonmusic.base.fragment.BaseFragment;
import com.iphonmusic.config.Rconfig;
import com.iphonmusic.entity.EntitySong;

public class FragmentWishlist extends BaseFragment{
	
	
	public static FragmentWishlist newInstance() {
		FragmentWishlist fragment = new FragmentWishlist();
		return fragment;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				Rconfig.getInstance().layout("layout_fragment_wishlist"),
				container, false);
		List<EntitySong> list = EntitySong.getAllItemWishList();
		Log.e("Size====================>", list.size()+"");
		return rootView;
	}
	
	
	
	

}
