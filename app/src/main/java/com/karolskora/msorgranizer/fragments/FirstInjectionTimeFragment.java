package com.karolskora.msorgranizer.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.karolskora.msorgranizer.R;


public class FirstInjectionTimeFragment extends Fragment {

    public FirstInjectionTimeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first_injection_time, container, false);
    }

}
