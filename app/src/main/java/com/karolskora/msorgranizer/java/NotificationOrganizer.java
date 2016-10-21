package com.karolskora.msorgranizer.java;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import com.karolskora.msorgranizer.broadcastReceivers.InjectionTimeAlarmReceiver;

/**
 * Created by apple on 21.10.2016.
 */
public class NotificationOrganizer {

    public static void UpdateNotification(long injectionTime, Activity ac) {


        Intent broadcastIntent = new Intent(ac, InjectionTimeAlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(ac, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)ac.getSystemService(ac.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, injectionTime, AlarmManager.INTERVAL_DAY * 2, pendingIntent);
    }
}
