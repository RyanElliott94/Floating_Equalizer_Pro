/*
 * Decompiled with CFR 0_58.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.media.MediaPlayer
 *  android.media.MediaPlayer$OnCompletionListener
 *  android.os.Bundle
 *  android.preference.PreferenceManager
 *  android.support.v4.app.Fragment
 *  android.support.v4.app.FragmentActivity
 *  android.support.v4.app.FragmentManager
 *  android.support.v4.view.ViewPager
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.TabHost
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 */
package com.simplistic.floatingequalizerpro;

import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.simplistic.floatingequalizerpro.model.EqualizerApi;
import com.simplistic.floatingequalizerpro.model.Profile;
import com.simplistic.floatingequalizerpro.model.lib.DbHelper;
import com.simplistic.floatingequalizerpro.service.Floating;
import com.simplistic.floatingequalizerpro.ui.EffectsFragment;
import com.simplistic.floatingequalizerpro.ui.EqualizerFragment;
import com.simplistic.floatingequalizerpro.ui.ProfileFragment;
import com.simplistic.floatingequalizerpro.ui.lib.EqualizerProfile;
import com.simplistic.floatingequalizerpro.ui.lib.TabPagerAdapter;

import java.util.List;


public class MainActivity extends FragmentActivity implements
		EqualizerProfile.EqualizerProfileHandler {
	
	protected static String EQUALIZER_TAB_TAG;
	private static String PROFILE_TAB_TAG;
	private static String EFFECT_TAB_TAG;
	protected EffectsFragment mEffect;
	protected EqualizerFragment mEqualizer;
	protected ProfileFragment mProfile;
	protected TabHost mTabHost;
	protected TabPagerAdapter mTabPagerAdapter;
	protected ViewPager mViewPager;

	static {
		MainActivity.EQUALIZER_TAB_TAG = "equalizer";
		MainActivity.PROFILE_TAB_TAG = "profile";
		MainActivity.EFFECT_TAB_TAG = "effect";
	}



	protected Fragment instantiateFragment(Class<?> class_, Bundle bundle) {
		return Fragment.instantiate((this), (class_.getName()), (bundle));
	}

	@Override
	public void onBeforeSelectProfile() {
	}

	
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.setContentView(R.layout.main);

		Toolbar toolbar = findViewById(R.id.toolBar);
		setActionBar(toolbar);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);

		this.mViewPager = findViewById(R.id.pager);
		this.mTabHost = findViewById(android.R.id.tabhost);
		this.mTabHost.setup();
		this.mTabPagerAdapter = new TabPagerAdapter((this), this.mTabHost,
				this.mViewPager);
		if (bundle != null) {
			this.mEqualizer = (EqualizerFragment) this
					.getSupportFragmentManager().getFragment(bundle,
					MainActivity.EQUALIZER_TAB_TAG);
			mProfile = (ProfileFragment) getSupportFragmentManager().getFragment(bundle, PROFILE_TAB_TAG);
			
		}
		if (this.mEqualizer == null) {
			this.mEqualizer = (EqualizerFragment) this.instantiateFragment(
					EqualizerFragment.class, bundle);
		}
		if (this.mEffect == null) {
			this.mEffect = (EffectsFragment) this.instantiateFragment(
					EffectsFragment.class, bundle);
		}
		if (this.mProfile == null) {
			this.mProfile = (ProfileFragment) this.instantiateFragment(
					ProfileFragment.class, bundle);
		}
		
		mProfile.setOnSelectProfileHanlder(this);
		
		mTabPagerAdapter.addTab(MainActivity.EQUALIZER_TAB_TAG,
				"Equalizer", mEqualizer);
		
		mTabPagerAdapter.addTab(PROFILE_TAB_TAG, "Presets", mProfile);
		
		mTabPagerAdapter.addTab(EFFECT_TAB_TAG, "Extra's", mEffect);
		
		EqualizerApi.init(0);
        new Profile(this).loadSettings();
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(this).edit();
		editor.putBoolean(EqualizerApi.PREF_AUTOSTART, true);
		editor.apply();
		
		closeNotificationIfRunning(Floating.class.getName());
	}

	public void saveBands(){
		new Profile(this).saveSettings();
	}
	
	public void closeCursor(){
		DbHelper.getInstance(this).close();
	}
	
	
	public void closeAll(){
		saveBands();
		EqualizerApi.destroy();
		DbHelper.getInstance(this).closeAdapter();
		closeCursor();
		finish();
	}

	public void Sure() {
		MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder((this));
		builder.setTitle(("Please Choose:"));
		builder.setMessage(("Float it!: This will keep both the EQ and the Floating Icon running\n\nClose EQ: This will close both the EQ and the floating button"));
		builder.setCancelable(true);
		builder.setIcon(R.mipmap.ic_launcher);
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialogInterface2, int dialogInterface) {
			}
		});
		builder.setNeutralButton("Float it!", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialogInterface2, int dialogInterface) {
				Intent intent = new Intent(getApplicationContext(), Floating.class);
				intent.putExtra("advance_eq", "isFromAdvance");
				startService(intent);
				finish();
			}
		});
		builder.setPositiveButton("Close EQ", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialogInterface2, int dialogInterface) {
				closeAll();
			}
		});
		builder.create().show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()){
			case android.R.id.home:
				finish();
				break;
			case R.id.add_profile:
				mProfile.manageProfile(this, null);
				break;
			case R.id.rate:
				try {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.simplistic.floatingequalizerpro")));
				} catch (android.content.ActivityNotFoundException anfe) {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.simplistic.floatingequalizerpro")));
				}
				break;
			case R.id.mini:
				Intent intent = new Intent(getApplicationContext(), Floating.class);
				intent.putExtra("advance_eq", "isFromAdvance");
				startService(intent);
				finish();
				break;
			case R.id.stop:
				finish();
				break;
		}
		return super.onOptionsItemSelected(menuItem);
	}

	@Override
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		try {
			this.getSupportFragmentManager().putFragment(bundle,
					EQUALIZER_TAB_TAG, mEqualizer);
			
			return;
		} catch (Exception e) {
			return;
		}
	}

	@Override
	public void onSelectProfile() {
		try {
			if (mEqualizer.isVisible()) {
				mEqualizer.refetch();
			}
			if (mEffect.isVisible()) {
				mEffect.refetch();
			}
			
			return;
		} catch (Exception e) {
			return;
		}
	}

	@Override
	public void onBackPressed(){
		Sure();
	}

	public boolean closeNotificationIfRunning(String serviceClassName){
		final ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
		final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);
		for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
			if (runningServiceInfo.service.getClassName().equals(serviceClassName) && runningServiceInfo.service != null){
				Intent intent = new Intent(getApplicationContext(), Floating.class);
				stopService(intent);
				return true;
			}
		}
		return false;
	}



}
