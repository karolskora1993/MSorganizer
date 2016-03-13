package com.karolskora.msorgranizer.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.broadcastReceivers.InjectionTimeAlarmReceiver;
import com.karolskora.msorgranizer.helpers.DatabaseHelper;
import com.karolskora.msorgranizer.models.InjectionsSchedule;

public class LaunchNotificationActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    public static final String NOTIFICATION="notification";
    public static final String NOTIFICATION_ID="notification_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.scheduleNotification();

        Intent intent =new Intent(this, MainActivity.class);

        startActivity(intent);
    }

    private Notification getNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Kolejna dawka leku");
        builder.setContentText("Zbliża się czas kolejnej dawki leku");
        builder.setSmallIcon(R.drawable.button);
        return builder.build();
    }

    private void scheduleNotification() {

        Notification notification=this.getNotification();
        Intent notificationIntent = new Intent(this, InjectionTimeAlarmReceiver.class);
        notificationIntent.putExtra(NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, this.getInjectionTime(), 0, pendingIntent);
        Log.i(this.getClass().toString(), "Ustawienie cyklicznego powiadomienia o zastrzyku");

    }

    private long getInjectionTime(){

        DatabaseHelper dbHelper=getHelper();

        RuntimeExceptionDao<InjectionsSchedule, Integer> injectionsSchedueDao=dbHelper.getInjectionsScheduleDao();

        InjectionsSchedule schedule=injectionsSchedueDao.queryForAll().iterator().next();

        Log.d(this.getClass().toString(), "czas zastrzyku pobrany z bazy:"+schedule.getInjectionTime());

        return schedule.getInjectionTime();
    }
}
