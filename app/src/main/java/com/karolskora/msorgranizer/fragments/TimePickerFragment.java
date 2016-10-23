package com.karolskora.msorgranizer.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TimePicker;

import com.karolskora.msorgranizer.activities.MainActivity;
import com.karolskora.msorgranizer.java.DatabaseQueries;
import com.karolskora.msorgranizer.java.NotificationOrganizer;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private final static  int NOTIFICATION_ID=1;

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
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        long injectionTime=calendar.getTimeInMillis();

        DatabaseQueries.setPostponedInjection(getActivity(), injectionTime);

        NotificationOrganizer.oneTimeNotification(injectionTime, getActivity());
        Intent intent=new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
    }
}