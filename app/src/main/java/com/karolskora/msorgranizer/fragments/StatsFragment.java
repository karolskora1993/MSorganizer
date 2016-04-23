package com.karolskora.msorgranizer.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.java.DatabaseQueries;
import com.karolskora.msorgranizer.models.Injection;

import java.util.ArrayList;
import java.util.List;


public class StatsFragment extends Fragment {

    public StatsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Injection> injections= DatabaseQueries.getInjections(getActivity());

        int[] stats=getStats(injections);

        TextView totalInjectionstextView=(TextView)getActivity().findViewById(R.id.totalInjectionsTextVIew);
        totalInjectionstextView.setText("Wykonane zastrzyki:" + stats[0]);

        TextView totaltemperaturetextView=(TextView)getActivity().findViewById(R.id.totalTemperatureTextVIew);
        totaltemperaturetextView.setText("temperatura: " +stats[1]);

        TextView totalAchestextView=(TextView)getActivity().findViewById(R.id.totalAcheTextVIew);
        totalAchestextView.setText("Ból mięśni: " + stats[2]);

        TextView totalTremblestextView=(TextView)getActivity().findViewById(R.id.totalTremplesTextVIew);
        totalTremblestextView.setText("Dreszcze: " + stats[3]);


        PieChart chart=(PieChart)getActivity().findViewById(R.id.pieChart);

        chart.setDescription("Wykres");

        ArrayList<Entry> entries=new ArrayList<>();

        entries.add(new Entry(stats[1], 0));

        entries.add(new Entry(stats[2], 1));

        entries.add(new Entry(stats[3], 2));

        entries.add(new Entry(stats[4], 3));

        PieDataSet set=new PieDataSet(entries,"");
        set.setColors(ColorTemplate.COLORFUL_COLORS);

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("temperatura");
        xVals.add("ból mięśni");
        xVals.add("dreszcze");
        xVals.add("bez objawów");


        PieData data=new PieData(xVals,set);
        chart.setData(data);
        chart.animateY(1000);
        chart.invalidate();

    }

    public int[] getStats(List<Injection> injections){

        int totalInjections=0;
        int temperature=0;
        int ache=0;
        int trembles=0;
        int noSymptoms=0;

        for (Injection inj:injections){
            totalInjections++;
            if(inj.isTemperature())
                temperature++;
            if(inj.isAche())
                ache++;
            if(inj.isTrembles()){
                trembles++;
            }
            if(!inj.isTrembles() && !inj.isAche() && !inj.isTemperature()){
                noSymptoms++;
            }
        }
        return new int[]{totalInjections, temperature,ache,trembles, noSymptoms};
    }
}
