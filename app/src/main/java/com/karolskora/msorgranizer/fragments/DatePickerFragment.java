package com.karolskora.msorgranizer.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import com.karolskora.msorgranizer.activities.AboutAppActivity;
import com.karolskora.msorgranizer.activities.FirstInjectionTimeActivity;
import com.karolskora.msorgranizer.activities.UserInformationsActivity;


import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public static final String TIME_IN_MILIS="time_in_milis";




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

        String name=getActivity().getIntent().getStringExtra(UserInformationsActivity.USER_NAME);
        String doctorName=getActivity().getIntent().getStringExtra(UserInformationsActivity.DOCTOR_NAME);
        String nurseName=getActivity().getIntent().getStringExtra(UserInformationsActivity.NURSE_NAME);


        int hour=bundle.getInt(FirstInjectionTimeActivity.HOUR);
        int minute=bundle.getInt(FirstInjectionTimeActivity.MINUTE);

        year=view.getYear();
        month=view.getMonth();
        day=view.getDayOfMonth();

        Calendar calendar=Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        Intent intent=new Intent(getActivity(), AboutAppActivity.class);
        intent.putExtra(TIME_IN_MILIS, calendar.getTimeInMillis());
        intent.putExtra(UserInformationsActivity.USER_NAME, name);
        intent.putExtra(UserInformationsActivity.DOCTOR_NAME, doctorName);
        intent.putExtra(UserInformationsActivity.NURSE_NAME, nurseName);
        startActivity(intent);
    }
}