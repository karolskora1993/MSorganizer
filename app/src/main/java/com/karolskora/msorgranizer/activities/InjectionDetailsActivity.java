package com.karolskora.msorgranizer.activities;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.java.ModelRenderer;
import com.karolskora.msorgranizer.models.Injection;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

public class InjectionDetailsActivity extends AppCompatActivity {

    private int position;

    private GLSurfaceView mGLView;
    private ModelRenderer renderer = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_injection_details);
        setRenderer();

    }

    private void setSymptoms(Injection injection) {
        boolean temperature = injection.isTemperature();
        boolean trembles = injection.isTrembles();
        boolean ache = injection.isAche();

        CheckBox temperatureCheckBox = (CheckBox)findViewById(R.id.temperatureCheckBox);
        CheckBox tremblesCheckBox = (CheckBox)findViewById(R.id.tremblesCheckBox);
        CheckBox acheCheckBox = (CheckBox)findViewById(R.id.acheCheckBox);

        temperatureCheckBox.setChecked(temperature);
        tremblesCheckBox.setChecked(trembles);
        acheCheckBox.setChecked(ache);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void onButtonSendReportClick(View view) {

        //TO DO
    }

    private void setRenderer() {
        mGLView = (GLSurfaceView)findViewById(R.id.glSurface);
        mGLView.setEGLConfigChooser(new GLSurfaceView.EGLConfigChooser() {
            public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {

                int[] attributes = new int[]{EGL10.EGL_DEPTH_SIZE, 16, EGL10.EGL_NONE};
                EGLConfig[] configs = new EGLConfig[1];
                int[] result = new int[1];
                egl.eglChooseConfig(display, attributes, configs, 1, result);
                return configs[0];
            }
        });

        View view=findViewById(R.id.layout_injection_details);
        GLSurfaceView.Renderer renderer = new ModelRenderer(this, view, 25, "model.3DS");
        mGLView.setRenderer(renderer);
    }



}
