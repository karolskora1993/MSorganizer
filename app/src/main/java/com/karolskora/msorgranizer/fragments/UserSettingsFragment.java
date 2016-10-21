package com.karolskora.msorgranizer.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.activities.MainActivity;
import com.karolskora.msorgranizer.java.DatabaseQueries;
import com.karolskora.msorgranizer.models.User;


public class UserSettingsFragment extends Fragment implements View.OnClickListener {



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

        EditText emailTextEdit = (EditText)getActivity().findViewById(R.id.emailTextEdit);

        MainActivity activity=(MainActivity)getActivity();
        User user = DatabaseQueries.getUser(activity);
        if(nameTextEdit!=null) {
            nameTextEdit.setText(user.getName());
            doctorNameTextEdit.setText(user.getDoctorName());
            nurseNameTextEdit.setText(user.getNurseName());
            emailTextEdit.setText(user.getEmail());
        }
    }

    public void onButtonSaveUserInfoClick() {

        EditText nameTextEdit = (EditText)getActivity().findViewById(R.id.nameTextEdit);
        String name = nameTextEdit.getText().toString();

        EditText doctorNameTextEdit = (EditText)getActivity().findViewById(R.id.doctorNameTextEdit);
        String doctorName = doctorNameTextEdit.getText().toString();

        EditText nurseNameTextEdit = (EditText)getActivity().findViewById(R.id.nurseNameTextEdit);
        String nurseName = nurseNameTextEdit.getText().toString();

        EditText emailTextEdit = (EditText)getActivity().findViewById(R.id.emailTextEdit);
        String email = emailTextEdit.getText().toString();

        DatabaseQueries.updateUser(getActivity(), name, doctorName, nurseName, email);
        Toast toast = Toast.makeText(getActivity(), "zmieniono ustawienia użytkownika", Toast.LENGTH_LONG);
        toast.show();
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        try {
            getView().findViewById(R.id.fragmentUserSettingsButtonSave).setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        onButtonSaveUserInfoClick();
    }

}
