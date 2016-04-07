package com.iphonmusic.child.mp3zing;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iphonmusic.base.fragment.BaseFragment;
import com.iphonmusic.base.manager.BaseManager;
import com.iphonmusic.config.Rconfig;

public class FragmentMp3Zing extends BaseFragment {
	
	private Context mContext;
	
	public static FragmentMp3Zing newInstance() {
		FragmentMp3Zing mp3Zing = new FragmentMp3Zing();
		return mp3Zing;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity().getApplicationContext();
		View rootView = inflater.inflate(
				Rconfig.getInstance().layout("layout_fragment_mp3zing"),
				container, false);
		BlockZingMp3 block = new BlockZingMp3(rootView,mContext);
		
		ControllerZingMp3 mController = new ControllerZingMp3();
		mController.setDelegate(block);
		mController.request("nhac hot");
		
		block.setOnKeyEdittext(mController.getOnKeyListenerEditText());
		
		return rootView;
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
