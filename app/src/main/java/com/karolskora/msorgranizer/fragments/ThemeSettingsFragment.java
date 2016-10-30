package com.karolskora.msorgranizer.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.activities.MainActivity;
import com.karolskora.msorgranizer.java.DatabaseQueries;

public class ThemeSettingsFragment extends Fragment implements View.OnClickListener {

    private int style = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_theme_settings, container, false);
    }


    public void onButtonSaveClick() {

        if(style == 0) {
            Toast toast = Toast.makeText(getActivity(), "Podaj preferowany styl aby przejść do kolejnego ekranu", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            DatabaseQueries.updateApplicationSettings(getActivity(), style);
            if(style ==1) {
                getActivity().setTheme(R.style.lightAppTheme);
                MainActivity ma= (MainActivity)getActivity();
                ma.updateTheme();
            } else {
                getActivity().setTheme(R.style.darkAppTheme);
                MainActivity ma= (MainActivity)getActivity();
                ma.updateTheme();
            }
            Toast toast = Toast.makeText(getActivity(), "zmieniono ustawienia motywu", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        try {
            getView().findViewById(R.id.fragmentUThemeSettingsButtonSave).setOnClickListener(this);
            getView().findViewById(R.id.image_holo_dark_settings).setOnClickListener(this);
            getView().findViewById(R.id.image_holo_light_settings).setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        ImageView holoDark = (ImageView)getActivity().findViewById(R.id.image_holo_dark_settings);
        ImageView holoLight = (ImageView)getActivity().findViewById(R.id.image_holo_light_settings);

        switch(v.getId()) {
            case R.id.image_holo_light_settings: {
                style=1;
                holoLight.setImageDrawable(getResources().getDrawable(R.drawable.holo_light_checked));
                holoDark.setImageDrawable(getResources().getDrawable(R.drawable.holo_dark));

                break;
            }
            case R.id.image_holo_dark_settings: {
                style=2;
                holoLight.setImageDrawable(getResources().getDrawable(R.drawable.holo_light));
                holoDark.setImageDrawable(getResources().getDrawable(R.drawable.holo_dark_checked));
                break;
            }
            default:
                onButtonSaveClick();
                break;
        }
    }

}
