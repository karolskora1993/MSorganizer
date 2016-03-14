package com.karolskora.msorgranizer.models;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Calendar;
import java.util.Date;


@DatabaseTable(tableName = "injections_schedule")
public class InjectionsSchedule {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private long firstInjectionDateInMilis;

    @DatabaseField
    private int totalNumberOfInjections;

    @DatabaseField
    private int round;

    public InjectionsSchedule(){}

    public InjectionsSchedule(long firstInjectionDateInMilis){
        this.firstInjectionDateInMilis=firstInjectionDateInMilis;
        this.totalNumberOfInjections=0;
        this.round=0;
    }

    public long getInjectionTime(){

       return this.firstInjectionDateInMilis;
    }
}
