package com.karolskora.msorgranizer.fragments;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.activities.MainActivity;
import com.karolskora.msorgranizer.java.DatabaseQueries;
import com.karolskora.msorgranizer.java.NotificationOrganizer;
import com.karolskora.msorgranizer.models.Notification;
import java.util.Calendar;

public class NotificationSettingsFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.fragment_notification_settings, container, false);

        return inflater.inflate(R.layout.fragment_notification_settings, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TimePicker timePicker=(TimePicker)getActivity().findViewById(R.id.notificationSettingsTimePicker);
        MainActivity mainActivity=(MainActivity)getActivity();

        Notification notification =mainActivity.getInjectionsSchedule();

        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(notification.getInjectionTime());

        int hour=calendar.get(Calendar.HOUR);
        int minute=calendar.get(Calendar.MINUTE);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
    }

    public void changeInjectionsTimeClick() {
        TimePicker time = (TimePicker)getActivity().findViewById(R.id.notificationSettingsTimePicker);
        int hour, minute;
        if (Build.VERSION.SDK_INT < 23) {
            hour = time.getCurrentHour();
            minute = time.getCurrentMinute();
        } else {
            hour = time.getHour();
            minute = time.getMinute();
        }

        Notification notification = DatabaseQueries.getInjectionsSchedule(getActivity());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(notification.getInjectionTime());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        DatabaseQueries.updateInjectionSchedule(getActivity(), calendar.getTimeInMillis());

        Log.d(this.getClass().toString(), "Nowy czas notyfikacji: rok:" + calendar.get(Calendar.YEAR) + " miesiac: " + calendar.get(Calendar.MONTH) +
                " dzien: " + calendar.get(Calendar.DAY_OF_MONTH) + " godzina: " + calendar.get(Calendar.HOUR) + " minuta: " + calendar.get(Calendar.MINUTE) + calendar.get(Calendar.AM_PM));
        NotificationOrganizer.UpdateNotification(calendar.getTimeInMillis(), getActivity());
        Toast toast = Toast.makeText(getActivity(), "Zmieniono ustawienia notyfikacji", Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
            try {
                getView().findViewById(R.id.notificationSettingsButtonSave).setOnClickListener(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onClick(View v) {
        changeInjectionsTimeClick();
    }
}
