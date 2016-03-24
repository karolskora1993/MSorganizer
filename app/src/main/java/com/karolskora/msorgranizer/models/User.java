package com.karolskora.msorgranizer.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user")
public class User {

    @DatabaseField(id=true)
    private String name;
    @DatabaseField
    private String doctorName;
    @DatabaseField
    private String nurseName;

    public User(){}

    public User(String name, String doctorName, String nurseName) {
        this.name = name;
        this.doctorName = doctorName;
        this.nurseName = nurseName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoctorName() {
        return doctorName;
    }


    public String getNurseName() {
        return nurseName;
    }

}
