package com.karolskora.msorgranizer.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.karolskora.msorgranizer.activities.FirstInjectionTimeActivity;
import com.karolskora.msorgranizer.activities.MainActivity;
import com.karolskora.msorgranizer.helpers.DatabaseHelper;
import com.karolskora.msorgranizer.models.InjectionsSchedule;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        Bundle bundle =this.getArguments();

        int hour=bundle.getInt(FirstInjectionTimeActivity.HOUR);
        int minute=bundle.getInt(FirstInjectionTimeActivity.MINUTE);

        year=view.getYear();
        month=view.getMonth();
        day=view.getDayOfMonth();

        Date firstInjectionDate=new Date(year,month,day,hour,minute);
        Log.i(this.getClass().toString(), "Próba zapisania ustawień powiadomień do bazy danych");

        InjectionsSchedule schedule=new InjectionsSchedule(firstInjectionDate);

        DatabaseHelper dbHelper= OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);

        RuntimeExceptionDao<InjectionsSchedule, Integer> injectionsScheduleDao =dbHelper.getInjectionsScheduleDao();
        injectionsScheduleDao.create(schedule);

        OpenHelperManager.releaseHelper();
        Log.i(this.getClass().toString(),"Zapisano ustawienia w bazie danych");

        Intent intent=new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}