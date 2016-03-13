package com.karolskora.msorgranizer.broadcastReceivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.activities.LaunchNotificationActivity;
import com.karolskora.msorgranizer.activities.MainActivity;

public class InjectionTimeAlarmReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(LaunchNotificationActivity.NOTIFICATION);
        int id = intent.getIntExtra(LaunchNotificationActivity.NOTIFICATION_ID,0);
        notificationManager.notify(id, notification);
    }
}
