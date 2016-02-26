package com.iphonmusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iphonmusic.base.fragment.BaseFragment;
import com.iphonmusic.config.Constant;
import com.iphonmusic.config.Rconfig;

public class FragmentHome extends BaseFragment {

	public static FragmentHome newInstance() {
		FragmentHome fragment = new FragmentHome();
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setScreenName(Constant.SCREEN_HOME);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				Rconfig.getInstance().layout("layout_fragment_home"),
				container, false);
		return rootView;
	}

}
