package com.karolskora.msorgranizer.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.karolskora.msorgranizer.broadcastReceivers.InjectionTimeAlarmReceiver;
import com.karolskora.msorgranizer.helpers.DatabaseHelper;
import com.karolskora.msorgranizer.models.InjectionsSchedule;

import java.util.Calendar;

public class LaunchNotificationActivity extends OrmLiteBaseActivity<DatabaseHelper> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.scheduleNotification();
        Intent intent =new Intent(this, MainActivity.class);

        startActivity(intent);
    }


    private void scheduleNotification() {

        Intent broadcastIntent = new Intent(this, InjectionTimeAlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, this.getInjectionTime(), pendingIntent);
        Log.d(this.getClass().toString(), "alarm ustawiony");
    }

    private long getInjectionTime(){

        DatabaseHelper dbHelper=getHelper();

        RuntimeExceptionDao<InjectionsSchedule, Integer> injectionsSchedueDao=dbHelper.getInjectionsScheduleDao();

        InjectionsSchedule schedule=injectionsSchedueDao.queryForAll().iterator().next();

        long time=schedule.getInjectionTime();

        Log.d(this.getClass().toString(), "czas zastrzyku pobrany z bazy:"+time);

        return time;
    }
}
