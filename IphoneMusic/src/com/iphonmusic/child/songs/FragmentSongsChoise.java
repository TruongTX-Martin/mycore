package com.iphonmusic.child.songs;

import java.io.File;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.iphonmusic.adapter.AdapterSongs;
import com.iphonmusic.adapter.AdapterSongsChoise;
import com.iphonmusic.base.fragment.BaseFragment;
import com.iphonmusic.base.manager.BaseManager;
import com.iphonmusic.config.Config;
import com.iphonmusic.config.Instance;
import com.iphonmusic.config.Rconfig;

public class FragmentSongsChoise extends BaseFragment {

	private Context mContext;
	private ListView mListView;

	private RelativeLayout layout_done;
	private ImageView img_done;
	private AdapterSongsChoise mAdapter;

	public static FragmentSongsChoise newInstance() {
		FragmentSongsChoise choise = new FragmentSongsChoise();
		return choise;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				Rconfig.getInstance().layout("layout_fragment_songs_choise"),
				container, false);
		mContext = getActivity();
		mListView = (ListView) rootView.findViewById(Rconfig.getInstance().id(
				"listview"));
		img_done = (ImageView) rootView.findViewById(Rconfig.getInstance().id(
				"img_done"));
		img_done.setColorFilter(Color.parseColor("#000000"));
		layout_done = (RelativeLayout) rootView.findViewById(Rconfig
				.getInstance().id("layout_done"));

		if (mAdapter == null) {
			mAdapter = new AdapterSongsChoise(mContext, Instance.LISTSONG);
		}
		mListView.setAdapter(mAdapter);
		handleEvent();
		return rootView;
	}

	private void handleEvent() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ImageView img = (ImageView) view.findViewById(Rconfig
						.getInstance().id("img_check"));
				img.setImageDrawable(mContext.getResources().getDrawable(
						Rconfig.getInstance().drawable("ic_checked")));
				img.setColorFilter(Color.parseColor("#000000"));
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mAdapter == null) {
			mAdapter = new AdapterSongsChoise(mContext, Instance.LISTSONG);
		}
		mListView.setAdapter(mAdapter);
	}

}
