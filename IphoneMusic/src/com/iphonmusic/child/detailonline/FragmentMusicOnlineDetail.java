package com.iphonmusic.child.detailonline;

import java.lang.reflect.Array;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iphonmusic.base.fragment.BaseFragment;
import com.iphonmusic.base.manager.BaseManager;
import com.iphonmusic.config.Rconfig;
import com.iphonmusic.entity.EntityZingMp3;
import com.iphonmusic.style.floatingbutton.FloatingActionButton;
import com.iphonmusic.style.floatingbutton.FloatingActionsMenu;

public class FragmentMusicOnlineDetail extends BaseFragment {
	private View rootView;
	protected FloatingActionsMenu mMultipleActions;
	private Context mContext;

	protected FloatingActionButton more_share;
	protected ArrayList<FloatingActionButton> mListButton;

	private static EntityZingMp3 mCurrentEntity;
	private static ArrayList<EntityZingMp3> mEntitys;

	public static FragmentMusicOnlineDetail newInstance(
			EntityZingMp3 entityZingMp3, ArrayList<EntityZingMp3> array) {
		FragmentMusicOnlineDetail fragmentDetailPlay = new FragmentMusicOnlineDetail();
		mCurrentEntity = entityZingMp3;
		mEntitys = array;
		return fragmentDetailPlay;
	}

	public void setCurrentEntity(EntityZingMp3 mCurrentEntity) {
		this.mCurrentEntity = mCurrentEntity;
	}

	public void setEntitys(ArrayList<EntityZingMp3> mEntitys) {
		this.mEntitys = mEntitys;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		rootView = inflater.inflate(
				Rconfig.getInstance().layout("layout_fragment_detailplay"),
				container, false);
		BlockMusicOnlineDetail blockDetailPlay = new BlockMusicOnlineDetail(
				rootView);

		ControllerMusicOnlineDetail controller = new ControllerMusicOnlineDetail();
		controller.setCurrentEntity(mCurrentEntity);
		controller.setEntitys(mEntitys);
		controller.setDelegate(blockDetailPlay);
		controller.initListener();

		blockDetailPlay.setOnPlayListener(controller.getOnPlayListener());
		blockDetailPlay.setOnNextListener(controller.getOnNextListener());
		blockDetailPlay.setOnPreviousListener(controller
				.getOnPreviousListener());
		// initFloatButton();
		return rootView;
	}

	private void initFloatButton() {
		mMultipleActions = (FloatingActionsMenu) rootView.findViewById(Rconfig
				.getInstance().id("more_plugins_action"));
		mMultipleActions.createButton(mContext, Color.parseColor("#3498DB"),
				Color.parseColor("#3498DB"), Color.parseColor("#FFFFFF"));
		mListButton = new ArrayList<FloatingActionButton>();
		more_share = new FloatingActionButton(mContext);
		more_share.setColorNormal(Color.parseColor("#FFFFFF"));
		more_share.setColorPressed(Color.parseColor("#f4f4f4"));
		more_share.setIcon(Rconfig.getInstance().drawable("ic_play"));
		mListButton.add(more_share);
		for (int i = 0; i < mListButton.size(); i++) {
			mMultipleActions.addButton(mListButton.get(i));
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		BaseManager.getIntance().getControllerBottom().visibleRootView(false);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		BaseManager.getIntance().getControllerBottom().visibleRootView(true);
	}

}
