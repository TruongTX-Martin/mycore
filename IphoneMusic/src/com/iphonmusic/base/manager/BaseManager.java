package com.iphonmusic.base.manager;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.iphonmusic.base.fragment.BaseFragment;
import com.iphonmusic.config.Constant;
import com.iphonmusic.config.Rconfig;
import com.iphonmusic.menutop.controller.MenuTopController;
import com.iphonmusic.slidemenu.controller.PhoneSlideMenuController;

public class BaseManager {
	private PhoneSlideMenuController mSlideMenuController;
	private Activity mCurrentActivity;
	private static BaseManager instance;
	private Context mCurrentContext;
	protected MenuTopController mMenuTopController;
	private FragmentManager mManager;

	public Context getCurrentContext() {
		return mCurrentContext;
	}

	public Activity getCurrentActivity() {
		return mCurrentActivity;
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

	public static BaseManager getIntance() {
		if (null == instance) {
			instance = new BaseManager();
		}
		return instance;
	}

	public void setSlideMenuController(PhoneSlideMenuController controller) {
		mSlideMenuController = controller;
	}

	public void setmMenuTopController(MenuTopController mMenuTopController) {
		this.mMenuTopController = mMenuTopController;
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
					Rconfig.getInstance()
							.getId("in_from_right", "anim"),
					Rconfig.getInstance().getId("out_to_left", "anim"),
					Rconfig.getInstance().getId("in_from_left", "anim"),
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
				fragmentTransaction.setCustomAnimations(
						Rconfig.getInstance().getId("in_from_right",
								"anim"),
						Rconfig.getInstance().getId("out_to_left",
								"anim"),
						Rconfig.getInstance().getId("in_from_left",
								"anim"),
						Rconfig.getInstance().getId("out_to_right",
								"anim"));
			}
			fragmentTransaction.replace(
					Rconfig.getInstance().id("container"), fragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		}

	}

}
