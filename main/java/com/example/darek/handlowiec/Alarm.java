package com.example.darek.handlowiec;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.support.v7.app.NotificationCompat;

/**
 * Created by Darek on 2017-10-30.
 */

public class Alarm extends BroadcastReceiver {

    private static final int MY_NOTIFICATION_ID = 4;
    NotificationManager nfm;
    Notification ntf;
    String Nazwa, Godzina;
    @Override
    public void onReceive(Context context, Intent intent) {
        Nazwa = intent.getStringExtra("Naglowek");
        Godzina = intent.getStringExtra("Czas");
        Toast.makeText(context, "Masz spotkanie niedlugo!", Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(context, MeetingsActivity.class); //SO THIS ACTIVITY IN SETTED ALARM TIME.
       @SuppressWarnings("deprecation")
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

        ntf = new NotificationCompat.Builder(context)
                .setContentTitle(Nazwa)
                .setContentText(Godzina)
                .setTicker("Notification Head")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND) //PLAY DEFAULT SOUND
                .setAutoCancel(true) // REMOVE ALARM NOTIFICATION JUST BY SWIPE
                .setSmallIcon(R.mipmap.ic_launcher) //SHOWED IN STATUS BAR
                .build();

        nfm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        nfm.notify(MY_NOTIFICATION_ID, ntf);


    }
}
