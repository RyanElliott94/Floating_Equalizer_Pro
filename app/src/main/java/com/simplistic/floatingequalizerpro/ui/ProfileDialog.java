/*
 * Decompiled with CFR 0_58.
 *
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.text.Editable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.EditText
 *  java.lang.CharSequence
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 */
package com.simplistic.floatingequalizerpro.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.simplistic.floatingequalizerpro.R;

public class ProfileDialog {
	public static final int PROFILE_MANAGEMENT_DIALOG = 1000;
	protected Context mContext;
	protected EditText mEdit;
	protected OnProfileUpdate mHandler;

	public ProfileDialog(Context context) {
		this.mContext = context;
	}

	public void setOnProfileUpdateHandler(OnProfileUpdate onProfileUpdate) {
		this.mHandler = onProfileUpdate;
	}

	public void setProfileName(String string) {
		if (this.mEdit == null)
			return;
		this.mEdit.setText((string));
	}

	/*
	 * Enabled aggressive block sorting Enabled unnecessary exception pruning
	 */
	@SuppressLint("ResourceType")
	public Dialog showManagementDialog(final Long long_) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
		View view = ((LayoutInflater) this.mContext
				.getSystemService("layout_inflater")).inflate(R.layout.profile_dialog,
				(null));
		builder.setView(view);
		this.mEdit = view.findViewById(R.id.profile_edit);
		if (long_ != null) {
			builder.setTitle("");
		} else {
			builder.setTitle("");
		}
		builder.setNegativeButton("Okay",
				(new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialogInterface2,
										int dialogInterface) {
					}
				}));
		builder.setPositiveButton("Cancel",
				(new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialogInterface,
										int dialogInterface2) {
						if (mHandler == null)
							return;
						mHandler.onProfileUpdate(mEdit.getText().toString(),
								long_);
					}
				}));
		return builder.show();
	}

	public interface OnProfileUpdate {
		void onProfileUpdate(String var1, Long var2);
	}

}
