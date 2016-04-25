package com.ngocthuyen.project;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterFolder extends BaseAdapter {

	private Context mContext;
	private ArrayList<EntityFolder> mFolders;

	public AdapterFolder(Context context, ArrayList<EntityFolder> arrayList) {
		this.mContext = context;
		this.mFolders = arrayList;
	}

	@Override
	public int getCount() {
		return mFolders.size();
	}

	@Override
	public Object getItem(int position) {
		return mFolders.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.layout_folder_entity, null);
			holder.folderName = (TextView) convertView
					.findViewById(R.id.txt_folerName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		EntityFolder folder = (EntityFolder) getItem(position);
		holder.folderName.setText(folder.getName());
		return convertView;
	}

	private static class ViewHolder {
		private TextView folderName;
	}

}
