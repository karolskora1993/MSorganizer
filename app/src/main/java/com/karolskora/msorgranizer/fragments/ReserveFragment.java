package com.karolskora.msorgranizer.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.java.DatabaseQueries;


public class ReserveFragment extends Fragment implements View.OnClickListener{



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
    public void onButtonSaveQuantityClick() {
        EditText dosesEditText=(EditText)getActivity().findViewById(R.id.dosesFragmentEditText);
        EditText notificationDosesEditText=(EditText)getActivity().findViewById(R.id.notificationDosesFragmentEditText);

        int doses=Integer.parseInt(dosesEditText.getText().toString());
        int notificationDoses=Integer.parseInt(notificationDosesEditText.getText().toString());

        DatabaseQueries.updateDoses(getActivity(), doses, notificationDoses);
        Toast toast = Toast.makeText(getActivity(), "Zapisano ustawienia leku", Toast.LENGTH_LONG);
        toast.show();
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        try {
            getView().findViewById(R.id.fragmentReserveButton).setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        onButtonSaveQuantityClick();
    }

}
