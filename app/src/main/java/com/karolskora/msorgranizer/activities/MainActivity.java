package com.karolskora.msorgranizer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
            Intent intent = new Intent(this, InitialSettingsActivity.class);
            startActivity(intent);
        }
        else {
            setContentView(R.layout.layout_main);
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
