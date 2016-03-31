package com.karolskora.msorgranizer.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;



@DatabaseTable(tableName = "notification")
public class Notification {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private long notificationTime;

    public Notification(){}

    public Notification(long notificationTime){
        this.notificationTime = notificationTime;
    }

    public long getInjectionTime(){

       return this.notificationTime;
    }
}
