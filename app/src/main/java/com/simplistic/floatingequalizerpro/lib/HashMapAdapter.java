/*
 * Decompiled with CFR 0_58.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.widget.ArrayAdapter
 *  java.lang.Object
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.Set
 */
package com.simplistic.floatingequalizerpro.lib;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class HashMapAdapter extends ArrayAdapter<Object> {
	protected ArrayList<Object> mKeys = new ArrayList();
	protected ArrayList<Object> mValues = new ArrayList();

	public HashMapAdapter(Context context, int n,
			HashMap<Object, Object> hashMap) {
		super(context, n, hashMap.values().toArray());
		Object[] arrobject = hashMap.keySet().toArray();
		for (int i = 0; i < arrobject.length; ++i) {
			this.mKeys.add(arrobject[i]);
			this.mValues.add(hashMap.get(arrobject[i]));
		}
	}

	public Object getKeyByPosition(int n) {
		return this.mKeys.get(n);
	}

	public int getKeyPosition(Object object) {
		return this.mKeys.indexOf(object);
	}
}
