package com.karolskora.msorgranizer.java;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.models.ApplicationSettings;
import com.karolskora.msorgranizer.models.DrugSupply;
import com.karolskora.msorgranizer.models.Injection;
import com.karolskora.msorgranizer.models.Notification;
import com.karolskora.msorgranizer.models.User;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "ms_organizer.db";
    private static final int DATABASE_VERSION = 1;

    private RuntimeExceptionDao<User, String> userRuntimeDao = null;

    private RuntimeExceptionDao<Notification, Integer> injectionsScheduleRuntimeDao = null;

    private RuntimeExceptionDao<Injection, Long> injectionRuntimeDao = null;

    private RuntimeExceptionDao<DrugSupply, Integer> drugSupplyRuntimeDao = null;

    private RuntimeExceptionDao<ApplicationSettings, Integer> applicationSettingsesRuntimeDao = null;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }


    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Notification.class);
            TableUtils.createTable(connectionSource, Injection.class);
            TableUtils.createTable(connectionSource, DrugSupply.class);
            TableUtils.createTable(connectionSource, ApplicationSettings.class);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Nie można utworzyć tabeli", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Notification.class, true);
            TableUtils.dropTable(connectionSource, Injection.class, true);
            TableUtils.dropTable(connectionSource, DrugSupply.class, true);
            TableUtils.dropTable(connectionSource, ApplicationSettings.class, true);

            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Nie można skasować tabeli", e);
            throw new RuntimeException(e);
        }
    }


    public RuntimeExceptionDao<User, String> getUserDao() {
        if (userRuntimeDao == null) {
            userRuntimeDao = getRuntimeExceptionDao(User.class);
        }
        return userRuntimeDao;
    }


    public RuntimeExceptionDao<Notification, Integer> getInjectionsScheduleDao() {
        if (injectionsScheduleRuntimeDao == null) {
            injectionsScheduleRuntimeDao = getRuntimeExceptionDao(Notification.class);
        }
        return injectionsScheduleRuntimeDao;
    }


    public RuntimeExceptionDao<Injection, Long> getInjectionDao() {
        if (injectionRuntimeDao == null) {
            injectionRuntimeDao = getRuntimeExceptionDao(Injection.class);
        }
        return injectionRuntimeDao;
    }

    public RuntimeExceptionDao<DrugSupply, Integer> getDrugSupplyDao() {
        if (drugSupplyRuntimeDao == null) {
            drugSupplyRuntimeDao = getRuntimeExceptionDao(DrugSupply.class);
        }
        return drugSupplyRuntimeDao;
    }

    public RuntimeExceptionDao<ApplicationSettings, Integer> getApplicationSettingsRuntimeDao() {
        if (applicationSettingsesRuntimeDao == null) {
            applicationSettingsesRuntimeDao = getRuntimeExceptionDao(ApplicationSettings.class);
        }
        return applicationSettingsesRuntimeDao;
    }

    @Override
    public void close() {
        super.close();
        userRuntimeDao = null;
        injectionsScheduleRuntimeDao=null;
        injectionRuntimeDao=null;
        drugSupplyRuntimeDao=null;
        applicationSettingsesRuntimeDao=null;
    }
}