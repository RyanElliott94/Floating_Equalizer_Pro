/*
 * Decompiled with CFR 0_58.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteOpenHelper
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.String
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.simplistic.floatingequalizerpro.model.lib;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper
extends SQLiteOpenHelper {
    static final String DB_NAME = "Settings.db";
    static final Integer DB_VERSION = 40;
    protected static DbHelper mInstance;
    protected SQLiteDatabase mDbAdapter;

    protected DbHelper(Context context) {
        super(context, "Settings.db", (null), DbHelper.DB_VERSION.intValue());
    }

    public static DbHelper getInstance(Context context) {
        Class<DbHelper> var3_1 = DbHelper.class;
        synchronized (DbHelper.class) {
            if (DbHelper.mInstance == null) {
                DbHelper.mInstance = new DbHelper(context);
            }
            DbHelper dbHelper = DbHelper.mInstance;
            // ** MonitorExit[var3_1] (shouldn't be in output)
            return dbHelper;
        }
    }

    public void closeAdapter() {
        DbHelper dbHelper = this;
        synchronized (dbHelper) {
            if ((this.mDbAdapter != null) && (this.mDbAdapter.isOpen())) {
                this.mDbAdapter.close();
            }
            return;
        }
    }

    public Integer delete(String string, Long long_) {
        SQLiteDatabase sQLiteDatabase = this.getAdapter();
        String[] arrstring = new String[]{long_.toString()};
        return sQLiteDatabase.delete(string, "_id = ?", arrstring);
    }

    public SQLiteDatabase getAdapter() {
        DbHelper dbHelper = this;
        synchronized (dbHelper) {
            if (((this.mDbAdapter == null) || (!(this.mDbAdapter.isOpen()))) || (this.mDbAdapter.isReadOnly())) {
                this.mDbAdapter = this.getWritableDatabase();
            }
            SQLiteDatabase sQLiteDatabase = this.mDbAdapter;
            return sQLiteDatabase;
        }
    }

    public Long insert(String string, ContentValues contentValues) {
        return this.getAdapter().insert(string, "", contentValues);
    }

    @Override
	public void onCreate(SQLiteDatabase sQLiteDatabase) {
        if ((!(sQLiteDatabase.isOpen())) || (sQLiteDatabase.isReadOnly())) {
            sQLiteDatabase = this.getWritableDatabase();
        }
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS profile;");
        sQLiteDatabase.execSQL("CREATE TABLE profile (_id INTEGER PRIMARY KEY AUTOINCREMENT,profile_name TEXT NOT NULL,selected INTEGER,settings TEXT);");
        sQLiteDatabase.execSQL("INSERT INTO profile (profile_name, settings, selected) VALUES ('Default', '{\"equalizer_band_2\":176,\"equalizer_band_1\":528,\"equalizer_band_4\":1188,\"equalizer_band_3\":760,\"equalizer_band_0\":1119,\"bass_boost_enabled\":true,\"virtualizer_enabled\":false,\"equalizer_enabled\":true,\"bass_boost\":185,\"virtualizer\":180}', 1)");
        sQLiteDatabase.execSQL("INSERT INTO profile (profile_name, settings) VALUES ('That Bassy Bass', '{\"equalizer_band_2\":43,\"equalizer_band_1\":500,\"equalizer_band_4\":1366,\"equalizer_band_3\":640,\"equalizer_band_0\":1049,\"bass_boost_enabled\":true,\"virtualizer_enabled\":false,\"equalizer_enabled\":true,\"bass_boost\":502,\"virtualizer\":0}')");
        sQLiteDatabase.execSQL("INSERT INTO profile (profile_name, settings) VALUES ('Light', '{\"equalizer_band_2\":367,\"equalizer_band_1\":519,\"equalizer_band_4\":1077,\"equalizer_band_3\":684,\"equalizer_band_0\":938,\"bass_boost_enabled\":false,\"virtualizer_enabled\":false,\"equalizer_enabled\":true,\"bass_boost\":0,\"virtualizer\":0}')");
        sQLiteDatabase.execSQL("INSERT INTO profile (profile_name, settings) VALUES ('Classical', '{\"equalizer_band_2\":-400,\"equalizer_band_1\":600,\"equalizer_band_4\":800,\"equalizer_band_3\":800,\"equalizer_band_0\":1000,\"bass_boost_enabled\":false,\"virtualizer_enabled\":false,\"equalizer_enabled\":true,\"bass_boost\":0,\"virtualizer\":0}')");
        sQLiteDatabase.execSQL("INSERT INTO profile (profile_name, settings) VALUES ('Dance', '{\"equalizer_band_2\":400,\"equalizer_band_1\":0,\"equalizer_band_4\":200,\"equalizer_band_3\":800,\"equalizer_band_0\":1200,\"bass_boost_enabled\":false,\"virtualizer_enabled\":false,\"equalizer_enabled\":true,\"bass_boost\":0,\"virtualizer\":0}')");
        sQLiteDatabase.execSQL("INSERT INTO profile (profile_name, settings) VALUES ('Flat', '{\"equalizer_band_2\":0,\"equalizer_band_1\":0,\"equalizer_band_4\":0,\"equalizer_band_3\":0,\"equalizer_band_0\":0,\"bass_boost_enabled\":false,\"virtualizer_enabled\":false,\"equalizer_enabled\":true,\"bass_boost\":0,\"virtualizer\":0}')");
        sQLiteDatabase.execSQL("INSERT INTO profile (profile_name, settings) VALUES ('Folk', '{\"equalizer_band_2\":0,\"equalizer_band_1\":0,\"equalizer_band_4\":-200,\"equalizer_band_3\":400,\"equalizer_band_0\":600,\"bass_boost_enabled\":false,\"virtualizer_enabled\":false,\"equalizer_enabled\":true,\"bass_boost\":0,\"virtualizer\":0}')");
        sQLiteDatabase.execSQL("INSERT INTO profile (profile_name, settings) VALUES ('Heavy Metal', '{\"equalizer_band_2\":1500,\"equalizer_band_1\":200,\"equalizer_band_4\":0,\"equalizer_band_3\":600,\"equalizer_band_0\":800,\"bass_boost_enabled\":false,\"virtualizer_enabled\":false,\"equalizer_enabled\":true,\"bass_boost\":0,\"virtualizer\":0}')");
        sQLiteDatabase.execSQL("INSERT INTO profile (profile_name, settings) VALUES ('Hip Hop', '{\"equalizer_band_2\":0,\"equalizer_band_1\":600,\"equalizer_band_4\":600,\"equalizer_band_3\":200,\"equalizer_band_0\":1000,\"bass_boost_enabled\":false,\"virtualizer_enabled\":false,\"equalizer_enabled\":true,\"bass_boost\":0,\"virtualizer\":0}')");
        sQLiteDatabase.execSQL("INSERT INTO profile (profile_name, settings) VALUES ('Jazz', '{\"equalizer_band_2\":-400,\"equalizer_band_1\":400,\"equalizer_band_4\":1000,\"equalizer_band_3\":400,\"equalizer_band_0\":800,\"bass_boost_enabled\":false,\"virtualizer_enabled\":false,\"equalizer_enabled\":true,\"bass_boost\":0,\"virtualizer\":0}')");
        sQLiteDatabase.execSQL("INSERT INTO profile (profile_name, settings) VALUES ('Pop', '{\"equalizer_band_2\":1000,\"equalizer_band_1\":400,\"equalizer_band_4\":-400,\"equalizer_band_3\":200,\"equalizer_band_0\":-200,\"bass_boost_enabled\":false,\"virtualizer_enabled\":false,\"equalizer_enabled\":true,\"bass_boost\":0,\"virtualizer\":0}')");
        sQLiteDatabase.execSQL("INSERT INTO profile (profile_name, settings) VALUES ('Rock', '{\"equalizer_band_2\":-200,\"equalizer_band_1\":600,\"equalizer_band_4\":1000,\"equalizer_band_3\":600,\"equalizer_band_0\":1000,\"bass_boost_enabled\":false,\"virtualizer_enabled\":false,\"equalizer_enabled\":true,\"bass_boost\":0,\"virtualizer\":0}')");
        sQLiteDatabase.execSQL("INSERT INTO profile (profile_name, settings) VALUES ('Latin', '{\"equalizer_band_2\":-600,\"equalizer_band_1\":400,\"equalizer_band_4\":1400,\"equalizer_band_3\":200,\"equalizer_band_0\":1200,\"bass_boost_enabled\":false,\"virtualizer_enabled\":false,\"equalizer_enabled\":true,\"bass_boost\":0,\"virtualizer\":0}')");
        this.upgrade40(sQLiteDatabase);
    }

    @Override
	public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
        if (n <= 35) {
            this.upgrade40(sQLiteDatabase);
            return;
        }
        this.onCreate(sQLiteDatabase);
    }

    public Integer update(String string, ContentValues contentValues, Long long_) {
        SQLiteDatabase sQLiteDatabase = this.getAdapter();
        String[] arrstring = new String[]{long_.toString()};
        return sQLiteDatabase.update(string, contentValues, "_id = ?", arrstring);
    }

    protected void upgrade40(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("ALTER TABLE profile ADD sort INTEGER");
        sQLiteDatabase.execSQL("UPDATE profile SET sort = _id * 10;");
        sQLiteDatabase.execSQL("INSERT INTO profile (profile_name, settings, sort) VALUES ('Blues', '{\"equalizer_band_2\":700,\"equalizer_band_1\":200,\"equalizer_band_4\":400,\"equalizer_band_3\":500,\"equalizer_band_0\":100,\"bass_boost_enabled\":false,\"virtualizer_enabled\":false,\"equalizer_enabled\":true,\"bass_boost\":0,\"virtualizer\":0}', 35)");
    }
}

