package com.karolskora.msorgranizer.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.broadcastReceivers.InjectionTimeAlarmReceiver;
import com.karolskora.msorgranizer.fragments.DatePickerFragment;
import com.karolskora.msorgranizer.java.DatabaseHelper;
import com.karolskora.msorgranizer.java.DatabaseQueries;

public class AboutAppActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);
        saveData();
        scheduleNotification();
    }

    public void onButtonNextClick(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void saveData() {

        Intent intent = getIntent();

        String name = intent.getStringExtra(UserInformationsActivity.USER_NAME);
        String doctorName = intent.getStringExtra(UserInformationsActivity.DOCTOR_NAME);
        String nurseName = intent.getStringExtra(UserInformationsActivity.NURSE_NAME);
        String email = intent.getStringExtra(UserInformationsActivity.EMAIL);


        long timeInMilis = intent.getLongExtra(DatePickerFragment.TIME_IN_MILIS, 0);

        int doses = intent.getIntExtra(DrugSupplyActivity.DOSES, 0);
        int notificationDoses = intent.getIntExtra(DrugSupplyActivity.NOTIFICATION_DOSES, 0);

        DatabaseQueries.putUser(this, name, doctorName, nurseName, email);
        DatabaseQueries.putInjectionsSchedule(this, timeInMilis);
        DatabaseQueries.setDoses(this, doses, notificationDoses);
    }

    private void scheduleNotification() {
        long injectionTime = getIntent().getLongExtra(DatePickerFragment.TIME_IN_MILIS, 0);
        Intent broadcastIntent = new Intent(this, InjectionTimeAlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, injectionTime, AlarmManager.INTERVAL_DAY * 2, pendingIntent);
    }
}
