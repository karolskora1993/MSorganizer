package com.karolskora.msorgranizer.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;


@DatabaseTable(tableName = "injections_schedule")
public class InjectionsSchedule {

    @DatabaseField
    private Date firstInjectionDate;
}
