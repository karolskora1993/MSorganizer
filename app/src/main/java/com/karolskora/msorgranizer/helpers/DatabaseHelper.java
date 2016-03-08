package com.karolskora.msorgranizer.helpers;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.models.InjectionsSchedule;
import com.karolskora.msorgranizer.models.User;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "ms_organizer.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<User, String> userDao = null;
    private RuntimeExceptionDao<User, String> userRuntimeDao = null;

    private Dao<InjectionsSchedule, Integer> injectionsScheduleDao = null;
    private RuntimeExceptionDao<InjectionsSchedule, Integer> injectionsScheduleRuntimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }


    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, InjectionsSchedule.class);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, InjectionsSchedule.class, true);

            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }


    /*public Dao<User, String> getDao() throws SQLException {
        if (userDao == null) {
            userDao = getDao(User.class);
        }
        return userDao;
    }*/

    public RuntimeExceptionDao<User, String> getUserDao() {
        if (userRuntimeDao == null) {
            userRuntimeDao = getRuntimeExceptionDao(User.class);
        }
        return userRuntimeDao;
    }

    /*public Dao<InjectionsSchedule, Integer> getDao() throws SQLException {
        if (injectionsScheduleDao == null) {
            injectionsScheduleDao = getDao(InjectionsSchedule.class);
        }
        return injectionsScheduleDao;
    }*/

    public RuntimeExceptionDao<InjectionsSchedule, Integer> getInjectionsScheduleDao() {
        if (injectionsScheduleRuntimeDao == null) {
            injectionsScheduleRuntimeDao = getRuntimeExceptionDao(InjectionsSchedule.class);
        }
        return injectionsScheduleRuntimeDao;
    }

    @Override
    public void close() {
        super.close();
        userDao = null;
        userRuntimeDao = null;
        injectionsScheduleDao=null;
        injectionsScheduleRuntimeDao=null;
    }
}