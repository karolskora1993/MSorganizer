package com.karolskora.msorgranizer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.karolskora.msorgranizer.R;

public class ApplicationStyleFragment extends Fragment implements View.OnClickListener {
    public static int style = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_application_style, container, false);
    }

    public void onImageClick(int id) {

        ImageView holoDark = (ImageView)getActivity().findViewById(R.id.image_holo_dark);
        ImageView holoLight = (ImageView)getActivity().findViewById(R.id.image_holo_light);

        switch(id) {
            case R.id.image_holo_light: {
                style=1;
                break;
            }
            case R.id.image_holo_dark: {
                style=2;
                break;
            }
            default:
                style=0;
                break;
        }

        Log.d(this.getClass().toString(), "styl ustawiony:" + style);
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        try {
            getView().findViewById(R.id.image_holo_dark).setOnClickListener(this);
            getView().findViewById(R.id.image_holo_light).setOnClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        onImageClick(v.getId());
    }
}
