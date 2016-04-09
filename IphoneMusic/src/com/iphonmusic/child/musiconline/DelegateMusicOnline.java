package com.iphonmusic.child.musiconline;

import android.widget.EditText;

public interface DelegateMusicOnline {
	
	public EditText getEditTextSearch();
	
	public void updateView(String result);
	public void updateProgressBar(boolean input);

}
