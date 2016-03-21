package com.karolskora.msorgranizer.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;


import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.activities.MainActivity;
import com.karolskora.msorgranizer.models.InjectionsSchedule;

import java.util.Calendar;


public class NotificationSettingsFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification_settings, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TimePicker timePicker=(TimePicker)getActivity().findViewById(R.id.timePicker);
        MainActivity mainActivity=(MainActivity)getActivity();

        InjectionsSchedule injectionsSchedule=mainActivity.getInjectionsSchedule();

        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(injectionsSchedule.getInjectionTime());

        int hour=calendar.get(Calendar.HOUR);
        int minute=calendar.get(Calendar.MINUTE);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
        timePicker.setIs24HourView(true);

    }
}
