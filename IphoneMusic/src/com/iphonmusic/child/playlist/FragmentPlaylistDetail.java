package com.iphonmusic.child.playlist;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.iphonmusic.base.fragment.BaseFragment;
import com.iphonmusic.base.manager.BaseManager;
import com.iphonmusic.child.songs.FragmentSongsChoise;
import com.iphonmusic.config.Rconfig;
import com.iphonmusic.entity.EntitySong;

public class FragmentPlaylistDetail extends BaseFragment {

	private ListView listview;
	private Context mContext;
	private ImageView img_add;
	private TextView txt_message;
	private ArrayList<EntitySong> mEntitySongs = new ArrayList<EntitySong>();

	public static FragmentPlaylistDetail newInstance() {
		FragmentPlaylistDetail detail = new FragmentPlaylistDetail();
		return detail;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater
				.inflate(
						Rconfig.getInstance().layout(
								"layout_fragment_playlist_detail"), container,
						false);
		mContext = getActivity();
		listview = (ListView) rootView.findViewById(Rconfig.getInstance().id(
				"listview"));
		img_add = (ImageView) rootView.findViewById(Rconfig.getInstance().id(
				"img_addnew_detail"));
		txt_message = (TextView) rootView.findViewById(Rconfig.getInstance()
				.id("txt_message"));
		showview();
		handleEvent();
		return rootView;
	}
	void showview(){
		if(mEntitySongs.size() > 0){
			listview.setVisibility(View.VISIBLE);
			txt_message.setVisibility(View.GONE);
			BaseManager.getIntance().getControllerBottom().visibleRootView(true);
		}else{
			listview.setVisibility(View.GONE);
			txt_message.setVisibility(View.VISIBLE);
			BaseManager.getIntance().getControllerBottom().visibleRootView(false);
		}
	}
	
	private void handleEvent() {
		img_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentSongsChoise choise = FragmentSongsChoise.newInstance();
				BaseManager.getIntance().replaceFragment(choise);
			}
		});
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		BaseManager.getIntance().getControllerBottom().visibleRootView(true);
	}

}
