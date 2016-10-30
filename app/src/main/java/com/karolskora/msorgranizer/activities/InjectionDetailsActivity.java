package com.karolskora.msorgranizer.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.fragments.HistoryFragment;
import com.karolskora.msorgranizer.java.DatabaseQueries;
import com.karolskora.msorgranizer.java.ModelRenderer;
import com.karolskora.msorgranizer.java.PdfGenerator;
import com.karolskora.msorgranizer.models.Injection;
import com.karolskora.msorgranizer.models.User;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

public class InjectionDetailsActivity extends AppCompatActivity {

    private int position;
    private Injection injection;
    private GLSurfaceView mGLView;
    private ModelRenderer renderer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int appStyle = DatabaseQueries.getApplicationStyle(this);

        if(appStyle == 2) {
            setTheme(R.style.darkAppTheme);
        }

        setContentView(R.layout.layout_injection_details);
        Intent intent=getIntent();
        position=intent.getIntExtra(HistoryFragment.POSITION,-1);
        List<Injection> injections= DatabaseQueries.getInjections(this);
        injection= injections.get(position);
        setSymptoms(injection);
        setRenderer();
        setImage();
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
        List<Injection> injections =new ArrayList<>();
        injections.add(injection);

        User user=DatabaseQueries.getUser(this);
        Calendar calendar=Calendar.getInstance();

        String injectionDate = calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR);
        String injectionTime=calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
        String date="data: "+ injectionDate+"    godzina: "+injectionTime;
        String fileName="report_"+ calendar.getTimeInMillis()+".pdf";

        PdfGenerator.generate(this,injections, fileName);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"email@example.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, user.getName()+ "-raport");
        intent.putExtra(Intent.EXTRA_TEXT, "Raport wysłany w dniu "+date);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+fileName);
        if (!file.exists() || !file.canRead()) {
            Toast.makeText(this, "nie można dodać załącznika", Toast.LENGTH_LONG).show();
        }
        else {
            Uri uri = Uri.fromFile(file);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(intent, "Wyślij email..."));
        }
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
        GLSurfaceView.Renderer renderer = new ModelRenderer(this, view, 25, "model.3DS", injection.getArea(), injection.getPoint());
        mGLView.setRenderer(renderer);
    }

    private void setImage(){
        ImageView imageView=(ImageView)findViewById(R.id.injectionPointImageView);
        String field="f"+ String.valueOf(injection.getArea()) + String.valueOf(injection.getPoint());
        Drawable d = ContextCompat.getDrawable(this, this.getResources().getIdentifier(field, "drawable", this.getPackageName()));
        imageView.setImageDrawable(d);
    }



}
