package com.iphonmusic.child.playlist;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.iphonmusic.adapter.AdapterPlayList;
import com.iphonmusic.base.fragment.BaseFragment;
import com.iphonmusic.config.Rconfig;
import com.iphonmusic.entity.EntityPlaylist;

public class FragmentPlaylist extends BaseFragment {

	private ListView listview;

	private ArrayList<EntityPlaylist> mPlayList = new ArrayList<EntityPlaylist>();
	private AdapterPlayList mAdapter;
	private Context mContext;

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
		mContext = getActivity().getApplicationContext();
		listview = (ListView) rootView.findViewById(Rconfig.getInstance().id(
				"listview"));
		EntityPlaylist entityPlaylist = new EntityPlaylist();
		entityPlaylist.setPlaylistName("Default Playlist");
		mPlayList.add(entityPlaylist);
		if (mAdapter == null) {
			mAdapter = new AdapterPlayList(mContext, mPlayList);
			listview.setAdapter(mAdapter);
		}
		return rootView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (mAdapter == null) {
			mAdapter = new AdapterPlayList(mContext, mPlayList);
		}
		listview.setAdapter(mAdapter);
	}

}
