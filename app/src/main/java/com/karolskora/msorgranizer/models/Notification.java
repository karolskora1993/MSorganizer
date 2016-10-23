package com.karolskora.msorgranizer.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "notification")
public class Notification {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private long notificationTime;

    @DatabaseField
    private long postoponedNotificationTime;

    public Notification(){}

    public Notification(long notificationTime){
        this.notificationTime = notificationTime;
        this.postoponedNotificationTime = 0;
    }

    public long getInjectionTime(){

       return this.notificationTime;
    }

    public long getPostoponedNotificationTime() {
        return postoponedNotificationTime;
    }

    public void setPostoponedNotificationTime(long postoponedNotificationTime) {
        this.postoponedNotificationTime = postoponedNotificationTime;
    }
}
