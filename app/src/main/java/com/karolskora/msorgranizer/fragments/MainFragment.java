package com.karolskora.msorgranizer.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.activities.MainActivity;
import com.karolskora.msorgranizer.java.DatabaseQueries;
import com.karolskora.msorgranizer.models.Injection;
import com.karolskora.msorgranizer.models.InjectionsSchedule;

import java.util.Calendar;

public class MainFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainActivity owner=(MainActivity)getActivity();
        if (owner.getUser()!=null) {
            TextView textView = (TextView) owner.findViewById(R.id.fragmentMainTextView);
            String name = "Witaj " + DatabaseQueries.getUser(getActivity()).getName() + ", do następnego zastrzyku pozostało:";
            textView.setText(name);

            TextView timeTextView = (TextView) owner.findViewById(R.id.timeToInjectionTextView);
            timeTextView.setText(getTimeToInjection());
        }
    }
    private String getTimeToInjection(){
        InjectionsSchedule injectionsSchedule=DatabaseQueries.getInjectionsSchedule(getActivity());
        Injection lastInjection=DatabaseQueries.getLatestInjection(getActivity());
        if(lastInjection!=null) {
            Calendar lastInjectionTime = Calendar.getInstance();
            lastInjectionTime.setTimeInMillis(lastInjection.getTimeInMilis());
            Calendar currentTime = Calendar.getInstance();
            Log.d(getClass().toString(), "Obecny czas:" + currentTime.get(Calendar.YEAR) + " miesiac:" + currentTime.get(Calendar.MONTH) +
                    "dzien: " + currentTime.get(Calendar.DAY_OF_MONTH) + "godzina:" + currentTime.get(Calendar.HOUR_OF_DAY) + "minuta:" + currentTime.get(Calendar.MINUTE));

            Calendar notificationTime = Calendar.getInstance();
            notificationTime.setTimeInMillis(injectionsSchedule.getInjectionTime());

            notificationTime.set(Calendar.YEAR, lastInjectionTime.get(Calendar.YEAR));
            notificationTime.set(Calendar.MONTH, lastInjectionTime.get(Calendar.MONTH));
            notificationTime.set(Calendar.DAY_OF_MONTH, lastInjectionTime.get(Calendar.DAY_OF_MONTH));
            notificationTime.setTimeInMillis(notificationTime.getTimeInMillis()+47*60*60*1000);
            Log.d(getClass().toString(), "Czas najblizszej notyfikacji, rok:" + notificationTime.get(Calendar.YEAR) + " miesiac:" + notificationTime.get(Calendar.MONTH) +
                    "dzien: " + notificationTime.get(Calendar.DAY_OF_MONTH)+ "godzina:"+notificationTime.get(Calendar.HOUR_OF_DAY)+"minuta:"+
                    notificationTime.get(Calendar.MINUTE));

            Long timeToInjection = notificationTime.getTimeInMillis() -currentTime.getTimeInMillis();
            Log.d(getClass().toString(), "czas do notyfikacji w milis:"+timeToInjection);
            int hours = (int) (timeToInjection / (1000 * 60 * 60));
            int minutes = (int) ((timeToInjection - (hours * 60 * 60 * 1000)) / (60  * 1000));
            String time;
            if(timeToInjection>=48*60*60*1000)
            {
                int days=2;
                hours=hours-24;
                time = days+"dni \n"+ hours + "godzin\n" + minutes + "minut";

            }
            else if(timeToInjection>=24*60*60*1000)
            {
                int days=1;
                hours=hours-24;
                time = days+"dzień \n"+ hours + "godzin\n" + minutes + "minut";

            }
            else
                time = hours + "godzin\n" + minutes + "minut";
            return time;
        }
        else{
            Calendar currentTime = Calendar.getInstance();
            Log.d(getClass().toString(), "Obecny czas:" + currentTime.get(Calendar.YEAR) + " miesiac:" + currentTime.get(Calendar.MONTH) +
                    "dzien: " + currentTime.get(Calendar.DAY_OF_MONTH));
            Long timeToInjection = currentTime.getTimeInMillis() - injectionsSchedule.getInjectionTime();

            int hours = (int) (timeToInjection / 1000 * 60 * 60);
            int minutes = (int) ((timeToInjection - hours * 60 * 60 * 1000) / 60 * 60 * 1000);
            String time;
            if(timeToInjection>24*60*60*1000)
            {
                int days=1;
                hours=hours-(24*60*60*1000);
                time = days+"dni \n"+ hours + "godzin\n" + minutes + "minut";

            }
            else
                time = hours + "godzin\n" + minutes + "minut";

            return time;
        }

    }
}
