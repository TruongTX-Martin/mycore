package com.iphonmusic.child.video;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iphonmusic.base.fragment.BaseFragment;
import com.iphonmusic.config.Rconfig;

public class FragmentVideo extends BaseFragment{
	
	
	public static FragmentVideo newInstance() {
		FragmentVideo fragment = new FragmentVideo();
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				Rconfig.getInstance().layout("layout_fragment_videos"),
				container, false);
		return rootView;
	}


}
