package com.karolskora.msorgranizer.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.fragments.AboutFragment;
import com.karolskora.msorgranizer.fragments.HelpFragment;
import com.karolskora.msorgranizer.fragments.HistoryFragment;
import com.karolskora.msorgranizer.fragments.MainFragment;
import com.karolskora.msorgranizer.fragments.ReserveFragment;
import com.karolskora.msorgranizer.fragments.SettingsFragment;
import com.karolskora.msorgranizer.helpers.DatabaseHelper;
import com.karolskora.msorgranizer.models.User;

import java.util.List;

public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> {


    private DatabaseHelper dbHelper;
    private String[] titles;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    public void onButtonSaveUserInfoClick(View view) {
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener{


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
        }


        if (savedInstanceState == null)
            selectItem(0);
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


    private void selectItem(int position){

        Fragment fragment;
        switch(position)
        {
            case 1:
                fragment=new HistoryFragment();
                break;
            case 2:
                fragment=new ReserveFragment();
                break;
            case 3:
                fragment=new SettingsFragment();
                break;
            case 4:
                fragment=new HelpFragment();
                break;
            case 5:
                fragment=new AboutFragment();
                break;
            default:
                fragment=new MainFragment();
        }

        FragmentTransaction ft= getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        setActionBarTitle(position);

        DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(drawerList);
    }

    public void setActionBarTitle(int position){
        String title;
        if(position==0){
            title=getResources().getString(R.string.app_name);
        }
        else
            title=this.titles[position];

        getActionBar().setTitle(title);

    }

    public User getUser(){

        dbHelper=getHelper();

        RuntimeExceptionDao<User, String> userDao=dbHelper.getUserDao();

        List<User> users= userDao.queryForAll();

        if(users.isEmpty())
            return null;
        else
            return users.iterator().next();
    }
}

