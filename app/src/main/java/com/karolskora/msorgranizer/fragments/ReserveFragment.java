package com.karolskora.msorgranizer.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.java.DatabaseQueries;


public class ReserveFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reserve, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity owner=getActivity();
        int doses= DatabaseQueries.getDoses(getActivity());
        int notificationDoses=DatabaseQueries.getNotificationDoses(getActivity());

        EditText dosesEditText=(EditText)owner.findViewById(R.id.dosesFragmentEditText);

        dosesEditText.setText(String.valueOf(doses));

        EditText notificationDosesEditText=(EditText)owner.findViewById(R.id.notificationDosesFragmentEditText);

        notificationDosesEditText.setText(String.valueOf(notificationDoses));
    }
}
