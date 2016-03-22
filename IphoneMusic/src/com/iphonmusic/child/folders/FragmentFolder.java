package com.iphonmusic.child.folders;

import java.io.File;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.iphonmusic.base.fragment.BaseFragment;
import com.iphonmusic.config.Instance;
import com.iphonmusic.config.Rconfig;
import com.iphonmusic.entity.EntityFolder;
import com.iphonmusic.entity.EntitySong;

public class FragmentFolder extends BaseFragment {

	private ListView listView;
	final String MEDIA_PATH = Environment.getExternalStorageDirectory()
			.getPath() + "/";
	public static FragmentFolder newInstance() {
		FragmentFolder fragment = new FragmentFolder();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				Rconfig.getInstance().layout("layout_fragment_folders"),
				container, false);
		listView = (ListView) rootView.findViewById(Rconfig.getInstance().id(
				"listview"));
		ArrayList<EntityFolder> arrayList = getAllFolder(new File(MEDIA_PATH));
		System.out.println(arrayList);
		return rootView;
	}

	private ArrayList<EntityFolder> getAllFolder(File dir) {
		String Pattern = ".mp3";
		ArrayList<EntityFolder> arrayList = new ArrayList<EntityFolder>();
		File listFile[] = dir.listFiles();
		if (listFile != null && listFile.length > 0) {
			for (File file : listFile) {
				if (file != null && !file.isHidden()) {
					if (file.isDirectory()) {
						File[] fileDirectory = file.listFiles();
						for(File file2 : fileDirectory) {
							if(file2.isDirectory()){
								getAllFolder(file2);
							}
							if(file2 != null && !file2.isHidden() && !file2.isDirectory()) {
								if(file2.getName().endsWith(Pattern)){
									EntityFolder folder = new EntityFolder();
									folder.setFolder_name(file.getName());
									folder.setFolder_url(file.getPath());
									arrayList.add(folder);
								}
							}
						}
					}else{
						if(file.getName().endsWith(Pattern)){
							EntityFolder folder = new EntityFolder();
							folder.setFolder_name(file.getName());
							folder.setFolder_url(file.getPath());
							arrayList.add(folder);
						}
					}
				}
			}
		}
		return arrayList;
	}

}
