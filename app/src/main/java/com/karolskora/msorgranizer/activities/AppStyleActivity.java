package com.karolskora.msorgranizer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.fragments.ApplicationStyleFragment;

public class AppStyleActivity extends AppCompatActivity {

    public static String USER_STYLE = "user_style";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_app_style);
    }

    public void onButtonNextClick(View view) {

        int style = ApplicationStyleFragment.style;

        if(style == 0) {
            Toast toast = Toast.makeText(this, "Podaj preferowany styl aby przejść do kolejnego ekranu", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            if(style == 2) {
                setTheme(R.style.darkAppTheme);
            }
            startNextActivity(style);
        }
    }

    private void startNextActivity(int style) {
        Intent intent = new Intent(this, UserInformationsActivity.class);
        intent.putExtra(USER_STYLE, style);

        startActivity(intent);
    }
}
