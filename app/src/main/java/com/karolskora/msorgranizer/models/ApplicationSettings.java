package com.karolskora.msorgranizer.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by apple on 29.10.2016.
 */

@DatabaseTable(tableName = "notification")
public class ApplicationSettings {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private int appStyle;

    public ApplicationSettings(){}

    public ApplicationSettings(int appStyle){
        this.appStyle = appStyle;
    }

    public int getAppStyle() {
        return appStyle;
    }

    public void setAppStyle(int appStyle) {
        this.appStyle = appStyle;
    }
}