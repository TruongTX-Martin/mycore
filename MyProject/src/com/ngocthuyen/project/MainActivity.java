package com.ngocthuyen.project;

import java.util.ArrayList;
import java.util.Map;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
	private String mBaseUrl = "https://shining-inferno-1438.firebaseio.com/myproject";
	private Firebase mFirebase;
	private Firebase mFireBaseFolder;
	private ArrayList<EntityFolder> mFolder;
	private AdapterFolder mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn_add = (Button) findViewById(R.id.btn_add);
		mListView = (ListView) findViewById(R.id.listview);
		mContext = this;
		Firebase.setAndroidContext(this);
		mFirebase = new Firebase(mBaseUrl);
		mFireBaseFolder = mFirebase.child("folder");

		// try {
		//
		// Firebase folder = firebase.child("folder");
		// EntityFolder entityFolder = new EntityFolder("1", "XXXX");
		// // folder.setValue(entityFolder);
		// folder.setValue(entityFolder, new Firebase.CompletionListener() {
		//
		// @Override
		// public void onComplete(FirebaseError fireBaseError,
		// Firebase fireBase) {
		// showToast("Add Success");
		// }
		// });
		// firebase.addValueEventListener(new ValueEventListener() {
		//
		// @Override
		// public void onDataChange(DataSnapshot arg0) {
		// showToast("Data Change");
		// }
		//
		// @Override
		// public void onCancelled(FirebaseError arg0) {
		//
		// }
		// });
		// } catch (Exception e) {
		// System.err.println(e.getMessage());
		// }
		handleEvent();
		mFirebase.addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				System.out.println("xxx");
				String key = snapshot.getKey();
				String value = snapshot.getValue().toString();
				System.out.println(key);
				System.out.println(value);
				for (DataSnapshot chidle : snapshot.getChildren()) {
					Map<String, Object> newPost = (Map<String, Object>) chidle.getValue();
					if(newPost.size() > 0){
					}
				}
			}

			@Override
			public void onCancelled(FirebaseError filebaseError) {
				showToast("Some error occure while get data");
			}
		});
	}

	private void requestData() {

	}

	void handleEvent() {
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				newFolder();
			}
		});
	}

	void newFolder() {
		final Dialog dialog = new Dialog(mContext);
		dialog.setContentView(R.layout.layout_new_folder);
		dialog.setTitle("Input Name Folder:");
		final EditText editText = (EditText) dialog
				.findViewById(R.id.edt_folder);
		Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
		Button btnOk = (Button) dialog.findViewById(R.id.btn_ok);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = editText.getText().toString().trim();
				if (name.length() > 0) {
					EntityFolder folder = new EntityFolder(name);
					mFireBaseFolder.push().setValue(folder,
							new Firebase.CompletionListener() {

								@Override
								public void onComplete(FirebaseError arg0,
										Firebase arg1) {
									showToast("Add Success!");
									dialog.dismiss();
								}
							});
				}
			}
		});
		dialog.show();
	}

	void showToast(String toast) {
		Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
	}

}
