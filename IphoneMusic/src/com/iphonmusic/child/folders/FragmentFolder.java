package com.iphonmusic.child.folders;

import java.io.File;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.iphonmusic.base.delegate.ModelDelegate;
import com.iphonmusic.base.fragment.BaseFragment;
import com.iphonmusic.base.network.response.CoreResponse;
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
//		ArrayList<EntityFolder> arrayList = getAllFolder(new File(MEDIA_PATH));
//		System.out.println(arrayList);
		request();
		return rootView;
	}
	
	private void request(){
		MyModel mModel = new MyModel();
		mModel.setModelDelegate(new ModelDelegate() {
			
			@Override
			public void callBack(CoreResponse coreResponse, boolean isSuccess) {
				System.out.println(coreResponse);
				parserData(coreResponse.getData());
			}
		});
		mModel.request();
	}

	private Document mDocument;
	private void parserData(String html){
		mDocument = Jsoup.parse(html);
//		Log.e("HTML return ======================>:", html);
		longInfo(html);
		Elements eSlide = mDocument.select(".slick-track");
//		System.out.println(eSlide);
		Elements tagA = eSlide.select("a");
//		System.out.println(tagA);
	}
	public void longInfo(String str) {
        if(str.length() > 4000) {
            Log.i("",str.substring(0, 4000));
            longInfo(str.substring(4000));
        } else
            Log.i("",str);
    } 
	
	
	private ArrayList<EntityFolder> getAllFolder(File dir) {
		String Pattern = ".mp3";
		ArrayList<EntityFolder> arrayList = new ArrayList<EntityFolder>();
		File listFile[] = dir.listFiles();
		if (listFile != null && listFile.length > 0) {
			for (File file : listFile) {
				if(file.isDirectory()){
					if(checkFileHasMp3(file)){
						EntityFolder folder = new EntityFolder();
						folder.setFolder_name(file.getName());
						folder.setFolder_url(file.getAbsolutePath());
						arrayList.add(folder);
					}else{
						getAllFolder(file);
					}
				}
			}
		}
		return arrayList;
	}
	private boolean checkFileHasMp3(File file){
		File[] array = file.listFiles();
		for(int i=0; i < array.length; i++){
			if(array[i].getName().endsWith(".mp3")){
				return true;
			}
		}
		return false;
	}

}
