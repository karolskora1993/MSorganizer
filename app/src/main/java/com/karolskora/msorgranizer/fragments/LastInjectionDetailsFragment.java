package com.karolskora.msorgranizer.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.java.ModelRenderer;
import com.karolskora.msorgranizer.models.Injection;

import java.util.Calendar;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;


public class LastInjectionDetailsFragment extends Fragment {

    private Injection injection;
    private GLSurfaceView mGLView;
    private View view;


    public LastInjectionDetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        injection = (Injection) bundle.get(HistoryFragment.INJECTION);

        view = inflater.inflate(R.layout.fragment_injection_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView date = (TextView) getActivity().findViewById(R.id.injectionDateTextView);
        if (date != null) {
            setDate();
            setSymptoms();
            setField();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    private void setDate() {
        Activity owner = getActivity();
        TextView date = (TextView) owner.findViewById(R.id.injectionDateTextView);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(injection.getTimeInMilis());
        String injectionDate = calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR) +
                " " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
        date.setText(injectionDate);
    }

    private void setSymptoms() {
        boolean temperature = injection.isTemperature();
        boolean trembles = injection.isTrembles();
        boolean ache = injection.isAche();

        Activity owner = getActivity();
        CheckBox temperatureCheckBox = (CheckBox) owner.findViewById(R.id.temperatureCheckBox);
        CheckBox tremblesCheckBox = (CheckBox) owner.findViewById(R.id.tremblesCheckBox);
        CheckBox acheCheckBox = (CheckBox) owner.findViewById(R.id.acheCheckBox);

        temperatureCheckBox.setChecked(temperature);
        tremblesCheckBox.setChecked(trembles);
        acheCheckBox.setChecked(ache);

    }

    private void setField(){

        Activity context=getActivity();

        ImageView imageView=(ImageView)context.findViewById(R.id.glSurfaceView);

        String field="f"+ String.valueOf(injection.getArea()) + String.valueOf(injection.getPoint());

        Log.d(this.getClass().toString(), "field: "+field);
        Drawable d = ContextCompat.getDrawable(context, context.getResources().getIdentifier(field, "drawable", context.getPackageName()));

        imageView.setImageDrawable(d);
    }

    public Injection getInjection() {
        return injection;
    }
}
