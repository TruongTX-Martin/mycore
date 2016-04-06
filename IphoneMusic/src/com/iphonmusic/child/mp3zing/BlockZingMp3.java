package com.iphonmusic.child.mp3zing;

import java.util.ArrayList;

import com.iphonmusic.config.Rconfig;
import com.iphonmusic.entity.EntityZingMp3;

import android.content.Context;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class BlockZingMp3 implements DelegateZingMp3{
	
	private View mRootView;
	private Context mContext;
	
	private ListView mListView;
	private TextView mTextMessage;
	private EditText mEdtSearch;
	
	private ArrayList<EntityZingMp3> mZingMp3 = new ArrayList<EntityZingMp3>();
	
	
	public void setOnKeyEdittext(OnKeyListener listener){
		mEdtSearch.setOnKeyListener(listener);
	}
	
	public BlockZingMp3(View view,Context context) {
		this.mRootView = view;
		this.mContext = context;
		initView();
	}
	
	private void initView(){
		mListView = (ListView) mRootView.findViewById(Rconfig.getInstance().id("listview"));
		mTextMessage =  (TextView) mRootView.findViewById(Rconfig.getInstance().id("txt_message"));
		mEdtSearch = (EditText) mRootView.findViewById(Rconfig.getInstance().id("edt_search"));
		updateView();
	}
	
	private void updateView(){
		if(mZingMp3.size() > 0){
			mListView.setVisibility(View.VISIBLE);
			mTextMessage.setVisibility(View.GONE);
		}else{
			mListView.setVisibility(View.GONE);
			mTextMessage.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public EditText getEditTextSearch() {
		return mEdtSearch;
	}

}
