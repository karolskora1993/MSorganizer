package com.karolskora.msorgranizer.activities;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.broadcastReceivers.InjectionTimeAlarmReceiver;
import com.karolskora.msorgranizer.fragments.AboutFragment;
import com.karolskora.msorgranizer.fragments.HistoryFragment;
import com.karolskora.msorgranizer.fragments.MainFragment;
import com.karolskora.msorgranizer.fragments.ReserveFragment;
import com.karolskora.msorgranizer.fragments.SettingsFragment;
import com.karolskora.msorgranizer.java.DatabaseHelper;
import com.karolskora.msorgranizer.models.InjectionsSchedule;
import com.karolskora.msorgranizer.models.User;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> {


    private DatabaseHelper dbHelper;
    private String[] titles;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private class DrawerItemClickListener implements ListView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = getUser();
        if (user == null) {
            Intent intent = new Intent(this, UserInformationsActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.layout_main);
            titles = getResources().getStringArray(R.array.titles);
            drawerList = (ListView) findViewById(R.id.drawer);
            if (drawerList == null)
                Log.d(this.getClass().toString(), "null");
            drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, titles));
            drawerList.setOnItemClickListener(new DrawerItemClickListener());

            DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawerToggle = new ActionBarDrawerToggle(
                    this,
                    drawerLayout,
                    R.string.open_drawer,
                    R.string.close_drawer
            ) {

                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                }

                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }
            };

            drawerLayout.setDrawerListener(drawerToggle);

            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
            if (savedInstanceState == null)
                selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    private void selectItem(int position) {

        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new HistoryFragment();
                break;
            case 2:
                fragment = new ReserveFragment();
                break;
            case 3:
                fragment = new SettingsFragment();
                break;
            case 4:
                fragment = new AboutFragment();
                break;
            default:
                fragment = new MainFragment();
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        setActionBarTitle(position);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(drawerList);
    }

    public void setActionBarTitle(int position) {
        String title;
        if (position == 0) {
            title = getResources().getString(R.string.app_name);
        } else
            title = this.titles[position];

        ActionBar ab=getActionBar();
        if(ab!=null)
            ab.setTitle(title);

    }

    public User getUser() {

        dbHelper = getHelper();

        RuntimeExceptionDao<User, String> userDao = dbHelper.getUserDao();

        List<User> users = userDao.queryForAll();

        if (users.isEmpty())
            return null;
        else
            return users.iterator().next();
    }

    public void onButtonSaveUserInfoClick(View view) {

        EditText nameTextEdit = (EditText) findViewById(R.id.nameTextEdit);
        String name = nameTextEdit.getText().toString();

        EditText doctorNameTextEdit = (EditText) findViewById(R.id.doctorNameTextEdit);
        String doctorName = doctorNameTextEdit.getText().toString();

        EditText nurseNameTextEdit = (EditText) findViewById(R.id.nurseNameTextEdit);
        String nurseName = nurseNameTextEdit.getText().toString();

        dbHelper = getHelper();
        RuntimeExceptionDao<User, String> userDao = dbHelper.getUserDao();
        userDao.delete(getUser());

        userDao.create(new User(name, doctorName, nurseName));
        Log.d(this.getClass().toString(), "zmiany danych użytkownika zapisane");
    }

    public void onButtonSaveNotificationSettingsClick(View view) {

        TimePicker time = (TimePicker) findViewById(R.id.timePicker);
        int hour, minute;
        if (Build.VERSION.SDK_INT < 23) {
            hour = time.getCurrentHour();
            minute = time.getCurrentMinute();
        } else {
            hour = time.getHour();
            minute = time.getMinute();
        }

        dbHelper = getHelper();

        RuntimeExceptionDao<InjectionsSchedule, Integer> injectionSchedulesDao = dbHelper.getInjectionsScheduleDao();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getInjectionsSchedule().getInjectionTime());
        injectionSchedulesDao.delete(getInjectionsSchedule());

        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        injectionSchedulesDao.create(new InjectionsSchedule(calendar.getTimeInMillis()));

        Log.d(this.getClass().toString(), "zmiana czasu notyfikacji zapisana");
        scheduleNewNotification(calendar.getTimeInMillis());

    }


    private void scheduleNewNotification(long injectionTime) {


        Intent broadcastIntent = new Intent(this, InjectionTimeAlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        Log.d(this.getClass().toString(), "nieaktualna notyfikacja usunieta");

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, injectionTime, AlarmManager.INTERVAL_DAY * 2, pendingIntent);
        Log.d(this.getClass().toString(), "alarm ustawiony na nowy czas w milisekundach: " + injectionTime);
    }

    public InjectionsSchedule getInjectionsSchedule() {
        dbHelper = getHelper();

        RuntimeExceptionDao<InjectionsSchedule, Integer> injectionSchedulesDao = dbHelper.getInjectionsScheduleDao();

        return injectionSchedulesDao.queryForAll().iterator().next();
    }
}

