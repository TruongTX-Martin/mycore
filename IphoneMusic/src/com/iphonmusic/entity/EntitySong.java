package com.iphonmusic.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iphonmusic.base.manager.BaseManager;

public class EntitySong {

	private String song_name;
	private String song_url;
	private File song_file;
	private String sone_singer;

	// Contacts table name
	public static final String TABLE_ITEMWISHLIST = "contacts";

	// Contacts Table Columns names
	public static final String KEY_ID = "song_id";
	public static final String KEY_SONG_NAME = "song_name";
	public static final String KEY_SONG_URL = "song_url";
	public static final String KEY_SONG_FILE = "song_file";
	public static final String KEY_SONG_SINGER = "song_singer";

	public EntitySong() {
	}

	public void setSong_singer(String sone_singer) {
		this.sone_singer = sone_singer;
	}

	public String getSong_singer() {
		return sone_singer;
	}

	public void setSong_file(File song_file) {
		this.song_file = song_file;
	}

	public File getSong_file() {
		return song_file;
	}

	public String getSong_name() {
		return song_name;
	}

	public void setSong_name(String song_name) {
		this.song_name = song_name;
	}

	public String getSong_url() {
		return song_url;
	}

	public void setSong_url(String sone_url) {
		this.song_url = sone_url;
	}

	public static void addItemWishList(EntitySong song) {
		try {
			SQLiteDatabase database = BaseManager.getIntance()
					.getDatabaseHandler().getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_SONG_NAME, song.getSong_name());
			values.put(KEY_SONG_URL, song.getSong_url());
			values.put(KEY_SONG_SINGER, song.getSong_singer());
			values.put(KEY_SONG_FILE, song.getSong_file().toString());

			database.insert(TABLE_ITEMWISHLIST, null, values);
			database.close();
			Log.e("ENtitySOng-====================>", "Add success");
		} catch (Exception e) {
			Log.e("ENtitySOng- addItemWishList", e.getMessage());
		}
	}

	public static ArrayList<EntitySong> getAllItemWishList() {
		ArrayList<EntitySong> list = new ArrayList<EntitySong>();
		String query = "SELECT * FROM " + TABLE_ITEMWISHLIST;
		SQLiteDatabase database = BaseManager.getIntance().getDatabaseHandler()
				.getWritableDatabase();
		Cursor cursor = database.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			do {
				EntitySong entity = new EntitySong();
				entity.setSong_name(cursor.getString(1));
				entity.setSong_url(cursor.getString(2));
				entity.setSong_singer(cursor.getString(3));
				entity.setSong_file(new File(cursor.getString(4)));
				list.add(entity);
			} while (cursor.moveToNext());
		}
		return list;
	}

	public static void deleteItemWishList(EntitySong song) {
		SQLiteDatabase db = BaseManager.getIntance().getDatabaseHandler()
				.getWritableDatabase();
		db.delete(TABLE_ITEMWISHLIST, KEY_SONG_NAME + " = ?",
				new String[] { String.valueOf(song.getSong_name()) });
		db.close();
		Log.e("Delete Item Sucess========== >", "Success");
	}
	public static boolean checkExitsSong(EntitySong entitySong) {
		List<EntitySong> list = getAllItemWishList();
		if(list.size() > 0) {
			for (EntitySong song : list) {
				if(song.getSong_url().equals(entitySong.getSong_url())) {
					return true;
				}
			}
		}
		return false;
	}

}
