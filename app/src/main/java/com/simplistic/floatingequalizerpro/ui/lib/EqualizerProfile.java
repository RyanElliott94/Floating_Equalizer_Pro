/*
 * Decompiled with CFR 0_58.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.support.v4.app.Fragment
 *  android.support.v4.app.FragmentActivity
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 */
package com.simplistic.floatingequalizerpro.ui.lib;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.simplistic.floatingequalizerpro.model.Profile;
import com.simplistic.floatingequalizerpro.ui.ProfileDialog;

public abstract class EqualizerProfile extends Fragment implements
		ProfileDialog.OnProfileUpdate {
	protected EqualizerProfileHandler mHandler;
	protected ProfileDialog mProfileDialog;
	protected Profile mProfileModel;

	protected void beforeProfileSelected() {
		if (this.mHandler == null)
			return;
		this.mHandler.onBeforeSelectProfile();
	}

	protected ProfileDialog getProfileDialogInstance(Context context) {
		EqualizerProfile equalizerProfile = this;
		synchronized (equalizerProfile) {
			if (this.mProfileDialog == null) {
				this.mProfileDialog = new ProfileDialog(context);
				this.mProfileDialog.setOnProfileUpdateHandler((this));
			}
			ProfileDialog profileDialog = this.mProfileDialog;
			return profileDialog;
		}
	}

	protected Profile getProfileModelInstance() {
		EqualizerProfile equalizerProfile = this;
		synchronized (equalizerProfile) {
			if (this.mProfileModel == null) {
				this.mProfileModel = new Profile((this.getActivity()));
			}
			Profile profile = this.mProfileModel;
			return profile;
		}
	}

	public void manageProfile(Context context, Long long_) {
		ProfileDialog profileDialog = this.getProfileDialogInstance(context);
		profileDialog.showManagementDialog(long_);
		if (long_ == null) {
			profileDialog.setProfileName("");
			return;
		}
		profileDialog.setProfileName(this.getProfileModelInstance().getName(
				long_));
	}

	@Override
	public void onProfileUpdate(String string, Long long_) {
		this.getProfileModelInstance().setName(long_, string);
		this.refresh();
	}

	protected void profileSelected() {
		if (this.mHandler == null)
			return;
		this.mHandler.onSelectProfile();
	}

	public abstract void refresh();

	public void setOnSelectProfileHanlder(
			EqualizerProfileHandler equalizerProfileHandler) {
		this.mHandler = equalizerProfileHandler;
	}

	public interface EqualizerProfileHandler {
		void onBeforeSelectProfile();

		void onSelectProfile();
	}

}
