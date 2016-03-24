package com.karolskora.msorgranizer.fragments;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.activities.InjectionActivity;
import com.karolskora.msorgranizer.broadcastReceivers.InjectionTimeAlarmReceiver;
import com.karolskora.msorgranizer.broadcastReceivers.PostponedNotification;

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
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Calendar calendar=Calendar.getInstance();
        long injectionTime=calendar.getTimeInMillis();


        Intent broadcastIntent = new Intent(getActivity(), PostponedNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, injectionTime, AlarmManager.INTERVAL_DAY * 2, pendingIntent);
        Log.d(this.getClass().toString(), "alarm ustawiony na czas w milisekundach: "+injectionTime);

    }

}