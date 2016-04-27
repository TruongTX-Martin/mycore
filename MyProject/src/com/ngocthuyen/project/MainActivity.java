package com.ngocthuyen.project;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import android.app.Activity;
import android.app.Dialog;
import android.app.Instrumentation.ActivityResult;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button btn_add;
	private ListView mListView;
	private Context mContext;
	private ArrayList<EntityFolder> mFolder;
	private AdapterFolder mAdapter;
	private String mBaseUrl = "http://192.168.1.151:3000";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn_add = (Button) findViewById(R.id.btn_add);
		mListView = (ListView) findViewById(R.id.listview);
		mContext = this;
		handleEvent();
	}

	void handleEvent() {
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Intent mediaIntent = new Intent(Intent.ACTION_GET_CONTENT);
				// mediaIntent.setType("*/*"); // set mime type as per
				// requirement
				// startActivityForResult(mediaIntent, 100);
				getAllData();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100 && resultCode == RESULT_OK) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String filePath = cursor.getString(columnIndex);
			if (filePath != null && !filePath.equals("")) {
				String name = getNameFileFromPath(filePath);
				uploadFile(filePath, name);
			}
		}

	}

	private String getNameFileFromPath(String filePath) {
		String name = "";
		if (filePath.contains("/")) {
			String[] array = filePath.split("/");
			if (array.length > 0) {
				name = array[array.length - 1];
			}
		}
		return name;
	}
	
	private void uploadFile(String path) {
		File file = new File(path);
		try {
		} catch (Exception e) {
		}
	}
	
	

	private void getAllData() {
		RequestQueue queue = Volley.newRequestQueue(this);
		CustomRequest request = new CustomRequest(Method.GET, mBaseUrl,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String result) {
						System.out.println(result);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						System.out.println(error);
					}
				});
		request.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		queue.add(request);
	}

	private void uploadFile(String filePath, String nameFile) {
		RequestQueue queue = Volley.newRequestQueue(this);
		String url = mBaseUrl + "/" + nameFile;
		CustomRequest request = new CustomRequest(Method.POST, mBaseUrl,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String result) {
						System.out.println(result);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						System.out.println(error);
					}
				});
		request.setParams("-f/files", "@" + filePath);
		queue.add(request);

	}

	void showToast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}

}
