package com.karolskora.msorgranizer.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "drug_suply")

public class DrugSupply {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private int doses;

    @DatabaseField
    private int notificationDoses;
    public DrugSupply(){

    }
    public DrugSupply(int doses, int notificationDoses){
        this.doses=doses;
        this.notificationDoses=notificationDoses;
    }

    public int getDoses() {
        return doses;
    }

    public void setDoses(int doses) {
        this.doses = doses;
    }

    public int getNotificationDoses() {
        return notificationDoses;
    }

    public void setNotificationDoses(int notificationDoses) {
        this.notificationDoses = notificationDoses;
    }
}
