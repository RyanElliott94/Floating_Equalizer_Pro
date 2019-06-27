/*
 * Decompiled with CFR 0_58.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 */
package com.simplistic.floatingequalizerpro.model;

import android.annotation.SuppressLint;
import android.content.Context;

import com.simplistic.floatingequalizerpro.R;
import com.simplistic.floatingequalizerpro.lib.HashMapAdapter;

import java.util.HashMap;

public class Reverberation {
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final int SL_REVERBPRESET_LARGEHALL = 5;
	public static final int SL_REVERBPRESET_LARGEROOM = 3;
	public static final int SL_REVERBPRESET_MEDIUMHALL = 4;
	public static final int SL_REVERBPRESET_MEDIUMROOM = 2;
	public static final int SL_REVERBPRESET_NONE = 0;
	public static final int SL_REVERBPRESET_PLATE = 6;
	public static final int SL_REVERBPRESET_SMALLROOM = 1;
	protected Context mContext;
	protected HashMap<Object, Object> mData;

	public Reverberation(Context context) {
		this.mContext = context;
	}

	@SuppressLint("ResourceType")
	public HashMapAdapter getAdapter() {
		HashMapAdapter hashMapAdapter = new HashMapAdapter(this.mContext,
				17367048, this.getValues());
		hashMapAdapter.setDropDownViewResource(17367049);
		return hashMapAdapter;
	}

	public HashMap<Object, Object> getValues() {
		this.mData = new HashMap();
		this.mData.put((0), this.mContext.getString(R.string.reverbpreset_none));
		this.mData.put((1), this.mContext.getString(R.string.reverbpreset_smallroom));
		this.mData.put((2), this.mContext.getString(R.string.reverbpreset_mediumroom));
		this.mData.put((3), this.mContext.getString(R.string.reverbpreset_largeroom));
		this.mData.put((4), this.mContext.getString(R.string.reverbpreset_mediumhall));
		this.mData.put((5), this.mContext.getString(R.string.reverbpreset_largehall));
		this.mData.put((6), this.mContext.getString(R.string.reverbpreset_plate));
		return this.mData;
	}
}
