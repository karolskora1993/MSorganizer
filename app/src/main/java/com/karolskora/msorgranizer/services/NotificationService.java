package com.karolskora.msorgranizer.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.activities.InjectionActivity;

public class NotificationService extends Service {

    private final static  int NOTIFICATION_ID=1;

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);
        Context context = this.getApplicationContext();
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, InjectionActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = this.getNotificationBuilder();

        builder.setContentIntent(pendingIntent);

        notificationManager.notify(NOTIFICATION_ID, builder.build());

        Log.d(this.getClass().toString(), "notyfikacja");


        return START_NOT_STICKY;
    }

    private NotificationCompat.Builder getNotificationBuilder() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Kolejna dawka leku");
        builder.setContentText("Zbliża się czas kolejnej dawki leku");
        builder.setSmallIcon(R.mipmap.icon);
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        return builder;
    }
}
