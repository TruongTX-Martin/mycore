package com.iphonmusic.child.folders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iphonmusic.base.fragment.BaseFragment;
import com.iphonmusic.config.Rconfig;

public class FragmentFolder extends BaseFragment{
	
	
	public static FragmentFolder newInstance() {
		FragmentFolder fragment = new FragmentFolder();
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				Rconfig.getInstance().layout("layout_fragment_folders"),
				container, false);
		return rootView;
	}

}
