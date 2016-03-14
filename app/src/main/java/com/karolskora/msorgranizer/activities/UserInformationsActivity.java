package com.karolskora.msorgranizer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.helpers.DatabaseHelper;
import com.karolskora.msorgranizer.models.User;

public class UserInformationsActivity extends FragmentActivity {

    public static final String USER_NAME="user_name";
    public static final String DOCTOR_NAME="doctor_name";
    public static final String NURSE_NAME="nurse_name";



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

        if(name.equals("") || doctorName.equals("") || nurseName.equals("")) {
            Log.i(this.getClass().toString(), "Wyswietlenie wiadomosci o niepełności danych");
            Toast toast = Toast.makeText(this, "Wypełnij wszystkie dane", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            Intent intent = new Intent(this, FirstInjectionTimeActivity.class);
            intent.putExtra(USER_NAME, name);
            intent.putExtra(DOCTOR_NAME, doctorName);
            intent.putExtra(NURSE_NAME, nurseName);
            startActivity(intent);
        }
    }
}
