package com.iphonmusic.child.feedback;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iphonmusic.base.fragment.BaseFragment;
import com.iphonmusic.base.manager.BaseManager;
import com.iphonmusic.config.Rconfig;
import com.iphonmusic.style.FloatLabel;

public class FragmentFeedBack extends BaseFragment {

	public static FragmentFeedBack newInstance() {
		FragmentFeedBack feedBack = new FragmentFeedBack();
		return feedBack;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(
				Rconfig.getInstance().layout("layout_fragment_feedback"), null,
				false);
		FloatLabel floatLabel = (FloatLabel) view.findViewById(Rconfig
				.getInstance().id("float_label_email"));
		floatLabel.setLabelAnimator(new CustomLabelAnimator());
		return view;
	}

	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		BaseManager.getIntance().getControllerBottom().visibleRootView(false);
		BaseManager.getIntance().getSlideMenuController().closeSlideMenu();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		BaseManager.getIntance().getControllerBottom().visibleRootView(true);
	}
	@SuppressLint("NewApi")
	private static class CustomLabelAnimator implements
			FloatLabel.LabelAnimator {
		/* package */static final float SCALE_X_SHOWN = 1f;
		/* package */static final float SCALE_X_HIDDEN = 2f;
		/* package */static final float SCALE_Y_SHOWN = 1f;
		/* package */static final float SCALE_Y_HIDDEN = 0f;

		@Override
		public void onDisplayLabel(View label) {
			final float shift = label.getWidth() / 2;
			label.setScaleX(SCALE_X_HIDDEN);
			label.setScaleY(SCALE_Y_HIDDEN);
			label.setX(shift);
			label.animate().alpha(1).scaleX(SCALE_X_SHOWN)
					.scaleY(SCALE_Y_SHOWN).x(0f);
		}

		@Override
		public void onHideLabel(View label) {
			final float shift = label.getWidth() / 2;
			label.setScaleX(SCALE_X_SHOWN);
			label.setScaleY(SCALE_Y_SHOWN);
			label.setX(0f);
			label.animate().alpha(0).scaleX(SCALE_X_HIDDEN)
					.scaleY(SCALE_Y_HIDDEN).x(shift);
		}
	}

}
