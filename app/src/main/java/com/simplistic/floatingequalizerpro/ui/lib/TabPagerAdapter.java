/*
 * Decompiled with CFR 0_58.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.support.v4.app.Fragment
 *  android.support.v4.app.FragmentActivity
 *  android.support.v4.app.FragmentManager
 *  android.support.v4.app.FragmentPagerAdapter
 *  android.support.v4.view.PagerAdapter
 *  android.support.v4.view.ViewPager
 *  android.support.v4.view.ViewPager$OnPageChangeListener
 *  android.view.View
 *  android.widget.TabHost
 *  android.widget.TabHost$OnTabChangeListener
 *  android.widget.TabHost$TabContentFactory
 *  android.widget.TabHost$TabSpec
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 */
package com.simplistic.floatingequalizerpro.ui.lib;

import android.content.Context;
import android.view.View;
import android.widget.TabHost;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class TabPagerAdapter extends FragmentPagerAdapter implements
		ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener {
	Context mContext;
	FragmentManager mFragmentManager;
	TabHost mTabHost;
	ArrayList<TabInfo> mTabInfo;
	ViewPager mViewPager;

	public TabPagerAdapter(FragmentActivity fragmentActivity, TabHost tabHost,
			ViewPager viewPager) {
		super(fragmentActivity.getSupportFragmentManager());
		this.mTabHost = tabHost;
		this.mViewPager = viewPager;
		this.mFragmentManager = fragmentActivity.getSupportFragmentManager();
		this.mContext = fragmentActivity;
		this.mTabInfo = new ArrayList();
		this.mTabHost.setOnTabChangedListener((this));
		this.mViewPager.setOnPageChangeListener((this));
		this.mViewPager.setAdapter((this));
	}

	public void addTab(String string, String string2, Fragment fragment) {
		TabHost.TabSpec tabSpec = this.mTabHost.newTabSpec(string);
		tabSpec.setIndicator((string2));
		tabSpec.setContent((new TabHost.TabContentFactory() {

			@Override
			public View createTabContent(String string) {
				return new View(TabPagerAdapter.this.mContext);
			}
		}));
		this.mTabHost.addTab(tabSpec);
		this.mTabInfo.add((new TabInfo(string, string2, fragment)));
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return this.mTabInfo.size();
	}

	@Override
	public Fragment getItem(int n) {
		return (this.mTabInfo.get((n))).mFragment;
	}

	@Override
	public void onPageScrollStateChanged(int n) {
	}

	@Override
	public void onPageScrolled(int n22, float n2, int n) {
	}

	@Override
	public void onPageSelected(int n) {
		this.mTabHost.setCurrentTab(n);
	}

	@Override
	public void onTabChanged(String string) {
		int n = this.mTabHost.getCurrentTab();
		this.mViewPager.setCurrentItem(n);
	}

	public static class TabInfo {
		public Fragment mFragment;
		public String mTag;
		public String mTitle;

		public TabInfo(String string, String string2, Fragment fragment) {
			this.mTag = string;
			this.mTitle = string2;
			this.mFragment = fragment;
		}
	}

}
