package com.karolskora.msorgranizer.activities;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;


import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.fragments.DatePickerFragment;

public class FirstInjectionTimeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_first_injection_time);

    }

    public void onButtonNextClick(View view){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
}
