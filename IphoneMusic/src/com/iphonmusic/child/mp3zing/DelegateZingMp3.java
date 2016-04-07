package com.iphonmusic.child.mp3zing;

import android.widget.EditText;

public interface DelegateZingMp3 {
	
	public EditText getEditTextSearch();
	
	public void updateView(String result);
	public void updateProgressBar(boolean input);

}
