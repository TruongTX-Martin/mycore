package com.iphonmusic.child.songs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.iphonmusic.adapter.AdapterSongs;
import com.iphonmusic.base.fragment.BaseFragment;
import com.iphonmusic.base.manager.BaseManager;
import com.iphonmusic.config.Instance;
import com.iphonmusic.config.Rconfig;
import com.iphonmusic.entity.EntitySong;

public class FragmentSongs extends BaseFragment {

	final String MEDIA_PATH = Environment.getExternalStorageDirectory()
			.getPath() + "/";

	private ListView mListView;
	private AdapterSongs mAdapter;
	private Context mContext;
	private EditText edt_search;

	public static FragmentSongs newInstance() {
		FragmentSongs fragment = new FragmentSongs();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				Rconfig.getInstance().layout("layout_fragment_songs"),
				container, false);
		mContext = getActivity();
		mListView = (ListView) rootView.findViewById(Rconfig.getInstance().id(
				"listview"));
		edt_search = (EditText) rootView.findViewById(Rconfig.getInstance().id(
				"edt_search"));
		if (Instance.LISTSONG.size() == 0) {
			getListSongs(new File(MEDIA_PATH));
		} else {
			if (mAdapter == null) {
				mAdapter = new AdapterSongs(mContext, Instance.LISTSONG);
				mListView.setAdapter(mAdapter);
				BaseManager.getIntance().getControllerBottom()
						.updateView(Instance.LISTSONG.get(0));
			}
		}
		handleEvent();
		return rootView;
	}

	private void handleEvent() {

		edt_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				mAdapter.getFilter().filter(s.toString());
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				EntitySong song = (EntitySong) parent
						.getItemAtPosition(position);
				BaseManager.getIntance().setCurrentSong(song);
				BaseManager.getIntance().playMusic();
			}
		});
	}

	@SuppressLint("NewApi")
	public void getListSongs(File dir) {
		String Pattern = ".mp3";
		File listFile[] = dir.listFiles();
		if (listFile != null && listFile.length > 0) {
			for (File file : listFile) {
				if (file != null && !file.isHidden()) {
					if (file.isDirectory()) {
						getListSongs(file);
					} else {
						if (file.getName().endsWith(Pattern)) {
							String fullName = file.getName().substring(0,
									(file.getName().length() - 4));
							if (!fullName.substring(0, 1).contains(".")) {
								EntitySong song = new EntitySong();
								song.setSong_name(Rconfig.getInstance()
										.getSongName(fullName));
								song.setSong_singer(Rconfig.getInstance()
										.getSingerName(fullName));
								song.setSong_url(file.getPath());
								song.setSong_file(file);
								Instance.LISTSONG.add(song);
								if (Instance.LISTSONG.size() == 1) {
									BaseManager.getIntance().setCurrentSong(
											song);
									BaseManager.getIntance().playMusic();
									BaseManager.getIntance().pauseMusic();
								}
							}
						}
					}
				}
			}
		}
		if (mAdapter == null) {
			mAdapter = new AdapterSongs(mContext, Instance.LISTSONG);
			mListView.setAdapter(mAdapter);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mAdapter == null) {
			mAdapter = new AdapterSongs(mContext, Instance.LISTSONG);
			mListView.setAdapter(mAdapter);
		} else {
			mListView.setAdapter(mAdapter);
		}
	}
}
