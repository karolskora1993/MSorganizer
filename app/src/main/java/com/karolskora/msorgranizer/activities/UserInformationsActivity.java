package com.karolskora.msorgranizer.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.karolskora.msorgranizer.R;

public class UserInformationsActivity extends FragmentActivity {

    public static final String USER_NAME="user_name";
    public static final String DOCTOR_NAME="doctor_name";
    public static final String NURSE_NAME="nurse_name";
    public static final String EMAIL="email";

    private String name;
    private String doctorName;
    private String nurseName;
    private String doctorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style=getIntent().getIntExtra(AppStyleActivity.USER_STYLE, 0);
        if(style == 2) {
            setTheme(R.style.darkAppTheme);
        }
        setContentView(R.layout.layout_initial_settings);
    }

    public void onButtonNextClick(View view) {
        name= getStringForField(R.id.nameTextEdit);
        doctorName=getStringForField(R.id.doctorNameTextEdit);
        nurseName=getStringForField(R.id.nurseNameTextEdit);
        doctorEmail=getStringForField(R.id.emailTextEdit);
        doctorEmail=getStringForField(R.id.emailTextEdit);

        if(areFieldsFilled()) {
            Toast toast = Toast.makeText(this, "Wypełnij wszystkie dane", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            startNextActivity();
        }
    }

    private String getStringForField(int fieldId) {
        EditText editText = (EditText)findViewById(fieldId);

        return  editText.getText().toString();
    }

    private boolean areFieldsFilled(){
     return name.equals("") || doctorName.equals("") || nurseName.equals("") || doctorEmail.equals("");

    }

    private void startNextActivity() {
        int style=getIntent().getIntExtra(AppStyleActivity.USER_STYLE, 0);

        Intent intent = new Intent(this, FirstInjectionTimeActivity.class);
        intent.putExtra(AppStyleActivity.USER_STYLE, style);
        intent.putExtra(USER_NAME, name);
        intent.putExtra(DOCTOR_NAME, doctorName);
        intent.putExtra(NURSE_NAME, nurseName);
        intent.putExtra(EMAIL, doctorEmail);

        startActivity(intent);
    }


}
