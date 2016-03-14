package com.karolskora.msorgranizer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.helpers.DatabaseHelper;
import com.karolskora.msorgranizer.models.User;

import java.util.List;

public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> {


    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user=getUser();
        if(user==null) {
            Intent intent = new Intent(this, UserInformationsActivity.class);
            startActivity(intent);
        }
        else {
            setContentView(R.layout.layout_main);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
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
