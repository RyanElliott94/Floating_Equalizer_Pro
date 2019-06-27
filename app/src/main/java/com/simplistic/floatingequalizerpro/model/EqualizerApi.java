/*
 * Decompiled with CFR 0_58.
 * 
 * Could not load the following classes:
 *  android.media.audiofx.BassBoost
 *  android.media.audiofx.Equalizer
 *  android.media.audiofx.Virtualizer
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 */
package com.simplistic.floatingequalizerpro.model;

import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.LoudnessEnhancer;
import android.media.audiofx.PresetReverb;
import android.media.audiofx.Virtualizer;

public class EqualizerApi {
	public static String PREF_AUTOSTART;
	private static Integer PRIORITY;
	private static Integer SESSION;
	private static Integer[] mBandsValue;
	private static BassBoost mBb;
	private static  PresetReverb mRv;
	private  static  LoudnessEnhancer mLe;
	public static Equalizer mEq;
	private static Integer mSession;
	private static Virtualizer mVirt;

	static {
		PRIORITY = 0;
		SESSION = 0;
		PREF_AUTOSTART = "autostart";
	}

	public static void destroy() {
		mEq.release();
		mVirt.release();
//		mRv.release();
		mBb.release();
		mLe.release();
	}

	public static void setReverbPreset(short preset){
		mRv.setPreset(preset);
	}

	public static int getBandFreq(int n) {
		short s = (short) (Math.ceil(((n / 2))));
		int n2 = mEq.getCenterFreq(s);
		int n3 = (30 * n2 / 100);
		if (((n + 1) % 2) != 0)
			return (n2 - n3);
		return (n2 + n3);
	}

	public static int getBandLevel(int n) {
		if ((mBandsValue == null)
				|| (mBandsValue[n] == null))
			return 0;
		return mBandsValue[n];
	}

	public static boolean getBassBoostEnabled() {
		return mBb.getEnabled();
	}

	public static boolean getReverbEnabled() {
		return mRv.getEnabled();
	}

	public static boolean getLoudEnabled() {
		return mLe.getEnabled();
	}

	public static short getReverbPreset() {
		return mRv.getPreset();
	}

	public static int getBassBoostStrength() {
		return mBb.getRoundedStrength();
	}

	public static boolean getEqualizerEnabled() {
		return mEq.getEnabled();
	}

	public static int getMaxBandLevelRange() {
		return mEq.getBandLevelRange()[1];
	}

	public static int getMinBandLevelRange() {
		return mEq.getBandLevelRange()[0];
	}

	public static int getNumberOfBands() {
		return (2 * mEq.getNumberOfBands());
	}

	public static boolean getVirtualizerEnabled() {
		return mVirt.getEnabled();
	}

	public static int getVirtualizerStrength() {
		return mVirt.getRoundedStrength();
	}

	public static void init(Integer integer) {
		if (integer == null) {
			integer = SESSION;
		}
		mSession = integer;
		mEq = new Equalizer(PRIORITY,
				mSession);
		mVirt = new Virtualizer(PRIORITY,
				mSession);
		mBb = new BassBoost(PRIORITY,
				mSession);
		mLe = new LoudnessEnhancer(mSession);
//		mRv = new PresetReverb(PRIORITY,
//				mSession);
		mBandsValue = new Integer[getNumberOfBands()];
	}

	/*
	 * Enabled aggressive block sorting Enabled unnecessary exception pruning
	 */
	public static void setBandLevel(int n, int n2) {
		double d;
		double d2;
		mBandsValue[n] = n2;
		if (((n + 1) % 2) == 0) {
			d = getBandLevel((n - 1));
			d2 = getBandLevel(n);
		} else {
			d = getBandLevel(n);
			d2 = getBandLevel((n + 1));
		}
		double d3 = (((getNumberOfBands()) / 2.0 - (n)) / 12.0);
		double d4 = (1.0 - d3);
		double d5 = (1.0 + d3);
		double d6 = (d > 0.0) ? ((d * d5)) : ((d / d4));
		double d7 = (d2 > 0.0) ? ((d2 / d5)) : ((d2 * d4));
		mEq.setBandLevel((short) (Math.ceil(((n / 2)))),
				(short) (Math.ceil((((d6 + d7) / 2.0)))));
	}

	public static void setBassBoostEnabled(boolean bl) {
		mBb.setEnabled(bl);
	}

	public static void setBassBoostStrength(int n) {
		mBb.setStrength((short) (n));
	}

	public static void setEqualizerEnabled(boolean bl) {
		mEq.setEnabled(bl);
	}

	public static void setReverbEnabled(boolean bool) {
		mRv.setEnabled(bool);
	}

	public static void setLoudEnabled(boolean bool) {
		mLe.setEnabled(bool);
	}

	public static void setLoudGain(int gain) {
		mLe.setTargetGain(gain);
	}

	public static void setVirtualizerEnabled(boolean bl) {
		mVirt.setEnabled(bl);
	}

	public static void setVirtualizerStrength(int n) {
		mVirt.setStrength((short) (n));
	}
}
