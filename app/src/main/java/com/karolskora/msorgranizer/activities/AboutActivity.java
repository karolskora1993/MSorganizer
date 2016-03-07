package com.karolskora.msorgranizer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.karolskora.msorgranizer.R;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);
    }

    public void onButtonNextClick(View view) {

        Intent intent = new Intent(this, FirstInjectionTimeActivity.class);
        startActivity(intent);
    }
}
