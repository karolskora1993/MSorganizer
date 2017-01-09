package com.karolskora.msorgranizer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.fragments.DatePickerFragment;
import com.karolskora.msorgranizer.java.AlertOrganizer;
import com.karolskora.msorgranizer.java.StringValidator;

public class DrugSupplyActivity extends AppCompatActivity {
    public static final String DOSES = "doses";
    public static final String NOTIFICATION_DOSES = "notification_doses";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style=getIntent().getIntExtra(AppStyleActivity.USER_STYLE, 0);
        if(style == 2) {
            setTheme(R.style.darkAppTheme);
        }
        setContentView(R.layout.layout_drug_supply);
    }

    public void onButtonNextClick(View view) {
        EditText dosesEditText = (EditText) findViewById(R.id.dosesEditTextFragment);
        EditText notificationDosesEditText = (EditText) findViewById(R.id.notificationDosesEditTextFragment);

        if (dosesEditText.getText().toString().equals("") || notificationDosesEditText.getText().toString().equals("")) {
            showNotCompletedFieldsToast();
        }else if (!StringValidator.isNumber(new String[] {dosesEditText.getText().toString(), notificationDosesEditText.getText().toString()})) {
            AlertOrganizer.showAlert(this, getResources().getString(R.string.characters_not_allowed_title), getResources().getString(R.string.characters_not_allowed_message));
        }
        else {
            Intent intent = getIntent();
            int doses = Integer.parseInt(dosesEditText.getText().toString());
            int notificationDoses = Integer.parseInt(notificationDosesEditText.getText().toString());
            String name = intent.getStringExtra(UserInformationsActivity.USER_NAME);
            String doctorName = intent.getStringExtra(UserInformationsActivity.DOCTOR_NAME);
            String nurseName = intent.getStringExtra(UserInformationsActivity.NURSE_NAME);
            String email = intent.getStringExtra(UserInformationsActivity.EMAIL);
            int style=getIntent().getIntExtra(AppStyleActivity.USER_STYLE, 0);
            long timeInMilis = intent.getLongExtra(DatePickerFragment.TIME_IN_MILIS, 0);

            intent = new Intent(this, AboutAppActivity.class);
            intent.putExtra(DOSES, doses);
            intent.putExtra(NOTIFICATION_DOSES, notificationDoses);
            intent.putExtra(DatePickerFragment.TIME_IN_MILIS, timeInMilis);
            intent.putExtra(AppStyleActivity.USER_STYLE, style);
            intent.putExtra(UserInformationsActivity.USER_NAME, name);
            intent.putExtra(UserInformationsActivity.DOCTOR_NAME, doctorName);
            intent.putExtra(UserInformationsActivity.NURSE_NAME, nurseName);
            intent.putExtra(UserInformationsActivity.EMAIL, email);

            startActivity(intent);
        }
    }

    private void showNotCompletedFieldsToast() {
        Toast toast = Toast.makeText(this, "Wypełnij wszystkie dane", Toast.LENGTH_LONG);
        toast.show();
    }
}
