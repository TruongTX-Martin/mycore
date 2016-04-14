package com.iphonmusic.child.folders;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.iphonmusic.base.fragment.BaseFragment;
import com.iphonmusic.config.Rconfig;

public class FragmentFolderDetail extends BaseFragment {

	private Context mContext;
	private LinearLayout mLayoutShuffle;
	private ImageView mImgShuffle;
	private ListView mListView;

	public static FragmentFolderDetail newInstance() {
		FragmentFolderDetail detail = new FragmentFolderDetail();
		return detail;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				Rconfig.getInstance().layout("layout_fragment_folder_detail"),
				container, false);
		mContext = getActivity();
		mLayoutShuffle = (LinearLayout) rootView.findViewById(Rconfig
				.getInstance().id("ll_shuffle"));
		mImgShuffle = (ImageView) rootView.findViewById(Rconfig.getInstance()
				.id("img_shuffle"));
		mListView = (ListView) rootView.findViewById(Rconfig.getInstance().id(
				"listview"));
		return rootView;
	}

}
