package com.karolskora.msorgranizer.fragments;


import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.activities.InjectionActivity;
import com.karolskora.msorgranizer.activities.MainActivity;
import com.karolskora.msorgranizer.java.AlertOrganizer;
import com.karolskora.msorgranizer.java.DatabaseQueries;
import com.karolskora.msorgranizer.models.Injection;
import com.karolskora.msorgranizer.models.Notification;

import java.util.Calendar;

public class ToInjectionFragment extends Fragment implements View.OnClickListener {
    private Injection lastInjection;
    private Notification notification;
    private ObjectAnimator animation;
    private long timeToInjection;
    private int seconds = 0;
    private boolean firstRun = true;
    public static String MANUAL_INJECTION_EXTRA_MESSAGE = "manualInjectionExtraMessage";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        lastInjection = DatabaseQueries.getLatestInjection(getActivity());
        notification = DatabaseQueries.getInjectionsSchedule(getActivity());
        return inflater.inflate(R.layout.fragment_to_injection, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity owner = (MainActivity) getActivity();

        TextView textView = (TextView) owner.findViewById(R.id.fragmentMainTextView);

        if (textView != null) {
            String name = "Witaj " + DatabaseQueries.getUser(getActivity()).getName() + ", do następnego zastrzyku pozostało:";
            textView.setText(name);
            setTimeToInjection();
            runTimer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setTimeToInjection() {
        Calendar calendar = Calendar.getInstance();
        if (lastInjection == null) {
            calendar.setTimeInMillis(notification.getInjectionTime());
        } else {
            calendar.setTimeInMillis(notification.getInjectionTime());
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int AM_PM = calendar.get(Calendar.AM_PM);
            calendar.setTimeInMillis(lastInjection.getTimeInMilis() + 48 * 60 * 60 * 1000);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.AM_PM, AM_PM);
        }

        long postponedInectionTime = notification.getPostoponedNotificationTime();

        if (postponedInectionTime > 0) {
            timeToInjection = postponedInectionTime - Calendar.getInstance().getTimeInMillis();
        } else {
            timeToInjection = calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        }
        if (firstRun) {
            calendar.setTimeInMillis(timeToInjection);
            seconds = calendar.get(Calendar.SECOND);
        }
    }

    private String getTimeToInjectionString() {
        int hours = (int) (timeToInjection / (1000 * 60 * 60));
        int minutes = (int) ((timeToInjection - (hours * 60 * 60 * 1000)) / (60 * 1000));
        if (seconds > 0) {
            minutes++;
        }
        String time;
        if (timeToInjection <= 60000) {
            return "Wykonaj\nzastrzyk";
        }
        if (timeToInjection > 48 * 60 * 60 * 1000) {
            int days = 2;
            hours = hours - 48;
            time = days + "dni \n" + hours + "godzin\n" + minutes + "minut";

        } else if (timeToInjection > 24 * 60 * 60 * 1000) {
            int days = 1;
            hours = hours - 24;
            time = days + "dzień \n" + hours + "godzin\n" + minutes + "minut";

        } else {
            time = hours + "godzin\n" + minutes + "minut";
        }
        return time;
    }

    public void onButtonInjectClick() {
        if (timeToInjection < 6 * 60 * 60 * 1000) {
            startInjectionActivity();
        } else {
            AlertOrganizer.showCancelableAlert(getActivity(), null, getResources().getString(R.string.manual_injection_time_alert_message), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startInjectionActivity();
                }
            });
        }
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        try {
            getView().findViewById(R.id.fragmentToInjectionButton).setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        onButtonInjectClick();
    }

    private void runTimer() {
        final TextView timeTextView = (TextView) getActivity().findViewById(R.id.timeTextView);
        final ProgressBar progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                timeTextView.setText(getTimeToInjectionString());
                if (timeToInjection <= 60000) {
                    int color = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.red_color);
                    ProgressBar progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
                    progressBar.setProgress(60);
                    timeTextView.setTextColor(color);
                    progressBar.getProgressDrawable().setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN);
                } else {
                    if (firstRun) {
                        handler.postDelayed(this, 60000 - seconds * 1000);
                        animate(progressBar);
                        Log.d(this.getClass().toString(), "firstRun, seconds: " + (60000 - seconds * 1000));
                        firstRun = false;
                        timeToInjection -= (60000 - seconds * 1000);
                        seconds = 0;
                    } else {
                        handler.postDelayed(this, 60000);
                        animate(progressBar);
                        Log.d(this.getClass().toString(), "next run, seconds: " + (60000 - seconds * 1000));
                        timeToInjection -= 60000;
                    }
                }
            }
        });
    }

    private void animate(ProgressBar progressBar) {
        animation = ObjectAnimator.ofInt(progressBar, "progress", seconds, 60);
        animation.setDuration(60000 - seconds * 1000);
        animation.setInterpolator(new LinearInterpolator());
        Log.d(this.getClass().toString(), "animate, duration: " + (60000 - seconds * 1000));
        animation.start();
    }

    private void startInjectionActivity() {
        Intent intent = new Intent(getActivity(), InjectionActivity.class);
        intent.putExtra(MANUAL_INJECTION_EXTRA_MESSAGE, true);
        startActivity(intent);
    }
}

