/*
 * Decompiled with CFR 0_58.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.support.v4.widget.SimpleCursorAdapter
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.RadioButton
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  org.json.JSONObject
 */
package com.simplistic.floatingequalizerpro.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.simplistic.floatingequalizerpro.R;
import com.simplistic.floatingequalizerpro.model.lib.DbTable;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile extends DbTable {
	public static final String PROFILE_NAME = "profile_name";
	public static final String SELECTED = "selected";
	public static final String SETTINGS = "settings";
	public static final String SORT = "sort";
	public static final String TABLE_NAME = "profile";

	public Profile(Context context) {
		super(context);
	}

	public Long getActive() {
		Cursor cursor = DbTable.mDb.getAdapter().query(this.getTableName(),
				new String[] { "_id" }, "selected = 1", (null),
                (null), (null), (null));
		Long long_ = new Long(0);
		if (cursor.moveToFirst()) {
			long_ = cursor.getLong(cursor.getColumnIndex("_id"));
		}
		cursor.close();
		return long_;
	}

	public ListAdapter getAdapter(int n) {
		return new ListAdapter(this.mContext, n, this.getProfileList());
	}

	public String getName(Long long_) {
		SQLiteDatabase sQLiteDatabase = DbTable.mDb.getAdapter();
		String string = this.getTableName();
		String[] arrstring = new String[] { "profile_name" };
		String[] arrstring2 = new String[] { long_.toString() };
		Cursor cursor = sQLiteDatabase.query(string, arrstring, "_id = ?",
				arrstring2, (null), (null), (null));
		String string2 = "";
		if (cursor.moveToFirst()) {
			string2 = cursor.getString(cursor.getColumnIndex("profile_name"));
		}
		cursor.close();
		return string2;
	}

	public Cursor getProfileList() {
		return DbTable.mDb.getAdapter().query(this.getTableName(),
				new String[] { "_id", "profile_name", "selected" },
                (null), (null), (null),
                (null), "sort ASC");
	}

	@Override
	public String getTableName() {
		return "profile";
	}

	public boolean loadSettings() {
		Cursor cursor = DbTable.mDb.getAdapter().query(this.getTableName(),
				new String[] { "settings" }, "selected = 1", (null),
                (null), (null), (null));
		if (cursor.moveToFirst()) {
			JSONObject jSONObject = new JSONObject();
			int n = 0;
			try {
				jSONObject = new JSONObject(cursor.getString(cursor
						.getColumnIndex("settings")));
				EqualizerApi.setBassBoostEnabled(jSONObject
						.getBoolean("bass_boost_enabled"));
				EqualizerApi.setBassBoostStrength(jSONObject
						.getInt("bass_boost"));
				EqualizerApi.setVirtualizerEnabled(jSONObject
						.getBoolean("virtualizer_enabled"));
				EqualizerApi.setVirtualizerStrength(jSONObject
						.getInt("virtualizer"));
				EqualizerApi.setEqualizerEnabled(jSONObject
						.getBoolean("equalizer_enabled"));
				n = 0;
			} catch (Exception e) {
				// empty catch block
			}
			do {
				if (n >= EqualizerApi.getNumberOfBands())
					return true;
				try {
					EqualizerApi.setBandLevel(n, jSONObject
							.getInt(("equalizer_band_" + Integer.valueOf(n)
									.toString())));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				++n;
			} while (true);
		}
		cursor.close();
		return false;
	}

	public boolean saveSettings() {
		int n;
		JSONObject jSONObject = new JSONObject();
		try {
			jSONObject.put("bass_boost_enabled",
					EqualizerApi.getBassBoostEnabled());
			jSONObject.put("bass_boost", EqualizerApi.getBassBoostStrength());
			jSONObject.put("virtualizer_enabled",
					EqualizerApi.getVirtualizerEnabled());
			jSONObject
					.put("virtualizer", EqualizerApi.getVirtualizerStrength());
			jSONObject.put("equalizer_enabled",
					EqualizerApi.getEqualizerEnabled());
			n = 0;
		} catch (Exception var2_4) {
			return false;
		}
		do {
			if (n >= EqualizerApi.getNumberOfBands())
				break;
			try {
				jSONObject.put(("equalizer_band_" + n),
						EqualizerApi.getBandLevel(n));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			++n;
		} while (true);
		ContentValues contentValues = new ContentValues();
		contentValues.put("settings", jSONObject.toString());
		DbTable.mDb.getAdapter().update(this.getTableName(), contentValues,
				"selected = 1", new String[0]);
		return true;
	}

	public void setActive(Long long_) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("selected", Integer.valueOf((0)));
		DbTable.mDb.getAdapter().update(this.getTableName(), contentValues,
				"selected = 1", new String[0]);
		contentValues.put("selected", Integer.valueOf((1)));
		this.update(contentValues, long_);
	}

	public void setName(Long long_, String string) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("profile_name", string);
		if (long_ == null) {
			this.setActive(this.insert(contentValues));
			return;
		}
		this.update(contentValues, long_);
	}

	public static class ListAdapter extends SimpleCursorAdapter {
		private final LayoutInflater mInflater;
		private TextView title;

		@SuppressLint("WrongConstant")
		public ListAdapter(Context context, int n, Cursor cursor) {
			super(context, n, cursor, new String[0], new int[0]);
			this.mInflater = (LayoutInflater) (context
					.getSystemService("layout_inflater"));
		}

		/*
		 * Enabled aggressive block sorting Enabled unnecessary exception
		 * pruning
		 */
		@Override
		public View getView(int n, View view, ViewGroup viewGroup) {
			View view2 = (view == null) ? (mInflater.inflate(R.layout.profile_entry,
					viewGroup, false)) : (view);
			Cursor cursor = (Cursor) (getItem(n));
			
 ((TextView) view2.findViewById(R.id.title)).setText(cursor.getString(cursor.getColumnIndex("profile_name")));
	RadioButton radioButton = view2.findViewById(R.id.active);
			if (cursor.getInt(cursor.getColumnIndex("selected")) > 0) {
				radioButton.setChecked(true);
				return view2;
			}
			radioButton.setChecked(false);
			return view2;
		}
	}

}
