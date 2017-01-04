package com.karolskora.msorgranizer.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.activities.InjectionActivity;
import com.karolskora.msorgranizer.activities.MainActivity;
import com.karolskora.msorgranizer.java.DatabaseQueries;
import com.karolskora.msorgranizer.models.Injection;
import com.karolskora.msorgranizer.models.Notification;
import java.util.Calendar;

public class ToInjectionFragment extends Fragment implements View.OnClickListener{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_to_injection, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity owner=(MainActivity)getActivity();

//        RelativeLayout layout =(RelativeLayout)getActivity().findViewById(R.id.fragment_to_injection);
//        layout.setBackgroundResource(android.R.drawable.dialog_holo_dark_frame);

        TextView textView = (TextView) owner.findViewById(R.id.fragmentMainTextView);

        if(textView!=null) {
                String name = "Witaj " + DatabaseQueries.getUser(getActivity()).getName() + ", do następnego zastrzyku pozostało:";
                textView.setText(name);

                TextView timeTextView = (TextView) owner.findViewById(R.id.timeToInjectionTextView);
                timeTextView.setText(getTimeToInjection());
        }
    }


    private String getTimeToInjection(){
        Calendar calendar=Calendar.getInstance();
        Injection lastInjection = DatabaseQueries.getLatestInjection(getActivity());
        Notification notification =DatabaseQueries.getInjectionsSchedule(getActivity());
        if(lastInjection == null) {
            calendar.setTimeInMillis(notification.getInjectionTime());
        } else {
            calendar.setTimeInMillis(notification.getInjectionTime());
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int AM_PM = calendar.get(Calendar.AM_PM);

            calendar.setTimeInMillis(lastInjection.getTimeInMilis() + 48 *60 * 60 * 1000);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.AM_PM, AM_PM);
        }

        long postponedInectionTime= notification.getPostoponedNotificationTime();

        if(postponedInectionTime>0)
        {
            Long timeToInjection =  postponedInectionTime-Calendar.getInstance().getTimeInMillis();
            if(timeToInjection <= 0 ) {
                return "Wykonaj zastrzyk teraz";
            }
            int hours = (int) (timeToInjection / (1000 * 60 * 60));
            int minutes = (int) ((timeToInjection - (hours * 60 * 60 * 1000)) / (60  * 1000));
            String time=hours+"h:"+minutes+"min";
            return time;
        }
        else{
            Long timeToInjection = calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
            int hours = (int) (timeToInjection / (1000 * 60 * 60));
            int minutes = (int) ((timeToInjection - (hours * 60 * 60 * 1000)) / (60 * 1000));
            String time;
            if(timeToInjection <= 0 ) {
                return "Wykonaj zastrzyk teraz";
            }
            if(timeToInjection>=48*60*60*1000)
            {
                int days=2;
                hours=hours-48;
                time = days+"dni \n"+ hours + "godzin\n" + minutes + "minut";

            }
            else if(timeToInjection>24*60*60*1000)
            {
                int days=1;
                hours=hours-24;
                time = days+"dzień \n"+ hours + "godzin\n" + minutes + "minut";

            }
            else {
                time = hours + "godzin\n" + minutes + "minut";
            }
            return time;
        }

    }

    public void onButtonInjectClick() {
        Intent intent=new Intent(getActivity(), InjectionActivity.class);
        startActivity(intent);
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        try {
            getView().findViewById(R.id.fragmentToInjectionButton).setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        onButtonInjectClick();
    }
}
