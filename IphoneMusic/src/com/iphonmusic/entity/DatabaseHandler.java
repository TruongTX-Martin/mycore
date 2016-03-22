package com.iphonmusic.entity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// Database Name
	private static final String DATABASE_NAME = "iphonemusic";
	// Database Version
	private static final int DATABASE_VERSION = 1;

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE "
				+ EntitySong.TABLE_ITEMWISHLIST + "(" + EntitySong.KEY_ID
				+ " INTEGER PRIMARY KEY," + EntitySong.KEY_SONG_NAME + " TEXT,"
				+ EntitySong.KEY_SONG_URL + " TEXT,"
				+ EntitySong.KEY_SONG_SINGER + " TEXT,"
				+ EntitySong.KEY_SONG_FILE + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + EntitySong.TABLE_ITEMWISHLIST);

		// Create tables again
		onCreate(db);
	}

}
