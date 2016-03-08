package com.karolskora.msorgranizer.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;


@DatabaseTable(tableName = "injections_schedule")
public class InjectionsSchedule {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private Date firstInjectionDate;

    @DatabaseField
    private int totalNumberOfInjections;

    @DatabaseField
    private int round;

    public InjectionsSchedule(){}

    public InjectionsSchedule(Date firstInjectionDate){
        this.firstInjectionDate=firstInjectionDate;
        this.totalNumberOfInjections=0;
        this.round=0;
    }
}
