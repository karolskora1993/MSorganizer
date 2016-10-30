package com.karolskora.msorgranizer.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.activities.AppStyleActivity;


public class AboutUserFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_user, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int style=getActivity().getIntent().getIntExtra(AppStyleActivity.USER_STYLE, 0);
        if(style == 2) {
            LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
            RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.fragment_about_user, null);
            layout.setBackgroundResource(android.R.drawable.dialog_holo_dark_frame);
        }
    }
}
