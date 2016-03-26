package com.karolskora.msorgranizer.java;


import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.karolskora.msorgranizer.models.Injection;
import com.karolskora.msorgranizer.models.InjectionsSchedule;
import com.karolskora.msorgranizer.models.User;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by apple on 24.03.2016.
 */
public class DatabaseQueries {

    private static DatabaseHelper dbHelper;

    private DatabaseQueries(){}

    public static InjectionsSchedule getInjectionsSchedule(Context activity){
        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<InjectionsSchedule, Integer> injectionsScheduleDao=dbHelper.getInjectionsScheduleDao();
        InjectionsSchedule injectionsSchedule=injectionsScheduleDao.queryForAll().iterator().next();

        return injectionsSchedule;
    }

    public static List<Injection> getInjections(Context activity){
        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<Injection, Long> injetionDao=dbHelper.getInjectionDao();
        List<Injection> injections=injetionDao.queryForAll();
        return injections;
    }

    public static Injection getLatestInjection(Context activity){
        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<Injection, Long> injetcionDao=dbHelper.getInjectionDao();
        List<Injection> injections=injetcionDao.queryForAll();
        Iterator<Injection> injectionsIterator=injections.iterator();

        Calendar lastInjectionTime=Calendar.getInstance();
        while (injectionsIterator.hasNext()) {
            lastInjectionTime.setTimeInMillis(injectionsIterator.next().getTimeInMilis());
            Log.d(DatabaseQueries.class.toString(), "zastrzyk:" + lastInjectionTime.get(Calendar.YEAR) + " miesiac:" + lastInjectionTime.get(Calendar.MONTH) +
                    "dzien: " + lastInjectionTime.get(Calendar.DAY_OF_MONTH));
        }

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
    }

    public static void addInjection(Context activity, long timeInMilis, int area, int point) {

        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<Injection, Long> injectionDao = dbHelper.getInjectionDao();
        injectionDao.create(new Injection(timeInMilis, area, point));
        Calendar lastInjectionTime=Calendar.getInstance();
        lastInjectionTime.setTimeInMillis(timeInMilis);
        Log.d(DatabaseQueries.class.toString(), "ostatni zastrzyk zapisany do bazy, rok:" + lastInjectionTime.get(Calendar.YEAR) + " miesiac:" + lastInjectionTime.get(Calendar.MONTH) +
                "dzien: " + lastInjectionTime.get(Calendar.DAY_OF_MONTH));
    }
}
