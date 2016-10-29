package com.karolskora.msorgranizer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.fragments.DatePickerFragment;
import com.karolskora.msorgranizer.java.DatabaseHelper;
import com.karolskora.msorgranizer.java.DatabaseQueries;
import com.karolskora.msorgranizer.java.NotificationOrganizer;

public class AboutAppActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);
        int style=getIntent().getIntExtra(AppStyleActivity.USER_STYLE, 0);
        if(style == 2) {
            setTheme(R.style.darkAppTheme);
        }
        saveData();
        long injectionTime = getIntent().getLongExtra(DatePickerFragment.TIME_IN_MILIS, 0);
        NotificationOrganizer.scheduleNotification(injectionTime, this);
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
        int style = intent.getIntExtra(AppStyleActivity.USER_STYLE , 0);
        long timeInMilis = intent.getLongExtra(DatePickerFragment.TIME_IN_MILIS, 0);
        int doses = intent.getIntExtra(DrugSupplyActivity.DOSES, 0);
        int notificationDoses = intent.getIntExtra(DrugSupplyActivity.NOTIFICATION_DOSES, 0);


        DatabaseQueries.putUser(this, name, doctorName, nurseName, email);
        DatabaseQueries.putInjectionsSchedule(this, timeInMilis);
        DatabaseQueries.setDoses(this, doses, notificationDoses);
        DatabaseQueries.putApplicationSettings(this, style);
    }
}
