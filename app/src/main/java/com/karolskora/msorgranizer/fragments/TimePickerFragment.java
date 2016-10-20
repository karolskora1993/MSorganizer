package com.karolskora.msorgranizer.fragments;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TimePicker;
import com.karolskora.msorgranizer.activities.MainActivity;
import com.karolskora.msorgranizer.broadcastReceivers.PostponedNotification;

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

        Intent broadcastIntent = new Intent(getActivity(), PostponedNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, injectionTime, pendingIntent);

        Intent intent=new Intent(getActivity(), MainActivity.class);
        intent.putExtra(TimePickerFragment.POSTPONED_INJECTION_TIME, injectionTime);
        startActivity(intent);
    }

}