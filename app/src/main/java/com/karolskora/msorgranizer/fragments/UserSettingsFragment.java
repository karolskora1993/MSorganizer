package com.karolskora.msorgranizer.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.activities.MainActivity;
import com.karolskora.msorgranizer.models.User;


public class UserSettingsFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_settings, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EditText nameTextEdit = (EditText)getActivity().findViewById(R.id.nameTextEdit);

        EditText doctorNameTextEdit = (EditText)getActivity().findViewById(R.id.doctorNameTextEdit);

        EditText nurseNameTextEdit = (EditText)getActivity().findViewById(R.id.nurseNameTextEdit);

        EditText emailTextEdit = (EditText)getActivity().findViewById(R.id.nurseNameTextEdit);

        MainActivity activity=(MainActivity)getActivity();
        User user =activity.getUser();
        if(nameTextEdit!=null) {
            nameTextEdit.setText(user.getName());
            doctorNameTextEdit.setText(user.getDoctorName());
            nurseNameTextEdit.setText(user.getNurseName());
            emailTextEdit.setText(user.getEmail());
        }
    }

}
