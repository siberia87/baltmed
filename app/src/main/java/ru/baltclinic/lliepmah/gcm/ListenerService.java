package ru.baltclinic.lliepmah.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.Notification;
import ru.baltclinic.lliepmah.view.activity.MainActivity;
import ru.baltclinic.lliepmah.view.activity.SplashActivity;

public class ListenerService extends GcmListenerService {

    public static final int MINUTE = 60000;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        sendNotification(data, new Notification(data));

        Context mContext = getApplicationContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
        int messages = prefs.getInt(MainActivity.KEY_PUSH_MESSAGES, 0);
        prefs.edit().putInt(MainActivity.KEY_PUSH_MESSAGES, messages + 1).apply();
    }


    private void sendNotification(Bundle data, Notification notification) {

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentIntent(getNotificationIntent(data))
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(notification.getMessage())
                .setAutoCancel(true)
                .setSound(defaultSoundUri);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());


    }

    private PendingIntent getNotificationIntent(Bundle data) {
        Intent startIntent = new Intent(getApplicationContext(), SplashActivity.class);
        startIntent.putExtras(data);
        return PendingIntent.getActivity(getApplicationContext(), 0, startIntent, PendingIntent.FLAG_ONE_SHOT);
    }
}