/*
 * Decompiled with CFR 0_58.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.provider.BaseColumns
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 */
package com.simplistic.floatingequalizerpro.model.lib;

import android.content.ContentValues;
import android.content.Context;
import android.provider.BaseColumns;

public abstract class DbTable implements BaseColumns {
	protected static DbHelper mDb;
	protected Context mContext;

	public DbTable(Context context) {
		if (DbTable.mDb == null) {
			DbTable.mDb = DbHelper.getInstance(context);
		}
		this.mContext = context;
	}

	public Integer delete(Long long_) {
		return DbTable.mDb.delete(this.getTableName(), long_);
	}

	public abstract String getTableName();

	public Long insert(ContentValues contentValues) {
		return DbTable.mDb.insert(this.getTableName(), contentValues);
	}

	public Integer update(ContentValues contentValues, Long long_) {
		return DbTable.mDb.update(this.getTableName(), contentValues, long_);
	}
}
