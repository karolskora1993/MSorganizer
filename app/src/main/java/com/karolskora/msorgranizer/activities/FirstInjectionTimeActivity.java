package com.karolskora.msorgranizer.activities;

import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TimePicker;


import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.fragments.DatePickerFragment;

public class FirstInjectionTimeActivity extends FragmentActivity {

    public static final String HOUR="hour";
    public static final String MINUTE="minute";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style=getIntent().getIntExtra(AppStyleActivity.USER_STYLE, 0);
        if(style == 2) {
            setTheme(R.style.darkAppTheme);
        }
        setContentView(R.layout.layout_first_injection_time);
    }

    public void onButtonNextClick(View view){

        int[] time = getTime();
        showDatePickerFragment(time);
    }

    private int[] getTime() {
        TimePicker time=(TimePicker)findViewById(R.id.timePicker);
        int hour, minute;
        if(Build.VERSION.SDK_INT < 23) {
            hour = time.getCurrentHour();
            minute = time.getCurrentMinute();
        }
        else
        {
            hour = time.getHour();
            minute = time.getMinute();
        }

        return new int[] {hour, minute};
    }

    private void showDatePickerFragment(int[] time) {
        Bundle bundle=new Bundle();
        bundle.putInt(FirstInjectionTimeActivity.HOUR, time[0]);
        bundle.putInt(FirstInjectionTimeActivity.MINUTE, time[1]);

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(bundle);
        newFragment.show(getFragmentManager(), "datePicker");
    }
}
