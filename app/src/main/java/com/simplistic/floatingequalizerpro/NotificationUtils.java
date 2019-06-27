package com.simplistic.floatingequalizerpro;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationUtils extends ContextWrapper {

    private NotificationManager mManager;
    public static final String ANDROID_CHANNEL_ID = "com.simplistic.floating_equalizer.ANDROID";
    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";
    NotificationCompat.Builder builder;

    public NotificationUtils(Context base) {
        super(base);
        createChannels();
    }

    public void createChannels() {

        // create android channel
        NotificationChannel androidChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                    ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

            androidChannel.enableLights(true);
            // Sets whether notification posted to this channel should vibrate.
            androidChannel.enableVibration(true);
            // Sets the notification light color for notifications posted to this channel
            androidChannel.setLightColor(Color.GREEN);
            // Sets whether notifications posted to this channel appear on the lockscreen or not
            androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            getManager().createNotificationChannel(androidChannel);
        }

    }

    public NotificationCompat.Builder getAndroidChannelNotification(String title, String body, Class className) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(this, className);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pi = PendingIntent.getActivity((this), (0), (intent),
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder = new NotificationCompat.Builder(getApplicationContext(), ANDROID_CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setContentIntent(pi)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(101, builder.build());
        }else{
            Intent intent = new Intent(this, className);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder = new NotificationCompat.Builder(this, ANDROID_CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            notificationManager.notify(101, builder.build());
        }
        return builder;
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
}