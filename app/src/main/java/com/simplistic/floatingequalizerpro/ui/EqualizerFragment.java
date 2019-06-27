/*
 * Decompiled with CFR 0_58.
 *
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.support.v4.app.FragmentActivity
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.widget.CheckBox
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.LinearLayout
 *  android.widget.SeekBar
 *  android.widget.SeekBar$OnSeekBarChangeListener
 *  android.widget.TextView
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Double
 *  java.lang.Integer
 *  java.lang.String
 */
package com.simplistic.floatingequalizerpro.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.simplistic.floatingequalizerpro.R;
import com.simplistic.floatingequalizerpro.model.EqualizerApi;
import com.simplistic.floatingequalizerpro.ui.lib.EqualizerSettings;
import com.simplistic.floatingequalizerpro.ui.widget.VerticalSeekBar;

public class EqualizerFragment extends EqualizerSettings implements
		CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {
	protected LinearLayout mBandsContainer;
	protected Integer mBandsCount;
	protected CheckBox mEqualizerEnabled;
	protected Integer mMaxBandLevel;
	protected Integer mMinBandLevel;

	protected void fetchValues() {
		Boolean boolean_ = EqualizerApi.getEqualizerEnabled();
		this.mEqualizerEnabled
				.setOnCheckedChangeListener((null));
		this.mEqualizerEnabled.setChecked(boolean_.booleanValue());
		this.mEqualizerEnabled.setOnCheckedChangeListener((this));
		for (int i = 0; i < this.mBandsCount; ++i) {
			this.getBandValueByIndex(i)
					.setText(
							(this.getDbValue((EqualizerApi.getBandLevel(i) - this.mMinBandLevel))));
			VerticalSeekBar verticalSeekBar = this.getBandByIndex(i);
			verticalSeekBar
					.setOnSeekBarChangeListener((null));
			verticalSeekBar
					.setProgress((EqualizerApi.getBandLevel(i) - this.mMinBandLevel));
			verticalSeekBar.setEnabled(true);
			verticalSeekBar.setOnSeekBarChangeListener((this));
		}
	}

	protected VerticalSeekBar getBandByIndex(Integer integer) {
		return (VerticalSeekBar) (((LinearLayout) (this.mBandsContainer
				.getChildAt(integer.intValue()))).getChildAt(1));
	}

	protected TextView getBandTitleByIndex(Integer integer) {
		return (TextView) (((LinearLayout) (this.mBandsContainer
				.getChildAt(integer.intValue()))).getChildAt(2));
	}

	protected TextView getBandValueByIndex(Integer integer) {
		return (TextView) (((LinearLayout) (this.mBandsContainer
				.getChildAt(integer.intValue()))).getChildAt(0));
	}

	protected String getDbValue(int n) {
		Double double_ = ((((n + this.mMinBandLevel) / 10)) / 10.0);
		String string = "";
		if (double_ <= 0.0)
			return (string + double_.toString());
		string = "+";
		return (string + double_.toString());
	}

	protected String getFreqValue(int n) {
		String string = "Hz";
		int n2 = (n / 1000);
		if (n2 < 1000)
			return (n2 + string);
		n2 /= 1000;
		string = "Khz";
		return (n2 + string);
	}

	protected void initUi() {
		mEqualizerEnabled = getView().findViewById(R.id.equalizer_checkbox);

		mBandsContainer = getView().findViewById(R.id.equalizer_bands);

		mMaxBandLevel = EqualizerApi.getMaxBandLevelRange();
		mMinBandLevel = EqualizerApi.getMinBandLevelRange();
		mBandsCount = EqualizerApi.getNumberOfBands();
		Integer integer = (mMaxBandLevel - mMinBandLevel);
		for (int i = 0; i < mBandsCount; ++i) {
			View.inflate(getActivity(), R.layout.equalizer_band, mBandsContainer);
		}
		for (int j = 0; j < mBandsCount; ++j) {
			VerticalSeekBar verticalSeekBar = getBandByIndex(j);
			verticalSeekBar.setMax(100);
			verticalSeekBar.setId(j);
		}
		for (int k = 0; k < mBandsCount; ++k) {
			getBandTitleByIndex(k).setText(
					(getFreqValue(EqualizerApi.getBandFreq(k))));
		}

	}

	@Override
	public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
		for (int i = 0; i < this.mBandsCount; ++i) {
			this.getBandByIndex(i).setEnabled(bl);
		}
		EqualizerApi.setEqualizerEnabled(bl);
	}

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
	}

	@Override
	public View onCreateView(LayoutInflater layoutInflater,
							 ViewGroup viewGroup, Bundle bundle) {
		return layoutInflater.inflate(R.layout.equalizer, viewGroup, false);

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int n, boolean bl) {
		((TextView) ((LinearLayout) (seekBar.getParent())).getChildAt(0))
				.setText((this.getDbValue(n)));
		this.updateBandsValue();
	}

	@Override
	public void onResume() {
		super.onResume();
		this.fetchValues();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onViewCreated(View view, Bundle bundle) {
		this.initUi();
		super.onViewCreated(view, bundle);
	}

	@Override
	public void refetch() {
		this.fetchValues();
	}

	protected void updateBandsValue() {
		for (int i = 0; i < this.mBandsCount; ++i) {
			EqualizerApi
					.setBandLevel(
							i,
							(this.getBandByIndex(i).getProgress() + this.mMinBandLevel));
		}
	}
}
