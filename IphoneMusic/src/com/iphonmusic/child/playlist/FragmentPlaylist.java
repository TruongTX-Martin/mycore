package com.iphonmusic.child.playlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iphonmusic.base.fragment.BaseFragment;
import com.iphonmusic.config.Rconfig;

public class FragmentPlaylist extends BaseFragment{
	
	
	private TextView txt_demo;
	
	public static FragmentPlaylist newInstance() {
		FragmentPlaylist fragment = new FragmentPlaylist();
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				Rconfig.getInstance().layout("layout_fragment_playlist"),
				container, false);
		txt_demo = (TextView) rootView.findViewById(Rconfig.getInstance().id("txt_home"));
		return rootView;
	}

}
