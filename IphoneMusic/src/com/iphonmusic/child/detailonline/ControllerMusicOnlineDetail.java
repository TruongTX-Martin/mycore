package com.iphonmusic.child.detailonline;

import java.util.ArrayList;

import com.iphonmusic.config.Config;
import com.iphonmusic.entity.EntityZingMp3;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class ControllerMusicOnlineDetail {
	private DelegateMusicOnlineDetail delegate;

	private OnClickListener onPlayListener, onNextListener, onPreviousListener;
	private EntityZingMp3 mCurrentEntity;
	private ArrayList<EntityZingMp3> mEntitys;
	private MediaPlayer mPlayer;

	public void setCurrentEntity(EntityZingMp3 mCurrentEntity) {
		this.mCurrentEntity = mCurrentEntity;
	}

	public void setEntitys(ArrayList<EntityZingMp3> mEntitys) {
		this.mEntitys = mEntitys;
	}

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
		mPlayer = new MediaPlayer();
	}

	public void initListener() {
		onPlayListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				playMusic();
			}
		};
		onNextListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		};
		onPreviousListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		};
	}

	public void playMusic() {
		if (mPlayer != null) {
			try {
				mPlayer.reset();
				mPlayer.setDataSource(mCurrentEntity.getzUrlDownload());
				mPlayer.prepare();
				mPlayer.start();
			} catch (Exception e) {
				Log.e("Exception Play Music:", e.getMessage());
			}
		}
	}

	public void pauseMusic() {
		if (mPlayer != null && mPlayer.isPlaying()) {
			mPlayer.pause();
		}
	}

	public void continuteMusic() {
		if (mPlayer != null && !mPlayer.isPlaying()) {
			mPlayer.start();
		}
	}

	public void nextMusic() {

	}

	public void previousMusic() {

	}
}
