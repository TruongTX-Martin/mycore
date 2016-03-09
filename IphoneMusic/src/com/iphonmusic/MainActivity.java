package com.iphonmusic;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;

import com.iphonmusic.base.manager.BaseManager;
import com.iphonmusic.config.Rconfig;
import com.iphonmusic.menutop.fragment.MenuTopFragment;
import com.iphonmusic.slidemenu.fragment.SlideMenuFragment;

public class MainActivity extends FragmentActivity {

	private SlideMenuFragment mNavigationDrawerFragment;
	public static Activity context;
	public static MainActivity instance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BaseManager.getIntance().setCurrentActivity(this);
		BaseManager.getIntance().setCurrentContext(
				getApplicationContext());
		context = this;
		context = this;
		try {
			setContentView(Rconfig.getInstance().layout(
					"core_main_activity"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		mNavigationDrawerFragment = (SlideMenuFragment) getSupportFragmentManager()
				.findFragmentById(Rconfig.getInstance().id("navigation_drawer"));
		mNavigationDrawerFragment.setup(Rconfig.getInstance().id("navigation_drawer"),
				(DrawerLayout) findViewById(Rconfig.getInstance().id("drawer_layout")));
		
		FragmentTransaction mFragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		MenuTopFragment fragmentMenuTop = MenuTopFragment
				.newInstance(mNavigationDrawerFragment);
		mFragmentTransaction.replace(
				Rconfig.getInstance().id("menu_top"), fragmentMenuTop);
		mFragmentTransaction.commit();

		BaseManager.getIntance()
				.setManager(getSupportFragmentManager());
	}
	
	@Override
	public void onBackPressed() {
		List<Fragment> list = BaseManager.getIntance()
				.getManager().getFragments();
		int count = BaseManager.getIntance().getManager()
				.getBackStackEntryCount();
		if(count == 1){
			AlertDialog.Builder alertboxDowload = new AlertDialog.Builder(
					this);
			alertboxDowload.setTitle("Close Application");
			alertboxDowload.setMessage("Are you sure you want to exit?");
			alertboxDowload.setCancelable(false);
			alertboxDowload.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int id) {
							context = null;
							BaseManager.getIntance().getManager()
									.popBackStack();
							android.os.Process
									.killProcess(android.os.Process
											.myPid());
							finish();
						}
					});
			alertboxDowload.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int id) {
						}
					});
			alertboxDowload.show();
		}else{
			super.onBackPressed();
		}
		
	}
}
