package com.karolskora.msorgranizer.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.broadcastReceivers.InjectionTimeAlarmReceiver;
import com.karolskora.msorgranizer.fragments.DatePickerFragment;
import com.karolskora.msorgranizer.java.DatabaseHelper;
import com.karolskora.msorgranizer.java.DatabaseQueries;
import com.karolskora.msorgranizer.models.DrugSupply;
import com.karolskora.msorgranizer.models.Notification;
import com.karolskora.msorgranizer.models.User;

import java.util.Calendar;

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

        long timeInMilis = intent.getLongExtra(DatePickerFragment.TIME_IN_MILIS, 0);

        int doses = intent.getIntExtra(DrugSupplyActivity.DOSES, 0);
        int notificationDoses = intent.getIntExtra(DrugSupplyActivity.NOTIFICATION_DOSES, 0);

        DatabaseHelper dbHelper = getHelper();

        RuntimeExceptionDao<User, String> userDao = dbHelper.getUserDao();
        userDao.create(new User(name, doctorName, nurseName));
        Log.d(this.getClass().toString(), "Zapisano dane użytkowniak do bazy danych. Imie i nazwisko: " + name + ", imie i nazwisko lekarza: " +
                doctorName + ", imie i naziwsko pielegniarki: " + nurseName);

        RuntimeExceptionDao<Notification, Integer> injectionsScheduleDao = dbHelper.getInjectionsScheduleDao();
        injectionsScheduleDao.create(new Notification(timeInMilis));
        Log.d(this.getClass().toString(), "Zapisano czas notyfikacji do bazy danych: " + timeInMilis + "milis");

        DatabaseQueries.setDoses(this, doses, notificationDoses);
        Log.d(this.getClass().toString(), "Zapisano ustawienia leku w bazie danych. Ilosc leku:" + doses);
    }

    private void scheduleNotification() {

        long injectionTime=getIntent().getLongExtra(DatePickerFragment.TIME_IN_MILIS, 0);
        Intent broadcastIntent = new Intent(this, InjectionTimeAlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, injectionTime, AlarmManager.INTERVAL_DAY * 2, pendingIntent);

        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(injectionTime);
        Log.d(this.getClass().toString(), "Notyfikacja ustawiona na czas rok:" + calendar.get(Calendar.YEAR) + " miesiac: " + calendar.get(Calendar.MONTH) +
                " dzien: " + calendar.get(Calendar.DAY_OF_MONTH) + " godzina: " + calendar.get(Calendar.HOUR) + " minuta: " + calendar.get(Calendar.MINUTE));    }

}
