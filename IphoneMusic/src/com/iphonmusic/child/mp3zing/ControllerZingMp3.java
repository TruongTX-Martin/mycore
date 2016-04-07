package com.iphonmusic.child.mp3zing;

import org.json.JSONArray;
import org.json.JSONException;

import com.iphonmusic.base.delegate.ModelDelegate;
import com.iphonmusic.base.manager.BaseManager;
import com.iphonmusic.base.network.response.CoreResponse;
import com.iphonmusic.config.Constant;
import com.iphonmusic.entity.EntityZingMp3;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Toast;

public class ControllerZingMp3 {

	private DelegateZingMp3 mDelegate;
	private OnKeyListener onKeyListener;

	public void setDelegate(DelegateZingMp3 mDelegate) {
		this.mDelegate = mDelegate;
	}

	public OnKeyListener getOnKeyListenerEditText() {
		return onKeyListener;
	}

	public ControllerZingMp3() {
		initListener();
	}

	void initListener() {
		onKeyListener = new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_SEARCH)
						&& (event.getAction() == KeyEvent.ACTION_DOWN)) {
					String query = mDelegate.getEditTextSearch().getText()
							.toString();
					BaseManager.getIntance().hideKeyBoard();
					if (query.length() > 0) {
						request(query);
					}
					return true;
				}
				return false;
			}
		};
	}

	public void request(String query) {
		mDelegate.updateProgressBar(true);
		ModelZingMp3  mModel = new ModelZingMp3();
		mModel.setModelDelegate(new ModelDelegate() {
			
			@Override
			public void callBack(CoreResponse coreResponse, boolean isSuccess) {
				String result = coreResponse.getData();
				mDelegate.updateView(result);
				mDelegate.updateProgressBar(false);
			}
		});
		if(query.trim().contains(" ")){
			query = query.replace(" ", "+");
			mModel.addParam("k", query);
		}else{
			mModel.addParam("k", query);
		}
		mModel.addParam("h", Constant.SITE_MP3_ZING);
		mModel.request();
	}
	
	private String getQuery(String query){
		return query.replace(" ", "+");
	}

}
