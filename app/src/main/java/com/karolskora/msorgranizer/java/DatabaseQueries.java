package com.karolskora.msorgranizer.java;


import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.karolskora.msorgranizer.models.DrugSupply;
import com.karolskora.msorgranizer.models.Injection;
import com.karolskora.msorgranizer.models.Notification;
import com.karolskora.msorgranizer.models.User;

import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class DatabaseQueries {

    private static DatabaseHelper dbHelper;

    private DatabaseQueries(){}

    public static Notification getInjectionsSchedule(Context activity){
        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<Notification, Integer> injectionsScheduleDao=dbHelper.getInjectionsScheduleDao();
        Notification notification =injectionsScheduleDao.queryForAll().iterator().next();

        return notification;
    }

    public static void updateInjectionSchedule(Context activity, long newTimeInMilis){
        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<Notification, Integer> injectionsScheduleDao=dbHelper.getInjectionsScheduleDao();
        Notification notification =injectionsScheduleDao.queryForAll().iterator().next();

        injectionsScheduleDao.delete(notification);

        injectionsScheduleDao.create(new Notification(newTimeInMilis));
    }

    public static List<Injection> getInjections(Context activity){
        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<Injection, Long> injetionDao=dbHelper.getInjectionDao();
        List<Injection> injections=injetionDao.queryForAll();
        Collections.reverse(injections);
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

    public static void updateUser(Context activity, String name, String doctorName, String nurseName, String email){
        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<User, String> userDao = dbHelper.getUserDao();
        userDao.delete(DatabaseQueries.getUser(activity));

        userDao.create(new User(name, doctorName, nurseName, email));
        Log.d(DatabaseQueries.class.toString(), "zmiany danych użytkownika zapisane");
    }

    public static void addInjection(Context activity, long timeInMilis, int area, int point) {

        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<Injection, Long> injectionDao = dbHelper.getInjectionDao();
        injectionDao.create(new Injection(timeInMilis, area, point));
        Calendar lastInjectionTime=Calendar.getInstance();
        lastInjectionTime.setTimeInMillis(timeInMilis);
        Log.d(DatabaseQueries.class.toString(), "nowy zastrzyk zapisany do bazy, rok:" + lastInjectionTime.get(Calendar.YEAR) + " miesiac:" + lastInjectionTime.get(Calendar.MONTH) +
                "dzien: " + lastInjectionTime.get(Calendar.DAY_OF_MONTH));
    }

    public static void updateInjection(Context activity, Injection injection, boolean temperature, boolean trembles, boolean ache) {

        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<Injection, Long> injectionDao = dbHelper.getInjectionDao();

        injection.setTemperature(temperature);
        injection.setTrembles(trembles);
        injection.setAche(ache);
        injectionDao.update(injection);

        Log.d(DatabaseQueries.class.toString(), "Objawy dodane do zastrzyku:");
    }

    public static int getDoses(Context activity) {

        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<DrugSupply, Integer> drugSupplyDao = dbHelper.getDrugSupplyDao();

        DrugSupply drugSupply=drugSupplyDao.queryForAll().iterator().next();
        if(drugSupply!=null){
            return drugSupply.getDoses();
        }
        else
            return -1;
    }

    public static int getNotificationDoses(Context activity) {

        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<DrugSupply, Integer> drugSupplyDao = dbHelper.getDrugSupplyDao();

        DrugSupply drugSupply=drugSupplyDao.queryForAll().iterator().next();
        if(drugSupply!=null){
            return drugSupply.getNotificationDoses();
        }
        else
            return 9999;
    }

    public static void updateDoses(Context activity, int doses){
        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<DrugSupply, Integer> drugSupplyDao = dbHelper.getDrugSupplyDao();

        DrugSupply drugSupply=drugSupplyDao.queryForAll().iterator().next();

        int notificationDoses=drugSupply.getNotificationDoses();

        drugSupplyDao.delete(drugSupply);

        DrugSupply newDrugSupply=new DrugSupply(doses, notificationDoses);

        drugSupplyDao.create(newDrugSupply);
    }

    public static void updateDoses(Context activity, int doses, int notificationDoses){
        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<DrugSupply, Integer> drugSupplyDao = dbHelper.getDrugSupplyDao();

        DrugSupply drugSupply=drugSupplyDao.queryForAll().iterator().next();

        drugSupplyDao.delete(drugSupply);

        DrugSupply newDrugSupply=new DrugSupply(doses, notificationDoses);

        drugSupplyDao.create(newDrugSupply);
    }

    public static void setDoses(Context activity, int doses, int notificationDoses){
        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<DrugSupply, Integer> drugSupplyDao = dbHelper.getDrugSupplyDao();


        DrugSupply newDrugSupply=new DrugSupply(doses, notificationDoses);

        drugSupplyDao.create(newDrugSupply);
    }

    public static void putUser(Context activity, String name, String doctorName, String nurseName, String email) {
        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<User, String> userDao = dbHelper.getUserDao();
        userDao.create(new User(name, doctorName, nurseName, email));
    }

    public static void putInjectionsSchedule(Context activity, long timeInMilis){
        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<Notification, Integer> injectionsScheduleDao = dbHelper.getInjectionsScheduleDao();
        injectionsScheduleDao.create(new Notification(timeInMilis));

    }
}
