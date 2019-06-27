package com.simplistic.floatingequalizerpro.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.core.app.NotificationCompat;

import com.simplistic.floatingequalizerpro.EqualizerActivity;
import com.simplistic.floatingequalizerpro.MainActivity;
import com.simplistic.floatingequalizerpro.NotificationUtils;
import com.simplistic.floatingequalizerpro.R;

public class Floating extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	ImageView eqBubble;
	WindowManager windowManager;
	private EqualizerActivity mainEQ;
	PopupMenu popup;
	private NotificationUtils mNotificationUtils;
	NotificationCompat.Builder nb;
	String basicEQ;
	String advanceEQ;
	WindowManager.LayoutParams myParams;


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		basicEQ = intent.getStringExtra("basic_eq");
		advanceEQ = intent.getStringExtra("advance_eq");
		return Service.START_STICKY;
	}

	@SuppressLint("ClickableViewAccessibility")
	public void onCreate() {
		super.onCreate();

		mNotificationUtils = new NotificationUtils(this);

		eqBubble = new ImageView(this);
		eqBubble.setImageResource(R.mipmap.ic_launcher);
		eqBubble.setMinimumWidth(200);
		eqBubble.setMinimumHeight(200);
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);


		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			myParams = new WindowManager.LayoutParams(
					WindowManager.LayoutParams.WRAP_CONTENT,
					WindowManager.LayoutParams.WRAP_CONTENT,
					WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
					WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
					PixelFormat.TRANSLUCENT);
		} else {
			myParams = new WindowManager.LayoutParams(
					WindowManager.LayoutParams.WRAP_CONTENT,
					WindowManager.LayoutParams.WRAP_CONTENT,
					WindowManager.LayoutParams.TYPE_PHONE,
					WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
					PixelFormat.TRANSLUCENT);
		}

		myParams.gravity = Gravity.TOP;
		myParams.x = 0;
		myParams.y = 100;
		windowManager.addView(eqBubble, myParams);
		try {

			eqBubble.setOnTouchListener(new View.OnTouchListener() {
				private int initialX;
				private int initialY;
				private float initialTouchX;
				private float initialTouchY;

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							initialX = myParams.x;
							initialY = myParams.y;
							initialTouchX = event.getRawX();
							initialTouchY = event.getRawY();
							break;
						case MotionEvent.ACTION_UP:
							break;
						case MotionEvent.ACTION_MOVE:
							myParams.x = initialX + (int) (event.getRawX() - initialTouchX);
							myParams.y = initialY + (int) (event.getRawY() - initialTouchY);
							windowManager.updateViewLayout(v, myParams);
							break;
					}
					return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		popup = new PopupMenu(getApplicationContext(), eqBubble);
		//Inflating the Popup using xml file
		popup.getMenuInflater().inflate(R.menu.bubble_menu, popup.getMenu());

		//registering popup with OnMenuItemClickListener
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
					case R.id.back_to_eq:
						backToEQ();
						break;
					case R.id.hide_icon:
						hideIcon();
						break;
					case R.id.stop:
						stopSelf();
						break;
				}
				return true;
			}
		});

		eqBubble.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View view) {
				if(eqBubble != null){
					popup.show();
				}
				return false;
			}
		});
	}

	public void hideIcon() {
		if (basicEQ != null && basicEQ.equals("isFromBasic")) {
			nb = mNotificationUtils.
					getAndroidChannelNotification("Floating Equalizer", "Running", EqualizerActivity.class);
			mNotificationUtils.getManager().notify(101, nb.build());
			eqBubble.setVisibility(View.GONE);
			stopSelf();
		} else if (advanceEQ != null && advanceEQ.equals("isFromAdvance")) {
			nb = mNotificationUtils.
					getAndroidChannelNotification("Floating Equalizer", "Running", MainActivity.class);
			mNotificationUtils.getManager().notify(101, nb.build());
			eqBubble.setVisibility(View.GONE);
			stopSelf();
		}
	}

	public void backToEQ() {
		if (basicEQ != null && basicEQ.equals("isFromBasic")) {
			Intent i = new Intent(getApplicationContext(), EqualizerActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
		} else if (advanceEQ != null && advanceEQ.equals("isFromAdvance")) {
			Intent i = new Intent(getApplicationContext(), MainActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
		}
	}

	public void onDestroy() {
		super.onDestroy();
		if (eqBubble != null) {
			windowManager.removeView(eqBubble);
			stopForeground(true);
		}
	}
}