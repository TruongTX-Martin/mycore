package com.iphonmusic.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.iphonmusic.config.Rconfig;
import com.iphonmusic.entity.EntitySong;

public class AdapterSongsChoise extends BaseAdapter{

	private Context mContext;
	private ArrayList<EntitySong> listSong = new ArrayList<EntitySong>();
	private Drawable icon_appble;
	private Drawable icon_extend;

	public AdapterSongsChoise(Context context, ArrayList<EntitySong> arrayList) {
		this.mContext = context;
		this.listSong = arrayList;
	}

	@Override
	public int getCount() {
		return listSong.size();
	}

	@Override
	public Object getItem(int position) {
		return listSong.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(
					Rconfig.getInstance().layout("layout_item_song_choise"), null);
			holder = new ViewHolder();
			holder.img_icon = (ImageView) convertView.findViewById(Rconfig
					.getInstance().id("img_icon"));
			holder.img_check = (ImageView) convertView.findViewById(Rconfig
					.getInstance().id("img_check"));
			holder.txt_song_name = (TextView) convertView.findViewById(Rconfig
					.getInstance().id("txt_name_song"));
			holder.txt_song_singer = (TextView) convertView
					.findViewById(Rconfig.getInstance().id("txt_singer"));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		EntitySong song = listSong.get(position);
		if (icon_appble == null) {
			icon_appble = mContext.getResources().getDrawable(
					Rconfig.getInstance().drawable("ic_appble"));
		}
		holder.img_icon.setImageDrawable(icon_appble);

		if (icon_extend == null) {
			icon_extend = mContext.getResources().getDrawable(
					Rconfig.getInstance().drawable("ic_unchecked"));
		}
		holder.img_check.setImageDrawable(icon_extend);
		holder.img_check.setColorFilter(Color.parseColor("#000000"));

		holder.txt_song_name.setText(song.getSong_name());
		holder.txt_song_singer.setText(song.getSong_singer());
		return convertView;
	}

	
	private static class ViewHolder {
		ImageView img_icon;
		ImageView img_check;
		TextView txt_song_name;
		TextView txt_song_singer;
	}


}
