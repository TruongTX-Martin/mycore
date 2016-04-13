package com.iphonmusic;

import java.io.File;
import java.util.ArrayList;

import com.iphonmusic.adapter.AdapterSongs;
import com.iphonmusic.base.manager.BaseManager;
import com.iphonmusic.config.Instance;
import com.iphonmusic.config.Rconfig;
import com.iphonmusic.entity.EntitySong;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

public class StartActivity extends Activity {

	final String MEDIA_PATH = Environment.getExternalStorageDirectory()
			.getPath() + "/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BaseManager.getIntance().setCurrentActivity(this);
		BaseManager.getIntance().setCurrentContext(
				getApplicationContext());
//		setContentView(R.layout.core_start_activity);
		setContentView(Rconfig.getInstance().layout("core_start_activity"));
		new getSongAsynTask().execute("");
	}
	

	private class getSongAsynTask extends
			AsyncTask<String, Void, ArrayList<EntitySong>> {

		@Override
		protected ArrayList<EntitySong> doInBackground(String... params) {
			File dir = new File(MEDIA_PATH);
			getListSongs(new File(MEDIA_PATH));
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<EntitySong> result) {
			toMainActivity();
		}

	}
	
	private void toMainActivity(){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	public void getListSongs(File dir) {
		String Pattern = ".mp3";
		File listFile[] = dir.listFiles();
		if (listFile != null && listFile.length > 0) {
			for (File file : listFile) {
				if (file != null && !file.isHidden()) {
					if (file.isDirectory()) {
						getListSongs(file);
					} else {
						if (file.getName().endsWith(Pattern)) {
							String fullName = file.getName().substring(0,
									(file.getName().length() - 4));
							if (!fullName.substring(0, 1).contains(".")) {
								EntitySong song = new EntitySong();
								song.setSong_name(Rconfig.getInstance()
										.getSongName(fullName));
								song.setSong_singer(Rconfig.getInstance()
										.getSingerName(fullName));
								song.setSong_url(file.getPath());
								song.setSong_file(file);
								Instance.LISTSONG.add(song);
							}
						}
					}
				}
			}
		}
	}

}
