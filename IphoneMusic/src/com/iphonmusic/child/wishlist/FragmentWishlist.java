package com.iphonmusic.child.wishlist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.iphonmusic.adapter.AdapterWishList;
import com.iphonmusic.base.fragment.BaseFragment;
import com.iphonmusic.config.Rconfig;
import com.iphonmusic.entity.EntitySong;

public class FragmentWishlist extends BaseFragment {

	private ListView listView;

	private AdapterWishList mAdapter;

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
		listView = (ListView) rootView.findViewById(Rconfig.getInstance().id(
				"listview_wishlist"));

		ArrayList<EntitySong> list = EntitySong.getAllItemWishList();
		if (mAdapter == null) {
			mAdapter = new AdapterWishList(list, getActivity());
		}
		listView.setAdapter(mAdapter);
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		ArrayList<EntitySong> list = EntitySong.getAllItemWishList();
		mAdapter = new AdapterWishList(list, getActivity());
		listView.setAdapter(mAdapter);
	}
}
