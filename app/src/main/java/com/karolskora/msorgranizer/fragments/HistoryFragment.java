package com.karolskora.msorgranizer.fragments;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.activities.InjectionDetailsActivity;
import com.karolskora.msorgranizer.activities.MainActivity;
import com.karolskora.msorgranizer.java.DatabaseQueries;
import com.karolskora.msorgranizer.models.Injection;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;


public class HistoryFragment extends Fragment {
    public static String INJECTION = "injection";
    public static String POSITION="position";
    public static String ID = "id";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Injection> injections = DatabaseQueries.getInjections(getActivity());
        ListView injectionsListView=(ListView)getActivity().findViewById(R.id.injectionsListView);


        List listItems=new ArrayList();

        AdapterView.OnItemClickListener listener =new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), InjectionDetailsActivity.class);

                intent.putExtra(POSITION, position);

                TaskStackBuilder builder=TaskStackBuilder.create(getActivity())
                        .addParentStack(MainActivity.class)
                        .addNextIntent(intent);

                startActivity(intent);

            }
        };

        for(Injection in : injections){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(in.getTimeInMilis());

            String injectionDate = calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR);
            String injectionTime=calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
            String description="data: "+ injectionDate+"    godzina: "+injectionTime;

            listItems.add(description);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_list_layout, listItems);
        injectionsListView.setAdapter(adapter);
        injectionsListView.setOnItemClickListener(listener);

    }
}
