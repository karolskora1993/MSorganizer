package com.karolskora.msorgranizer.fragments;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.java.DatabaseQueries;
import com.karolskora.msorgranizer.models.Injection;

import java.util.Iterator;
import java.util.List;


public class HistoryFragment extends Fragment {
    public static String INJECTION="injection";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Injection> injections= DatabaseQueries.getInjections(getActivity());
        Iterator<Injection> injectionIterator=injections.iterator();

        while(injectionIterator.hasNext()){
            Injection injection=injectionIterator.next();
            Activity owner=getActivity();
            Fragment fragment=new InjectionDetailsFragment();
            Bundle bundle=new Bundle();
            bundle.putSerializable(HistoryFragment.INJECTION, injection);
            fragment.setArguments(bundle);
            FragmentTransaction ft=getActivity().getFragmentManager().beginTransaction();
            ft.add(R.id.historyLayout, fragment);
            ft.commit();
        }

    }
}
