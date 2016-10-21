package com.karolskora.msorgranizer.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;
import com.karolskora.msorgranizer.java.NotificationOrganizer;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private final static  int NOTIFICATION_ID=1;
    public final static String POSTPONED_INJECTION_TIME="postponedInjectionTime";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
               true);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        long injectionTime=calendar.getTimeInMillis();

        NotificationOrganizer.oneTimeNotification(injectionTime, getActivity());
    }

}