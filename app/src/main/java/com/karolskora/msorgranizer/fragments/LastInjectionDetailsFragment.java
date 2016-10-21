package com.karolskora.msorgranizer.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.java.DatabaseQueries;
import com.karolskora.msorgranizer.models.Injection;
import java.util.Calendar;

public class LastInjectionDetailsFragment extends Fragment implements View.OnClickListener{

    private Injection injection;
    private View view;

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

        Log.d(this.getClass().toString(), "field: " + field);
        Drawable d = ContextCompat.getDrawable(context, context.getResources().getIdentifier(field, "drawable", context.getPackageName()));

        imageView.setImageDrawable(d);
    }

    public Injection getInjection() {
        return injection;
    }

    public void onButtonSaveSymptomsClick() {

        CheckBox temperatureCheckBox=(CheckBox)getActivity().findViewById(R.id.temperatureCheckBox);
        CheckBox tremblesCheckBox=(CheckBox)getActivity().findViewById(R.id.tremblesCheckBox);
        CheckBox acheCheckBox=(CheckBox)getActivity().findViewById(R.id.acheCheckBox);


        boolean temperature=temperatureCheckBox.isChecked();
        boolean trembles=tremblesCheckBox.isChecked();
        boolean ache=acheCheckBox.isChecked();

        LastInjectionDetailsFragment fragment = (LastInjectionDetailsFragment)getFragmentManager().findFragmentByTag("LAST_INJECTION_FRAGMENT");

        Injection injection=fragment.getInjection();
        DatabaseQueries.updateInjection(getActivity(), injection, temperature, trembles, ache);

        Toast toast = Toast.makeText(getActivity(), "Zaktualizowano objawy", Toast.LENGTH_LONG);
        toast.show();

    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        try {
            getView().findViewById(R.id.fragmentInjectionDetailsButton).setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        onButtonSaveSymptomsClick();
    }
}
