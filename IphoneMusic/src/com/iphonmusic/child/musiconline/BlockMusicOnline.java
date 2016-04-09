package com.iphonmusic.child.musiconline;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.iphonmusic.R;
import com.iphonmusic.adapter.AdapterZingMp3;
import com.iphonmusic.base.manager.BaseManager;
import com.iphonmusic.child.detailonline.FragmentMusicOnlineDetail;
import com.iphonmusic.config.Constant;
import com.iphonmusic.config.Rconfig;
import com.iphonmusic.entity.EntityZingMp3;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BlockMusicOnline implements DelegateMusicOnline {

	private View mRootView;
	private Context mContext;

	private ListView mListView;
	private TextView mTextMessage;
	private EditText mEdtSearch;
	private ProgressBar progressBar;

	private ArrayList<EntityZingMp3> mZingMp3 = new ArrayList<EntityZingMp3>();
	private String mSiteName;
	private ImageView img_banner;

	public void setOnKeyEdittext(OnKeyListener listener) {
		mEdtSearch.setOnKeyListener(listener);
	}

	public void setSiteName(String mSiteName) {
		this.mSiteName = mSiteName;
	}

	public BlockMusicOnline(View view, Context context) {
		this.mRootView = view;
		this.mContext = context;
	}

	public void initView() {
		mListView = (ListView) mRootView.findViewById(Rconfig.getInstance().id(
				"listview"));
		mTextMessage = (TextView) mRootView.findViewById(Rconfig.getInstance()
				.id("txt_message"));
		mEdtSearch = (EditText) mRootView.findViewById(Rconfig.getInstance()
				.id("edt_search"));
		progressBar = (ProgressBar) mRootView.findViewById(Rconfig
				.getInstance().id("progressbar"));
		updateView();
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				EntityZingMp3 entity = (EntityZingMp3) parent
						.getItemAtPosition(position);
				FragmentMusicOnlineDetail detail = FragmentMusicOnlineDetail
						.newInstance(entity, mZingMp3);
				BaseManager.getIntance().replaceFragment(detail);
			}
		});
		img_banner = (ImageView) mRootView.findViewById(Rconfig.getInstance()
				.id("img_banner"));
		if (mSiteName.equals(Constant.ITEM_MP3_ZING)) {
			img_banner.setImageResource(R.drawable.ic_banner_zingmp3);
		}
		if (mSiteName.equals(Constant.ITEM_NHACUATUI)) {
			img_banner.setImageResource(R.drawable.ic_banner_nhaccuattui);
		}
		if (mSiteName.equals(Constant.ITEM_KEENG)) {
			img_banner.setImageResource(R.drawable.ic_banner_keengs);
		}

	}

	private void updateView() {
		if (mZingMp3.size() > 0) {
			mListView.setVisibility(View.VISIBLE);
			mTextMessage.setVisibility(View.GONE);
			AdapterZingMp3 adapter = new AdapterZingMp3(mContext, mZingMp3);
			mListView.setAdapter(adapter);
		} else {
			mListView.setVisibility(View.GONE);
			mTextMessage.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public EditText getEditTextSearch() {
		return mEdtSearch;
	}

	@Override
	public void updateView(String result) {
		mZingMp3.clear();
		try {
			if (result != null) {
				JSONArray array = new JSONArray(result);
				if (array.length() > 0) {
					for (int i = 0; i < array.length(); i++) {
						JSONObject object = array.getJSONObject(i);
						EntityZingMp3 entity = new EntityZingMp3();
						entity.setJSON(object);
						mZingMp3.add(entity);
					}
					updateView();
				} else {
					updateView();
				}
			}
		} catch (Exception e) {
			Log.e("Block Zing Mp3:", e.getMessage());
		}
	}

	@Override
	public void updateProgressBar(boolean input) {
		if (input) {
			progressBar.setVisibility(View.VISIBLE);
		} else {
			progressBar.setVisibility(View.GONE);
		}
	}

}
