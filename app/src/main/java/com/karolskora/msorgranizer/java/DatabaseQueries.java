package com.karolskora.msorgranizer.java;


import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.karolskora.msorgranizer.models.Injection;

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
        return injections;
    }
    public static Injection getLatestInjection(Context activity){
        if(dbHelper==null)
            dbHelper= OpenHelperManager.getHelper(activity,DatabaseHelper.class);

        RuntimeExceptionDao<Injection, Long> injetionDao=dbHelper.getInjectionDao();
        List<Injection> injections=injetionDao.queryForAll();
        Iterator<Injection> it=injections.iterator();
        while(it.hasNext())
            it.next();

        return it.next();
    }
}
