package com.karolskora.msorgranizer.activities;

import java.util.Calendar;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.fragments.TimePickerFragment;
import com.karolskora.msorgranizer.java.DatabaseQueries;
import com.karolskora.msorgranizer.java.ModelRenderer;
import com.karolskora.msorgranizer.java.PointFinder;

public class InjectionActivity extends Activity {

    private GLSurfaceView mGLView;
    private ModelRenderer renderer;
    private ScaleGestureDetector scaleGestureDetector;
    private int scaleFactor=40;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int appStyle = DatabaseQueries.getApplicationStyle(this);

        if(appStyle == 2) {
            setTheme(R.style.darkAppTheme);
        }

        setContentView(R.layout.layout_injection);
        setRenderer();
        setImage();
        scaleGestureDetector = new ScaleGestureDetector(this,
                new ScaleListener());
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

    public void postpone(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");

    }

    public void inject(View view) {

        int doses = DatabaseQueries.getDoses(this);
        if (doses == 0) {
            AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setCancelable(false);
            ad.setMessage("Brak dostępnych dawek leku!");
            ad.setButton(AlertDialog.BUTTON_POSITIVE, "uzupełnij", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(InjectionActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            ad.show();
        } else {
            if (doses < DatabaseQueries.getNotificationDoses(this))
                drugSupplyNotification(doses);

            doses = doses - 1;
            DatabaseQueries.updateDoses(this, doses);
            Calendar calendar = Calendar.getInstance();
            int[] injectionPoint = PointFinder.findPoint(this);
            DatabaseQueries.addInjection(this, calendar.getTimeInMillis(), injectionPoint[0], injectionPoint[1]);
            DatabaseQueries.deletePostponedInjection(this);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void drugSupplyNotification(int doses) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = this.getNotificationBuilder(doses);

        builder.setContentIntent(pendingIntent);
        notificationManager.notify(1123, builder.build());
    }

    private NotificationCompat.Builder getNotificationBuilder(int doses) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Zapas leku na wyczerpaniu");
        builder.setContentText("Pozostało " + doses + "dawek leku. Uzupełnij zapas");
        builder.setSmallIcon(R.drawable.ic_launcher);
        return builder;
    }

    private void setRenderer() {
        mGLView = (GLSurfaceView)findViewById(R.id.glSurfaceView);
        mGLView.setEGLConfigChooser(new GLSurfaceView.EGLConfigChooser() {
            public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {

                int[] attributes = new int[]{EGL10.EGL_DEPTH_SIZE, 16, EGL10.EGL_NONE};
                EGLConfig[] configs = new EGLConfig[1];
                int[] result = new int[1];
                egl.eglChooseConfig(display, attributes, configs, 1, result);
                return configs[0];
            }
        });

        View view=findViewById(R.id.layout_injection);

        int[] injectionPoint=PointFinder.findPoint(this);

        renderer = new ModelRenderer(this, view, 35, "model.3DS", injectionPoint[0], injectionPoint[1]);
        mGLView.setRenderer(renderer);
    }

    private void setImage(){

        ImageView imageView=(ImageView)findViewById(R.id.injectionPointImageView);
        int[] injectionPoint=PointFinder.findPoint(this);
        String field="f"+ String.valueOf(String.valueOf(injectionPoint[0]) + String.valueOf(injectionPoint[1]));
        Drawable d = ContextCompat.getDrawable(this, this.getResources().getIdentifier(field, "drawable", this.getPackageName()));
        imageView.setImageDrawable(d);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);

        return true;
    }

    private class ScaleListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {

        private float change;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = (int)(Math.max(0.1f, Math.min(scaleFactor, 5.0f)));

            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            Log.d(this.getClass().toString(), "scaleEnd"+ detector.getFocusX() + detector.getFocusY());
        }
    }
}