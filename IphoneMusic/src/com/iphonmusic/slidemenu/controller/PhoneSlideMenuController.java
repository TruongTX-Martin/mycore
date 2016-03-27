package com.iphonmusic.slidemenu.controller;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.iphonmusic.base.controller.BaseController;
import com.iphonmusic.base.fragment.BaseFragment;
import com.iphonmusic.base.manager.BaseManager;
import com.iphonmusic.child.playlist.FragmentPlaylist;
import com.iphonmusic.child.songs.FragmentSongs;
import com.iphonmusic.child.video.FragmentVideo;
import com.iphonmusic.child.wishlist.FragmentWishlist;
import com.iphonmusic.config.Constant;
import com.iphonmusic.config.Rconfig;
import com.iphonmusic.fragment.FragmentHome;
import com.iphonmusic.slidemenu.delegate.CloseSlideMenuDelegate;
import com.iphonmusic.slidemenu.delegate.SlideMenuDelegate;
import com.iphonmusic.slidemenu.entity.ItemNavigation;

public class PhoneSlideMenuController extends BaseController {

	protected CloseSlideMenuDelegate mCloseDelegate;
	protected Context mContext;
	protected SlideMenuDelegate mDelegate;
	protected OnItemClickListener mListener;
	protected ArrayList<ItemNavigation> mItems = new ArrayList<ItemNavigation>();
	protected int DEFAULT_POSITION = 0;

	public PhoneSlideMenuController(SlideMenuDelegate delegate, Context context) {
		mDelegate = delegate;
		mContext = context;
		mItems = new ArrayList<ItemNavigation>();
	}

	public void setCloseDelegate(CloseSlideMenuDelegate delegate) {
		mCloseDelegate = delegate;
	}

	public OnItemClickListener getListener() {
		return mListener;
	}

	public void closeSlideMenutab() {
		mCloseDelegate.closeSlideMenu();
	}

	public void setListener(OnItemClickListener mListener) {
		this.mListener = mListener;
	}

	@Override
	public void onStart() {
	}

	public void create() {

		mListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				onNaviagte(position);
				// mMenuTopDelegate.controllCartLayout();
			}

		};

		initial();
	}

	private void initial() {
		initDataAdapter();
		mDelegate.setAdapter(mItems);
		onNaviagte(DEFAULT_POSITION);
	}

	public void initDataAdapter() {
		addHome();
		addSong();
		addPlayList();
		addWishList();
		addVideo();
	}

	private void addHome() {
		int index = checkElement(Constant.ITEM_HOME);
		if (index == -1) {
			ItemNavigation item = new ItemNavigation();
			item.setName(Constant.ITEM_HOME);
			int id_icon = Rconfig.getInstance().drawable("ic_menu_home");
			Drawable icon = mContext.getResources().getDrawable(id_icon);
			icon.setColorFilter(Color.parseColor("#ffffff"),
					PorterDuff.Mode.SRC_ATOP);
			item.setDrawble(icon);
			mItems.add(item);
		}
	}

	private void addSong() {
		int index = checkElement(Constant.ITEM_SONGS);
		if (index == -1) {
			ItemNavigation item = new ItemNavigation();
			item.setName(Constant.ITEM_SONGS);
			int id_icon = Rconfig.getInstance().drawable("ic_song");
			Drawable icon = mContext.getResources().getDrawable(id_icon);
			icon.setColorFilter(Color.parseColor("#ffffff"),
					PorterDuff.Mode.SRC_ATOP);
			item.setDrawble(icon);
			mItems.add(item);
		}
	}

	private void addPlayList() {
		int index = checkElement(Constant.ITEM_PLAYLIST);
		if (index == -1) {
			ItemNavigation item = new ItemNavigation();
			item.setName(Constant.ITEM_PLAYLIST);
			int id_icon = Rconfig.getInstance().drawable("ic_playlist");
			Drawable icon = mContext.getResources().getDrawable(id_icon);
			icon.setColorFilter(Color.parseColor("#ffffff"),
					PorterDuff.Mode.SRC_ATOP);
			item.setDrawble(icon);
			mItems.add(item);
		}
	}

	private void addWishList() {
		int index = checkElement(Constant.ITEM_WISHLIST);
		if (index == -1) {
			ItemNavigation item = new ItemNavigation();
			item.setName(Constant.ITEM_WISHLIST);
			int id_icon = Rconfig.getInstance().drawable("ic_wishlist");
			Drawable icon = mContext.getResources().getDrawable(id_icon);
			icon.setColorFilter(Color.parseColor("#ffffff"),
					PorterDuff.Mode.SRC_ATOP);
			item.setDrawble(icon);
			mItems.add(item);
		}
	}

	private void addVideo() {
		int index = checkElement(Constant.ITEM_VIDEOS);
		if (index == -1) {
			ItemNavigation item = new ItemNavigation();
			item.setName(Constant.ITEM_VIDEOS);
			int id_icon = Rconfig.getInstance().drawable("ic_video");
			Drawable icon = mContext.getResources().getDrawable(id_icon);
			icon.setColorFilter(Color.parseColor("#ffffff"),
					PorterDuff.Mode.SRC_ATOP);
			item.setDrawble(icon);
			mItems.add(item);
		}
	}

	public void onNaviagte(int position) {
		ItemNavigation item = mItems.get(position);
		if (null != item) {
			BaseFragment fragment = null;
			fragment = navigateNormal(item);
			if (fragment != null) {
				BaseManager.getIntance().replaceFragment(fragment);
			}
		}
		mDelegate.onSelectedItem(position);
		if (mCloseDelegate != null) {
			mCloseDelegate.closeSlideMenu();
		}
	}
	
	public void closeSlideMenu() {
		if (mCloseDelegate != null) {
			mCloseDelegate.closeSlideMenu();
		}
	}

	public BaseFragment navigateNormal(ItemNavigation item) {
		BaseFragment fragment = null;
		String name = item.getName();
		switch (name) {
		case Constant.ITEM_HOME:
			fragment = FragmentHome.newInstance();
			break;
		case Constant.ITEM_SONGS:
			fragment = FragmentSongs.newInstance();
			break;
		case Constant.ITEM_PLAYLIST:
			fragment = FragmentPlaylist.newInstance();
			break;
		case Constant.ITEM_WISHLIST:
			fragment = FragmentWishlist.newInstance();
			break;
		case Constant.ITEM_VIDEOS:
			fragment = FragmentVideo.newInstance();
			break;
		default:
			break;
		}
		return fragment;
	}

	protected int checkElement(String name) {
		if (null != mItems || mItems.size() > 0) {
			for (int i = 0; i < mItems.size(); i++) {
				ItemNavigation item = mItems.get(i);
				if (item.getName().equals(name)) {
					return i;
				}
			}
			return -1;
		}
		return -1;
	}

	@Override
	public void onResume() {

	}
}
