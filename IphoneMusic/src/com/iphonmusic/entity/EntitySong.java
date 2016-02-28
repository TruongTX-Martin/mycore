package com.iphonmusic.entity;

import java.io.File;

public class EntitySong {

	private String song_name;
	private String song_url;
	private File song_file;
	private String sone_singer;

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

}
