package com.iphonmusic.child.mp3zing;

import com.iphonmusic.base.delegate.ModelDelegate;
import com.iphonmusic.base.manager.BaseManager;
import com.iphonmusic.base.network.response.CoreResponse;

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
					if (query.length() > 0) {
						request(query);
					}
					return true;
				}
				return false;
			}
		};
	}

	private void request(String query) {
		Toast.makeText(BaseManager.getIntance().getCurrentContext(),
				"Search:" + query, Toast.LENGTH_SHORT).show();
		ModelZingMp3  mModel = new ModelZingMp3();
		mModel.setModelDelegate(new ModelDelegate() {
			
			@Override
			public void callBack(CoreResponse coreResponse, boolean isSuccess) {
				
			}
		});
		mModel.request();
	}

}
