/*
 * Decompiled with CFR 0_58.
 *
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.support.v4.app.FragmentActivity
 *  android.support.v4.widget.SimpleCursorAdapter
 *  android.view.ContextMenu
 *  android.view.ContextMenu$ContextMenuInfo
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MenuItem$OnMenuItemClickListener
 *  android.view.View
 *  android.view.View$OnCreateContextMenuListener
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$AdapterContextMenuInfo
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  java.lang.CharSequence
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 */
package com.simplistic.floatingequalizerpro.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.simplistic.floatingequalizerpro.R;
import com.simplistic.floatingequalizerpro.model.Profile;
import com.simplistic.floatingequalizerpro.ui.lib.EqualizerProfile;

public class ProfileFragment extends EqualizerProfile implements
		AdapterView.OnItemClickListener {
	protected ListView mList;
	protected Profile mProfile;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.mProfile = new Profile((this.getActivity()));
	}

	@Override
	public View onCreateView(LayoutInflater layoutInflater,
							 ViewGroup viewGroup, Bundle bundle) {
		return layoutInflater.inflate(R.layout.profile, viewGroup, false);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView22, View adapterView2,
							int adapterView, long l) {
		ListUpdateAsync listUpdateAsync = new ListUpdateAsync(this.mProfile,
				this.mList);
		Object[] arrobject = new Long[] { l };
		listUpdateAsync.execute((Long[]) arrobject);

	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onViewCreated(View view, Bundle bundle) {
		super.onViewCreated(view, bundle);

		mList = view.findViewById(R.id.profile_list);

		mList.setAdapter(mProfile.getAdapter(2130903048));

		SimpleCursorAdapter simpleCursorAdapter = (SimpleCursorAdapter)(mList
				.getAdapter());

		this.getActivity().startManagingCursor(simpleCursorAdapter.getCursor());
		this.mList.setOnItemClickListener((this));
		this.mList
				.setOnCreateContextMenuListener((new View.OnCreateContextMenuListener() {
					protected Long mId;
					private OnMenuItemClickListener var6_5;

					@Override
					public void onCreateContextMenu(ContextMenu contextMenu,
													View view,

													ContextMenu.ContextMenuInfo contextMenuInfo) {

						ProfileFragment.this.getActivity().getMenuInflater()
								.inflate(R.menu.profile, (contextMenu));

						AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) (contextMenuInfo);

						this.mId = mList.getAdapter().getItemId(adapterContextMenuInfo.position);

						contextMenu.setHeaderTitle(getProfileModelInstance().getName(mId));

						var6_5 = new MenuItem.OnMenuItemClickListener() {

							@Override
							public boolean onMenuItemClick(MenuItem menuItem) {
								if (menuItem.getItemId() == R.id.edit_profile) {
									ProfileFragment.this.manageProfile(
											(ProfileFragment.this.getActivity()),
											mId);
								}
								if (menuItem.getItemId() != R.id.delete_profile)
									return true;
								ProfileFragment.this.getProfileModelInstance()
										.delete(mId);
								ProfileFragment.this.refresh();
								return true;
							}
						};
						for (int i = 0; i < contextMenu.size(); ++i) {
							contextMenu.getItem(i).setOnMenuItemClickListener(
									(var6_5));
						}
					}

				}));
	}

	@Override
	public void refresh() {
		if (this.mList == null)
			return;
		((CursorAdapter) this.mList.getAdapter()).changeCursor(this.mProfile
				.getProfileList());
	}

	class ListUpdateAsync extends AsyncTask<Long, Long, Long> {
		protected ListView mList;
		protected Profile mProfile;

		public ListUpdateAsync(Profile profile, ListView listView) {
			this.mProfile = profile;
			this.mList = listView;
		}

		@Override
		protected/* varargs */Long doInBackground(Long... arrlong) {
			ProfileFragment.this.beforeProfileSelected();
			this.mProfile.saveSettings();
			this.mProfile.setActive(arrlong[0]);
			this.mProfile.loadSettings();
			return arrlong[0];
		}

		@Override
		protected void onPostExecute(Long long_) {
			super.onPostExecute((long_));
			ProfileFragment.this.profileSelected();
			ProfileFragment.this.refresh();
		}
	}

}
