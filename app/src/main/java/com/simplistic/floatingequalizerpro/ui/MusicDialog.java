/*
 * Decompiled with CFR 0_58.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnDismissListener
 *  android.database.Cursor
 *  android.net.Uri
 *  android.provider.MediaStore
 *  android.provider.MediaStore$Audio
 *  android.provider.MediaStore$Audio$Media
 *  android.support.v4.widget.SimpleCursorAdapter
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 */
package com.simplistic.floatingequalizerpro.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.SimpleCursorAdapter;

public class MusicDialog {
	public class MusicAdapter extends SimpleCursorAdapter {
		public MusicAdapter(Context context, int n, Cursor cursor,
				String[] arrstring, int[] arrn, int n2) {
			super(context, n, cursor, arrstring, arrn, n2);
		}

		protected String getDuration(int n) {
			int n2 = (n / 1000);
			int n3 = (int) (Math.floor(((n2 / 60))));
			int n4 = (n2 - n3 * 60);
			String string = Integer.toString((n3));
			String string2 = (string + Messages.getString("MusicDialog.11")); //$NON-NLS-1$
			if (n4 > 9)
				return (string2 + n4);
			string2 = (string2 + Messages.getString("MusicDialog.12")); //$NON-NLS-1$
			return (string2 + n4);
		}

		@SuppressLint("ResourceType")
		@Override
		public View getView(int n, View view, ViewGroup viewGroup) {
			View view2 = super.getView(n, view, viewGroup);
			Cursor cursor = (Cursor) (getItem(n));
			((TextView) view2.findViewById(2131165197)).setText((getDuration(cursor.getInt(cursor
					.getColumnIndex(Messages.getString("MusicDialog.13")))))); //$NON-NLS-1$
			return view2;
		}
	}
	public interface OnMusicSelectHandler {
		void onMusicSelect(String var1);
	}
	protected MusicAdapter mAdapter;

	protected Context mContext;

	protected OnMusicSelectHandler mHandler;

	public MusicDialog(Context context) {
		mContext = context;
	}

	public MusicAdapter getAdapter() {
		String[] arrstring = new String[] { Messages.getString("MusicDialog.0"), Messages.getString("MusicDialog.1"), Messages.getString("MusicDialog.2"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				Messages.getString("MusicDialog.3"), Messages.getString("MusicDialog.4") }; //$NON-NLS-1$ //$NON-NLS-2$
		Cursor cursor = mContext.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, arrstring,
				Messages.getString("MusicDialog.5"), (null), (null)); //$NON-NLS-1$
		mAdapter = new MusicAdapter(mContext, 2130903045, cursor,
				new String[] { Messages.getString("MusicDialog.6"), Messages.getString("MusicDialog.7"), Messages.getString("MusicDialog.8") }, new int[] { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				2131165198, 2131165196, 2131165197 }, 0);
		return mAdapter;
	}

	public void setOnMusicSelectHandler(
			OnMusicSelectHandler onMusicSelectHandler) {
		mHandler = onMusicSelectHandler;
	}

//	public Dialog showMusicDialog() {
//		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//		View view = ((LayoutInflater) mContext
//				.getSystemService(Messages.getString("MusicDialog.9"))).inflate(2130903044, //$NON-NLS-1$
//						(ViewGroup) (null));
//		final ListView listView = (ListView) (view.findViewById(2131165195));
//		listView.setAdapter((getAdapter()));
//		builder.setView(view);
//		builder.setTitle(2130968600);
//		final AlertDialog alertDialog = builder.show();
//		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> adapterView22,
//					View adapterView2, int n, long adapterView) {
//				if (mHandler != null) {
//					Cursor cursor = (Cursor) (listView.getItemAtPosition(n));
//					mHandler.onMusicSelect(cursor
//							.getString(cursor.getColumnIndex(Messages.getString("MusicDialog.10")))); //$NON-NLS-1$
//				}
//				alertDialog.dismiss();
//			}
//		});
//		alertDialog
//		.setOnDismissListener((new DialogInterface.OnDismissListener() {
//
//			@Override
//			public void onDismiss(DialogInterface dialogInterface) {
//				Cursor cursor;
//				if (((mAdapter == null) || ((cursor = mAdapter
//						.getCursor()) == null)) || (cursor.isClosed()))
//					return;
//				cursor.close();
//			}
//		}));
//		return alertDialog;
//	}

}
