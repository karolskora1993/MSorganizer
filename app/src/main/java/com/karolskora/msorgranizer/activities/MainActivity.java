package com.karolskora.msorgranizer.activities;

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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.broadcastReceivers.InjectionTimeAlarmReceiver;
import com.karolskora.msorgranizer.fragments.AboutFragment;
import com.karolskora.msorgranizer.fragments.HistoryFragment;
import com.karolskora.msorgranizer.fragments.InjectionDetailsFragment;
import com.karolskora.msorgranizer.fragments.MainFragment;
import com.karolskora.msorgranizer.fragments.ReserveFragment;
import com.karolskora.msorgranizer.fragments.SettingsFragment;
import com.karolskora.msorgranizer.java.DatabaseHelper;
import com.karolskora.msorgranizer.java.DatabaseQueries;
import com.karolskora.msorgranizer.models.Injection;
import com.karolskora.msorgranizer.models.Notification;
import com.karolskora.msorgranizer.models.User;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private DatabaseHelper dbHelper;
    private String[] titles;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private User user;


    private class DrawerItemClickListener implements ListView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_main);

        titles = getResources().getStringArray(R.array.titles);
        drawerList = (ListView) findViewById(R.id.drawer);
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

        drawerLayout.addDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if (savedInstanceState == null)
            selectItem(0);

        user = getUser();
        if (user == null) {
            Intent intent = new Intent(this, UserInformationsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.itemMain:
                return true;
            case R.id.itemHistory:
                return true;
            case R.id.itemReserve:
                return true;
            case R.id.itemAppSettings:
                return true;
            case R.id.itemScheduleSettings:
                return true;
            case R.id.itemHelp:
                return true;
            case R.id.itemAbout:
                return true;
            default:
                return super.onOptionsItemSelected(item);
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

        getSupportActionBar().setTitle(title);

    }

    public User getUser() {

        dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);

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

        DatabaseQueries.updateUser(this, name, doctorName, nurseName);
        Toast toast = Toast.makeText(this, "zmieniono ustawienia użytkownika", Toast.LENGTH_LONG);
        toast.show();
    }
    public void onButtonSaveClick(View view) {
        EditText dosesEditText=(EditText)findViewById(R.id.dosesFragmentEditText);
        EditText notificationDosesEditText=(EditText)findViewById(R.id.notificationDosesFragmentEditText);

        int doses=Integer.parseInt(dosesEditText.getText().toString());
        int notificationDoses=Integer.parseInt(notificationDosesEditText.getText().toString());

        DatabaseQueries.updateDoses(this, doses, notificationDoses);
        Toast toast = Toast.makeText(this, "Zapisano ustawienia leku", Toast.LENGTH_LONG);
        toast.show();
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

        dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);

        RuntimeExceptionDao<Notification, Integer> injectionSchedulesDao = dbHelper.getInjectionsScheduleDao();
        Notification notification =injectionSchedulesDao.queryForAll().iterator().next();

        Injection lastInjection =DatabaseQueries.getLatestInjection(this);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lastInjection.getTimeInMilis());
        Log.d(this.getClass().toString(), "Data ostatniego zastrzyku: rok:" + calendar.get(Calendar.YEAR) + " miesiac: " + calendar.get(Calendar.MONTH) +
                " dzien: " + calendar.get(Calendar.DAY_OF_MONTH) + " godzina: " + calendar.get(Calendar.HOUR) + " minuta: " + calendar.get(Calendar.MINUTE));

        injectionSchedulesDao.delete(notification);

        calendar.setTimeInMillis(calendar.getTimeInMillis() + 48 * 60 * 60 * 1000);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        injectionSchedulesDao.create(new Notification(calendar.getTimeInMillis()));

        Log.d(this.getClass().toString(), "Nowy czas notyfikacji: rok:" + calendar.get(Calendar.YEAR)+" miesiac: "+calendar.get(Calendar.MONTH)+
                " dzien: "+calendar.get(Calendar.DAY_OF_MONTH)+" godzina: "+calendar.get(Calendar.HOUR)+" minuta: "+calendar.get(Calendar.MINUTE));
        scheduleNewNotification(calendar.getTimeInMillis());

        Toast toast = Toast.makeText(this, "Zmieniono ustawienia notyfikacji", Toast.LENGTH_LONG);
        toast.show();

    }
    public void onButtonSaveSymptomsClick(View view) {

        CheckBox temperatureCheckBox=(CheckBox)findViewById(R.id.temperatureCheckBox);
        CheckBox tremblesCheckBox=(CheckBox)findViewById(R.id.tremblesCheckBox);
        CheckBox acheCheckBox=(CheckBox)findViewById(R.id.acheCheckBox);


        boolean temperature=temperatureCheckBox.isChecked();
        boolean trembles=tremblesCheckBox.isChecked();
        boolean ache=acheCheckBox.isChecked();

        InjectionDetailsFragment fragment = (InjectionDetailsFragment)getFragmentManager().findFragmentByTag("LAST_INJECTION_FRAGMENT");

        Injection injection=fragment.getInjection();
        DatabaseQueries.updateInjection(this, injection, temperature, trembles, ache);

        Toast toast = Toast.makeText(this, "Zaktualizowano objawy", Toast.LENGTH_LONG);
        toast.show();

    }


    private void scheduleNewNotification(long injectionTime) {


        Intent broadcastIntent = new Intent(this, InjectionTimeAlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, injectionTime, AlarmManager.INTERVAL_DAY * 2, pendingIntent);
    }

    public Notification getInjectionsSchedule() {
        dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);

        RuntimeExceptionDao<Notification, Integer> injectionSchedulesDao = dbHelper.getInjectionsScheduleDao();

        return injectionSchedulesDao.queryForAll().iterator().next();
    }
}

