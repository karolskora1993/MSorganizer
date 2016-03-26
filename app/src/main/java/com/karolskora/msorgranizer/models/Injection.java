package com.karolskora.msorgranizer.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "injection")

public class Injection  implements Serializable{

    @DatabaseField(id = true)
    private long timeInMilis;
    @DatabaseField
    private int area;
    @DatabaseField
    private int point;
    @DatabaseField
    private boolean temperature;
    @DatabaseField
    private boolean trembles;
    @DatabaseField
    private boolean ache;

    public Injection(){}

    public Injection(long timeInMilis, int area, int point){
        this.timeInMilis=timeInMilis;
        this.area=area;
        this.point=point;
        this.temperature=false;
        this.trembles=false;
        this.ache=false;
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

    public boolean isTemperature() {
        return temperature;
    }

    public void setTemperature(boolean temperature) {
        this.temperature = temperature;
    }

    public boolean isTrembles() {
        return trembles;
    }

    public void setTrembles(boolean trembles) {
        this.trembles = trembles;
    }

    public boolean isAche() {
        return ache;
    }

    public void setAche(boolean ache) {
        this.ache = ache;
    }
}
