package com.karolskora.msorgranizer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.fragments.HistoryFragment;

public class InjectionDetailsActivity extends AppCompatActivity {

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_injection_details);

        Intent intent= getIntent();
        position=intent.getIntExtra(HistoryFragment.POSITION,-1);

        if(position!=-1){
            //TO DO
        }

    }
}
