package com.karolskora.msorgranizer.java;


import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.karolskora.msorgranizer.models.Injection;
import com.karolskora.msorgranizer.models.User;

import java.util.Iterator;
import java.util.List;

/**
 * Created by apple on 24.03.2016.
 */
public class DatabaseQueries {

    private static DatabaseHelper dbHelper;

    private DatabaseQueries(){}

    public static List<Injection> getInjections(Context activity){
        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<Injection, Long> injetionDao=dbHelper.getInjectionDao();
        List<Injection> injections=injetionDao.queryForAll();
        OpenHelperManager.releaseHelper();
        return injections;
    }

    public static Injection getLatestInjection(Context activity){
        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<Injection, Long> injetionDao=dbHelper.getInjectionDao();
        List<Injection> injections=injetionDao.queryForAll();

        OpenHelperManager.releaseHelper();
        if(!injections.isEmpty())
            return injections.get(injections.size()-1);
        else
            return null;
    }

    public static User getUser(Context activity) {

        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<User, String> userDao = dbHelper.getUserDao();

        List<User> users = userDao.queryForAll();
        OpenHelperManager.releaseHelper();


        if (users.isEmpty())
            return null;
        else
            return users.iterator().next();
    }

    public static void updateUser(Context activity, String name, String doctorName, String nurseName){
        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<User, String> userDao = dbHelper.getUserDao();
        userDao.delete(DatabaseQueries.getUser(activity));

        userDao.create(new User(name, doctorName, nurseName));
        Log.d(DatabaseQueries.class.toString(), "zmiany danych użytkownika zapisane");
        OpenHelperManager.releaseHelper();
    }
    public static void addInjection(Context activity, long timeInMilis, int area, int point) {

        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<Injection, Long> injectionDao = dbHelper.getInjectionDao();
        injectionDao.create(new Injection(timeInMilis, area, point));
        OpenHelperManager.releaseHelper();
    }
}
