package com.simplistic.floatingequalizerpro.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.simplistic.floatingequalizerpro.R;
import com.simplistic.floatingequalizerpro.model.EqualizerApi;
import com.simplistic.floatingequalizerpro.ui.lib.EqualizerSettings;
import com.simplistic.floatingequalizerpro.ui.widget.VerticalSeekBar;

public class FloatingFragment extends EqualizerSettings implements
CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {
	protected LinearLayout mBandsContainer;
	protected Integer mBandsCount;
	protected CheckBox mEqualizerEnabled;
	protected Integer mMaxBandLevel;
	protected Integer mMinBandLevel;
	private Button min;
	private LinearLayout removeLayout;

	protected void fetchValues() {
		Boolean boolean_ = EqualizerApi.getEqualizerEnabled();
		mEqualizerEnabled
		.setOnCheckedChangeListener((null));
		mEqualizerEnabled.setChecked(boolean_.booleanValue());
		mEqualizerEnabled.setOnCheckedChangeListener((this));
		for (int i = 0; i < mBandsCount; ++i) {
			getBandValueByIndex(i)
			.setText(
					(getDbValue((EqualizerApi.getBandLevel(i) - mMinBandLevel))));
			VerticalSeekBar verticalSeekBar = getBandByIndex(i);
			verticalSeekBar
			.setOnSeekBarChangeListener((null));
			verticalSeekBar
			.setProgress((EqualizerApi.getBandLevel(i) - mMinBandLevel));
			verticalSeekBar.setEnabled(boolean_.booleanValue());
			verticalSeekBar.setOnSeekBarChangeListener((this));
		}
	}

	protected VerticalSeekBar getBandByIndex(Integer integer) {
		return (VerticalSeekBar) (((LinearLayout) (mBandsContainer
				.getChildAt(integer.intValue()))).getChildAt(1));
	}

	protected TextView getBandTitleByIndex(Integer integer) {
		return (TextView) (((LinearLayout) (mBandsContainer
				.getChildAt(integer.intValue()))).getChildAt(2));
	}

	protected TextView getBandValueByIndex(Integer integer) {
		return (TextView) (((LinearLayout) (mBandsContainer
				.getChildAt(integer.intValue()))).getChildAt(0));
	}

	protected String getDbValue(int n) {
		Double double_ = ((((n + mMinBandLevel) / 10)) / 10.0);
		String string = Messages.getString("FloatingFragment.0"); //$NON-NLS-1$
		if (double_ <= 0.0)
			return (string + double_.toString());
		string = Messages.getString("FloatingFragment.1"); //$NON-NLS-1$
		return (string + double_.toString());
	}

	protected String getFreqValue(int n) {
		String string = Messages.getString("FloatingFragment.2"); //$NON-NLS-1$
		int n2 = (n / 1000);
		if (n2 < 1000)
			return (n2 + string);
		n2 /= 1000;
		string = Messages.getString("FloatingFragment.3"); //$NON-NLS-1$
		return (n2 + string);
	}

	protected void initUi() {
		mEqualizerEnabled = getView()
				.findViewById(2131165191);
		mBandsContainer = getView()
				.findViewById(2131165192);

		mMaxBandLevel = EqualizerApi.getMaxBandLevelRange();
		mMinBandLevel = EqualizerApi.getMinBandLevelRange();
		mBandsCount = EqualizerApi.getNumberOfBands();
		Integer integer = (mMaxBandLevel - mMinBandLevel);
		for (int i = 0; i < mBandsCount; ++i) {
			View.inflate((getActivity()), (2130903042),
					(mBandsContainer));
		}
		for (int j = 0; j < mBandsCount; ++j) {
			VerticalSeekBar verticalSeekBar = getBandByIndex(j);
			verticalSeekBar.setMax(integer.intValue());
			verticalSeekBar.setId(j);
		}
		for (int k = 0; k < mBandsCount; ++k) {
			getBandTitleByIndex(k).setText(
					(getFreqValue(EqualizerApi.getBandFreq(k))));
		}


	}

	@Override
	public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
		for (int i = 0; i < mBandsCount; ++i) {
			getBandByIndex(i).setEnabled(bl);
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
		return layoutInflater.inflate(R.layout.floating_equalizer, viewGroup, false);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int n, boolean bl) {
		((TextView) ((LinearLayout) (seekBar.getParent())).getChildAt(0))
		.setText((getDbValue(n)));
		updateBandsValue();
	}

	@Override
	public void onResume() {
		super.onResume();
		fetchValues();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
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

	protected void updateBandsValue() {
		for (int i = 0; i < mBandsCount; ++i) {
			EqualizerApi
			.setBandLevel(
					i,
					(getBandByIndex(i).getProgress() + mMinBandLevel));
		}
	}


}
