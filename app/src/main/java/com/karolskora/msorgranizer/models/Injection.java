package com.karolskora.msorgranizer.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "injection")

public class Injection {

    @DatabaseField(id = true)
    private long timeInMilis;
    @DatabaseField
    private int area;
    @DatabaseField
    private int point;

    public Injection(){}

    public Injection(long timeInMilis, int area, int point){
        this.timeInMilis=timeInMilis;
        this.area=area;
        this.point=point;
    }

    public long getTimeInMilis() {
        return timeInMilis;
    }

    public int getArea() {
        return area;
    }

    public int getPoint() {
        return point;
    }

}
