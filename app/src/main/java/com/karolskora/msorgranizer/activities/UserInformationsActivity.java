package com.karolskora.msorgranizer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.karolskora.msorgranizer.R;

public class UserInformationsActivity extends FragmentActivity {

    public static final String USER_NAME="user_name";
    public static final String DOCTOR_NAME="doctor_name";
    public static final String NURSE_NAME="nurse_name";
    public static final String EMAIL="email";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_initial_settings);
    }

    public void onButtonNextClick(View view) {

        EditText nameTextEdit = (EditText)findViewById(R.id.nameTextEdit);
        String name=nameTextEdit.getText().toString();

        EditText doctorNameTextEdit = (EditText)findViewById(R.id.doctorNameTextEdit);
        String doctorName=doctorNameTextEdit.getText().toString();

        EditText nurseNameTextEdit = (EditText)findViewById(R.id.nurseNameTextEdit);
        String nurseName=nurseNameTextEdit.getText().toString();

        EditText emailTextEdit = (EditText)findViewById(R.id.emailTextEdit);
        String email=emailTextEdit.getText().toString();

        if(name.equals("") || doctorName.equals("") || nurseName.equals("") || email.equals("")) {
            Log.i(this.getClass().toString(), "Wyswietlenie wiadomosci o niepełności danych");
            Toast toast = Toast.makeText(this, "Wypełnij wszystkie dane", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            Intent intent = new Intent(this, FirstInjectionTimeActivity.class);
            intent.putExtra(USER_NAME, name);
            intent.putExtra(DOCTOR_NAME, doctorName);
            intent.putExtra(NURSE_NAME, nurseName);
            intent.putExtra(EMAIL, email);

            startActivity(intent);
        }
    }
}
