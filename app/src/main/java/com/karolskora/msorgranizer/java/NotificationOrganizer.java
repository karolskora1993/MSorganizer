package com.karolskora.msorgranizer.java;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.karolskora.msorgranizer.activities.MainActivity;
import com.karolskora.msorgranizer.broadcastReceivers.InjectionTimeAlarmReceiver;
import com.karolskora.msorgranizer.broadcastReceivers.PostponedNotification;
import com.karolskora.msorgranizer.fragments.TimePickerFragment;

import java.util.Calendar;

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

    public static void scheduleNotification(long injectionTime, Activity ac) {
        Intent broadcastIntent = new Intent(ac, InjectionTimeAlarmReceiver.class);

        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(injectionTime);
        Log.d(NotificationOrganizer.class.toString(), "Nowy czas notyfikacji: rok:" + calendar.get(Calendar.YEAR) + " miesiac: " + calendar.get(Calendar.MONTH) +
                " dzien: " + calendar.get(Calendar.DAY_OF_MONTH) + " godzina: " + calendar.get(Calendar.HOUR) + " minuta: " + calendar.get(Calendar.MINUTE) + " AM_PM:" + calendar.get(Calendar.AM_PM));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(ac, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)ac.getSystemService(ac.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, injectionTime, AlarmManager.INTERVAL_DAY * 2, pendingIntent);
    }

    public static void oneTimeNotification(long injectionTime, Activity ac) {
        Intent broadcastIntent = new Intent(ac, PostponedNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ac, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) ac.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, injectionTime, pendingIntent);

        Intent intent=new Intent(ac, MainActivity.class);
        intent.putExtra(TimePickerFragment.POSTPONED_INJECTION_TIME, injectionTime);
        ac.startActivity(intent);
    }
}
