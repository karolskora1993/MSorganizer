package com.karolskora.msorgranizer.fragments;


import android.app.FragmentTransaction;
import android.content.Intent;
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
import com.karolskora.msorgranizer.models.Notification;

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
            Injection injection = DatabaseQueries.getLatestInjection(getActivity());
            if(injection!=null) {
                Fragment fragment = new LastInjectionDetailsFragment();
                Fragment fragment2=new ToInjectionFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable(HistoryFragment.INJECTION, injection);
                fragment.setArguments(bundle);
                FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                ft.replace(R.id.lastInjectionDetails, fragment, "LAST_INJECTION_FRAGMENT");
                ft.replace(R.id.timeToInjection, fragment2);
                ft.commit();
            }
        }
    }
}
