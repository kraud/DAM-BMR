package com.dam.kevoundfreunde.laboratorio09;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Juxtar on 23/02/2017.
 */

public class ReceptorMensaje extends FirebaseMessagingService {
    @Override
    public void onCreate(){
        super.onCreate();
        Log.v("ReceptorMensaje", "Servicio creado");
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent notificationPIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder myBuild = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setContentIntent(notificationPIntent);

        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String strRingtonePreference = preferencias.getString("pref_ringtone", "DEFAULT_SOUND");
        myBuild.setSound(Uri.parse(strRingtonePreference));

        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context
                .NOTIFICATION_SERVICE);

        manager.notify(1, myBuild.build());
    }
}
