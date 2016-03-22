package com.iphonmusic.base.manager;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.iphonmusic.base.fragment.BaseFragment;
import com.iphonmusic.config.Config;
import com.iphonmusic.config.Constant;
import com.iphonmusic.config.Instance;
import com.iphonmusic.config.Rconfig;
import com.iphonmusic.entity.DatabaseHandler;
import com.iphonmusic.entity.EntitySong;
import com.iphonmusic.menubottom.ControllerBottom;
import com.iphonmusic.menutop.controller.MenuTopController;
import com.iphonmusic.slidemenu.controller.PhoneSlideMenuController;

public class BaseManager {
	private PhoneSlideMenuController mSlideMenuController;
	private Activity mCurrentActivity;
	private static BaseManager instance;
	private Context mCurrentContext;
	protected MenuTopController mMenuTopController;
	private FragmentManager mManager;
	private ControllerBottom mControllerBottom;
	private EntitySong mCurrentSong;
	private MediaPlayer mPlayer = new MediaPlayer();
	
	private DatabaseHandler databaseHandler;

	public static BaseManager getIntance() {
		if (null == instance) {
			instance = new BaseManager();
		}
		return instance;
	}
	
	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}
	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}
	
	public MediaPlayer getPlayer() {
		return mPlayer;
	}

	public void setCurrentSong(EntitySong mCurrentSong) {
		this.mCurrentSong = mCurrentSong;
		mControllerBottom.updateView(mCurrentSong);

	}

	public EntitySong getCurrentSong() {
		return mCurrentSong;
	}

	public Context getCurrentContext() {
		return mCurrentContext;
	}

	public Activity getCurrentActivity() {
		return mCurrentActivity;
	}

	public void setControllerBottom(ControllerBottom mControllerBottom) {
		this.mControllerBottom = mControllerBottom;
	}

	public ControllerBottom getControllerBottom() {
		return mControllerBottom;
	}

	public void setCurrentActivity(Activity mCurrentActivity) {
		this.mCurrentActivity = mCurrentActivity;
	}

	public void setCurrentContext(Context mCurrentContext) {
		this.mCurrentContext = mCurrentContext;
	}

	public void setChildFragment(FragmentManager childFragment) {
		mManager = childFragment;
	}

	public FragmentManager getChilFragmentManager() {
		return mManager;
	}

	public FragmentManager getManager() {
		return mManager;
	}

	public void setManager(FragmentManager mManager) {
		this.mManager = mManager;
	}

	public void setSlideMenuController(PhoneSlideMenuController controller) {
		mSlideMenuController = controller;
	}

	public void setmMenuTopController(MenuTopController mMenuTopController) {
		this.mMenuTopController = mMenuTopController;
	}

	public void playMusic() {
		if (mPlayer != null) {
			try {
				mPlayer.reset();
				mPlayer.setDataSource(mCurrentSong.getSong_url());
				mPlayer.prepare();
				mPlayer.start();
				mControllerBottom.updateImagePlay(true);
				Config.getInstance().setPlay(true);
				mControllerBottom.setIsFirstPlay(false);
			} catch (Exception e) {
				Log.e("Exception Play Music:", e.getMessage());
			}
		}
	}

	public void continueMusic() {
		if (mPlayer != null && !mPlayer.isPlaying()) {
			mPlayer.start();
			mControllerBottom.updateImagePlay(true);
			Config.getInstance().setPlay(true);
		}
	}

	public void pauseMusic() {
		if (mPlayer != null && mPlayer.isPlaying()) {
			mPlayer.pause();
			mControllerBottom.updateImagePlay(false);
			Config.getInstance().setPlay(false);
		}
	}

	public void nextSong() {
		if (Instance.LISTSONG.size() > 0 && mCurrentSong != null) {
			int current = Instance.LISTSONG.indexOf(mCurrentSong);
			if (current < Instance.LISTSONG.size() - 1) {
				setCurrentSong(Instance.LISTSONG.get(current + 1));
			} else {
				setCurrentSong(Instance.LISTSONG.get(0));
			}
			playMusic();
			
		}
	}
	public void previousSong() {
		if (Instance.LISTSONG.size() > 0 && mCurrentSong != null) {
			int current = Instance.LISTSONG.indexOf(mCurrentSong);
			if (current > 0 ){
				setCurrentSong(Instance.LISTSONG.get(current - 1));
			} 
			playMusic();
		}
	}
	

	public Fragment getCurrentFragment() {
		List<Fragment> fragments = mManager.getFragments();
		for (Fragment fragment : fragments) {
			if (fragment != null && fragment.isVisible()) {
				return fragment;
			}
		}
		return null;
	}

	public void addFragment(BaseFragment fragment) {
		if (null != mManager) {
			String nameFragment = fragment.getClass().getName();
			FragmentTransaction ft = mManager.beginTransaction();
			ft.setCustomAnimations(
					Rconfig.getInstance().getId("in_from_right", "anim"),
					Rconfig.getInstance().getId("out_to_left", "anim"), Rconfig
							.getInstance().getId("in_from_left", "anim"),
					Rconfig.getInstance().getId("out_to_right", "anim"));
			ft.replace(Rconfig.getInstance().id("container"), fragment);
			ft.addToBackStack(nameFragment);
			ft.commit();
			mManager.executePendingTransactions();
		}
	}

	public void replaceFragment(BaseFragment fragment) {
		System.out.println(mManager);
		if (mManager != null) {
			String nameFragment = fragment.getClass().getName();
			boolean isHome = false;
			String screen_name = fragment.getScreenName();
			if (null != screen_name && screen_name.equals(Constant.SCREEN_HOME)) {
				isHome = true;
			}

			mManager.popBackStack(nameFragment,
					FragmentManager.POP_BACK_STACK_INCLUSIVE);
			FragmentTransaction fragmentTransaction = mManager
					.beginTransaction();
			if (!isHome) {
				fragmentTransaction.setCustomAnimations(Rconfig.getInstance()
						.getId("in_from_right", "anim"), Rconfig.getInstance()
						.getId("out_to_left", "anim"), Rconfig.getInstance()
						.getId("in_from_left", "anim"), Rconfig.getInstance()
						.getId("out_to_right", "anim"));
			}
			fragmentTransaction.replace(Rconfig.getInstance().id("container"),
					fragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		}

	}

}
