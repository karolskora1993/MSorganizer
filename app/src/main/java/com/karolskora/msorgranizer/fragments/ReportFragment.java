package com.karolskora.msorgranizer.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.java.DatabaseQueries;
import com.karolskora.msorgranizer.models.Injection;

import java.util.Calendar;
import java.util.List;


public class ReportFragment extends Fragment {

    public ReportFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Injection> injections = DatabaseQueries.getInjections(getActivity());

        for(Injection in:injections){
            CheckBox checkBox=new CheckBox(getActivity());

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(in.getTimeInMilis());

            String injectionDate = calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR);
            String injectionTime=calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
            String description="data: "+ injectionDate+"    godzina: "+injectionTime;
            checkBox.setText(description);

            LinearLayout layout=(LinearLayout)getActivity().findViewById(R.id.layout_report);

            layout.addView(checkBox);
        }
    }
}
