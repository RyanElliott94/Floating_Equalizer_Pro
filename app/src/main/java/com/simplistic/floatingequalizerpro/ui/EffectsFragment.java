/*
 * Decompiled with CFR 0_58.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.CheckBox
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.SeekBar
 *  android.widget.SeekBar$OnSeekBarChangeListener
 *  android.widget.Spinner
 *  java.lang.Object
 */
package com.simplistic.floatingequalizerpro.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.simplistic.floatingequalizerpro.R;
import com.simplistic.floatingequalizerpro.model.EqualizerApi;
import com.simplistic.floatingequalizerpro.ui.lib.EqualizerSettings;

public class EffectsFragment extends EqualizerSettings {
	protected CheckBox mBassBoostCheckbox;
	protected SeekBar mBassBoostSeekBar;
	protected CheckBox mVirtualizerCheckbox;
	protected SeekBar mVirtualizerSeekBar;
	protected CheckBox mLoudCheckbox;
	protected SeekBar mLoudSeekBar;

	protected void initUi() {
		EqualizerApi.getEqualizerEnabled();
		mBassBoostCheckbox = getView()
				.findViewById(R.id.bass_boost_enabled);
		mBassBoostSeekBar = getView()
				.findViewById(R.id.bass_boost_val);
		mVirtualizerCheckbox = getView()
				.findViewById(R.id.virtualization_enabled);
		mVirtualizerSeekBar = getView()
				.findViewById(R.id.virtualization_val);
		mLoudCheckbox = getView()
				.findViewById(R.id.loud_enabled);
		mLoudSeekBar = getView()
				.findViewById(R.id.loud_val);
		initBassBoostUiEvents();
		initVirtualizerUiEvents();
		initLoudUiEvents();
	}

	protected void fetchValues() {
		boolean bl = EqualizerApi.getBassBoostEnabled();
		mBassBoostCheckbox.setChecked(bl);
		mBassBoostSeekBar.setProgress(EqualizerApi.getBassBoostStrength());
		mBassBoostSeekBar.setEnabled(bl);
		boolean bl2 = EqualizerApi.getVirtualizerEnabled();
		mVirtualizerCheckbox.setChecked(bl2);
		mVirtualizerSeekBar.setProgress(EqualizerApi
				.getVirtualizerStrength());
		mVirtualizerSeekBar.setEnabled(bl2);
		boolean bl4 = EqualizerApi.getLoudEnabled();
		mLoudSeekBar.setEnabled(bl4);
		mLoudCheckbox.setChecked(bl4);
	}

	protected void initBassBoostUiEvents() {
		mBassBoostCheckbox
				.setOnCheckedChangeListener((new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton compoundButton,
												 boolean bl) {
						EqualizerApi.setBassBoostEnabled(bl);
						mBassBoostSeekBar.setEnabled(bl);
					}
				}));
		mBassBoostSeekBar
				.setOnSeekBarChangeListener((new SeekBar.OnSeekBarChangeListener() {

					@Override
					public void onProgressChanged(SeekBar seekBar2, int n,
												  boolean seekBar) {
						EqualizerApi.setBassBoostStrength(n);
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
					}
				}));
	}

	protected void initVirtualizerUiEvents() {
		mVirtualizerCheckbox
		.setOnCheckedChangeListener((new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton compoundButton,
					boolean bl) {
				EqualizerApi.setVirtualizerEnabled(bl);
				mVirtualizerSeekBar.setEnabled(bl);
			}
		}));
		mVirtualizerSeekBar
		.setOnSeekBarChangeListener((new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar2, int n,
					boolean seekBar) {
				EqualizerApi.setVirtualizerStrength(n);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		}));
	}

	protected void initLoudUiEvents() {
		mLoudCheckbox
				.setOnCheckedChangeListener((new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton compoundButton,
												 boolean bl) {
						EqualizerApi.setLoudEnabled(bl);
						mLoudSeekBar.setEnabled(bl);
					}
				}));
		mLoudSeekBar
				.setOnSeekBarChangeListener((new SeekBar.OnSeekBarChangeListener() {

					@Override
					public void onProgressChanged(SeekBar seekBar2, int n,
												  boolean seekBar) {
						EqualizerApi.setLoudGain(n);
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
					}
				}));
	}

//	protected void initReverberationSpinner() {
//		mReverberationSpinner = getView().findViewById(R.id.spinner);
//
//		rv = new Reverberation(getContext());
//		mReverberationSpinner.setAdapter(rv.getAdapter());
//		mReverberationSpinner.setOnItemSelectedListener(this);
//
//		mReverbCheckbox
//				.setOnCheckedChangeListener((new CompoundButton.OnCheckedChangeListener() {
//
//					@Override
//					public void onCheckedChanged(CompoundButton compoundButton,
//												 boolean bl) {
//						if(bl){
//							EqualizerApi.setReverbEnabled(true);
//						}else{
//							EqualizerApi.setReverbEnabled(false);
//						}
//					}
//				}));
//	}
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
	}

	@Override
	public View onCreateView(LayoutInflater layoutInflater,
			ViewGroup viewGroup, Bundle bundle) {
		return layoutInflater.inflate(R.layout.effect, viewGroup, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		fetchValues();
	}

	@Override
	public void onViewCreated(View view, Bundle bundle) {
		initUi();
		super.onViewCreated(view, bundle);
	}

	@Override
	public void refetch() {
		fetchValues();
	}

//	@Override
//	public void onItemSelected(AdapterView<?> parent, View view,
//							   int pos, long id) {
//		if(pos == 0){
//			EqualizerApi.setReverbPreset(PresetReverb.PRESET_NONE);
//		}else if(pos == 1){
//			EqualizerApi.setReverbPreset(PresetReverb.PRESET_SMALLROOM);
//		}else if(pos == 2){
//			EqualizerApi.setReverbPreset((short) Reverberation.SL_REVERBPRESET_MEDIUMROOM);
//		}else if(pos == 3){
//			EqualizerApi.setReverbPreset((short) Reverberation.SL_REVERBPRESET_LARGEROOM);
//		}else if(pos == 4){
//			EqualizerApi.setReverbPreset((short) Reverberation.SL_REVERBPRESET_MEDIUMHALL);
//		}else if(pos == 5){
//			EqualizerApi.setReverbPreset(PresetReverb.PRESET_LARGEHALL);
//		}else if(pos == 6){
//			EqualizerApi.setReverbPreset((short) Reverberation.SL_REVERBPRESET_PLATE);
//		}
//	}
}
