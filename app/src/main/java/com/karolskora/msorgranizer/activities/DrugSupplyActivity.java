package com.karolskora.msorgranizer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.fragments.DatePickerFragment;

public class DrugSupplyActivity extends AppCompatActivity {
    public static final String DOSES = "doses";
    public static final String NOTIFICATION_DOSES = "notification_doses";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drug_supply);
    }

    public void onButtonNextClick(View view) {
        EditText dosesEditText = (EditText) findViewById(R.id.dosesEditText);

        int doses = Integer.parseInt(dosesEditText.getText().toString());

        EditText notificationDosesEditText = (EditText) findViewById(R.id.notificationDosesEditText);
        int notificationDoses = Integer.parseInt(notificationDosesEditText.getText().toString());

        if (dosesEditText.getText().toString().equals("") || notificationDosesEditText.getText().toString().equals("")) {
            Toast toast = Toast.makeText(this, "Wypełnij wszystkie dane", Toast.LENGTH_LONG);
            toast.show();
        } else {

            Intent intent = getIntent();

            String name = intent.getStringExtra(UserInformationsActivity.USER_NAME);
            String doctorName = intent.getStringExtra(UserInformationsActivity.DOCTOR_NAME);
            String nurseName = intent.getStringExtra(UserInformationsActivity.NURSE_NAME);
            String email = intent.getStringExtra(UserInformationsActivity.EMAIL);


            long timeInMilis = intent.getLongExtra(DatePickerFragment.TIME_IN_MILIS, 0);
            intent = new Intent(this, AboutAppActivity.class);
            intent.putExtra(DOSES, doses);
            intent.putExtra(NOTIFICATION_DOSES, notificationDoses);

            intent.putExtra(DatePickerFragment.TIME_IN_MILIS, timeInMilis);
            intent.putExtra(UserInformationsActivity.USER_NAME, name);
            intent.putExtra(UserInformationsActivity.DOCTOR_NAME, doctorName);
            intent.putExtra(UserInformationsActivity.NURSE_NAME, nurseName);
            intent.putExtra(UserInformationsActivity.EMAIL, email);

            startActivity(intent);
        }
    }
}
