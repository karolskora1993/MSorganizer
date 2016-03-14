package com.karolskora.msorgranizer.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.admin.DeviceAdminInfo;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.broadcastReceivers.InjectionTimeAlarmReceiver;
import com.karolskora.msorgranizer.fragments.DatePickerFragment;
import com.karolskora.msorgranizer.helpers.DatabaseHelper;
import com.karolskora.msorgranizer.models.InjectionsSchedule;
import com.karolskora.msorgranizer.models.User;

public class AboutAppActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);
        this.saveData();
    }

    public void onButtonNextClick(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void saveData(){

        Intent intent=getIntent();

        String name=intent.getStringExtra(UserInformationsActivity.USER_NAME);
        String doctorName=intent.getStringExtra(UserInformationsActivity.DOCTOR_NAME);
        String nurseName=intent.getStringExtra(UserInformationsActivity.NURSE_NAME);

        long timeInMilis=intent.getLongExtra(DatePickerFragment.TIME_IN_MILIS, 0);

        DatabaseHelper dbHelper=getHelper();

        RuntimeExceptionDao<User,String> userDao=dbHelper.getUserDao();
        userDao.create(new User(name, doctorName, nurseName));
        Log.d(this.getClass().toString(), "Zapisano dane użytkowniak do bazy danych. Imie i nazwisko: " + name + ", imie i nazwisko lekarza: " +
                doctorName + ", imie i naziwsko pielegniarki: " + nurseName);

        RuntimeExceptionDao<InjectionsSchedule,Integer> injectionsScheduleDao=dbHelper.getInjectionsScheduleDao();
        injectionsScheduleDao.create(new InjectionsSchedule(timeInMilis));
        Log.d(this.getClass().toString(), "Zapisano czas notyfikacji do bazy danych: " + timeInMilis + "milis");

        this.scheduleNotification(timeInMilis);
    }

    private void scheduleNotification(long injectionTime) {

        Intent broadcastIntent = new Intent(this, InjectionTimeAlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, injectionTime, AlarmManager.INTERVAL_DAY * 2, pendingIntent);
        Log.d(this.getClass().toString(), "alarm ustawiony na czas w milisekundach: "+injectionTime);
    }
}
