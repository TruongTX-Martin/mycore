package com.iphonmusic.child.detailonline;

import android.view.View;
import android.view.View.OnClickListener;

import com.iphonmusic.base.manager.BaseManager;
import com.iphonmusic.config.Config;
import com.iphonmusic.entity.EntityZingMp3;

public class ControllerMusicOnlineDetail {
	private DelegateMusicOnlineDetail delegate;

	private OnClickListener onPlayListener, onNextListener, onPreviousListener;


	public OnClickListener getOnPlayListener() {
		return onPlayListener;
	}

	public OnClickListener getOnNextListener() {
		return onNextListener;
	}

	public OnClickListener getOnPreviousListener() {
		return onPreviousListener;
	}

	public void setDelegate(DelegateMusicOnlineDetail delegate) {
		this.delegate = delegate;
	}

	public ControllerMusicOnlineDetail() {
		// mPlayer = new MediaPlayer();
	}

	public void initListener() {
		BaseManager.getIntance().playMusicOnline();
		onPlayListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Config.getInstance().getPlayOnline()) {
					BaseManager.getIntance().pauseMusicOnline();
				} else {
					BaseManager.getIntance().continueMusicOnline();
				}
			}
		};
		onNextListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				BaseManager.getIntance().nextSongOnline();

			}
		};
		onPreviousListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				BaseManager.getIntance().previousSongOnline();
			}
		};
	}
	public void updateView(boolean isPlay,EntityZingMp3 zingMp3){
		delegate.updateView(zingMp3, isPlay);
	}
	public void updateTime(){
		delegate.updateTime();
	}

}
