package com.karolskora.msorgranizer.java;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.CameraAccessException;
import android.opengl.GLSurfaceView;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.models.Injection;
import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.Loader;
import com.threed.jpct.Logger;
import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import com.threed.jpct.util.LensFlare;
import com.threed.jpct.util.MemoryHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ModelRenderer implements GLSurfaceView.Renderer {
    private FrameBuffer fb = null;
    private RGBColor back = new RGBColor(255, 255, 255, 0);

    private float touchTurn = 0;
    private float touchTurnUp = 0;
    private World world;

    private float xpos = -1;
    private float ypos = -1;

    private Object3D cube = null;
    private int fps = 0;

    private Light sun = null;


    private String thingName;
    private int thingScale;
    private Context context;
    private Context master;

    private int area;
    private int point;
    int scaleFactor=40;


    private long time = System.currentTimeMillis();

    public ModelRenderer(Context activity, final View view, int scale, String name, int area, int point) {
        super();
        context=activity;
        this.thingScale=scale;
        this.thingName=name;
        this.area=area;
        this.point=point;


        try {
            cube = loadModel(thingName, thingScale);
        } catch (IOException e) {
            Log.e(this.getClass().toString(), e.getMessage());
        }


        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent me) {
                if (me.getAction() == MotionEvent.ACTION_DOWN) {
                    xpos = me.getX();
                    ypos = me.getY();
                    return true;
                }

                if (me.getAction() == MotionEvent.ACTION_UP) {
                    xpos = -1;
                    ypos = -1;
                    touchTurn = 0;
                    touchTurnUp = 0;
                    return true;
                }

                if (me.getAction() == MotionEvent.ACTION_MOVE) {
                    float xd = me.getX() - xpos;

                    xpos = me.getX();

                    touchTurn = xd / -100f;
                    return true;
                }


                try {
                    Thread.sleep(15);
                } catch (Exception e) {
                    Log.e(this.getClass().toString(),e.getMessage());
                }

                return view.onTouchEvent(me);
            }
        });

    }

    public void onSurfaceChanged(GL10 gl, int w, int h) {
        if (fb != null) {
            fb.dispose();
        }
        fb = new FrameBuffer(gl, w, h);

        if (master == null) {

            world = new World();
            world.setAmbientLight(20, 20, 20);

            sun = new Light(world);
            sun.setIntensity(250, 250, 250);


            cube.build();

            world.addObject(cube);


            SimpleVector sv = new SimpleVector();
            sv.set(cube.getTransformedCenter());
            sv.y -= 100;
            sv.z -= 100;


            sun.setPosition(sv);
            MemoryHelper.compact();


            try {
                TextureManager.getInstance().replaceTexture("HAND.PNG", new Texture(context.getAssets().open("skin.png")));
                TextureManager.getInstance().replaceTexture("BODY.PNG", new Texture(context.getAssets().open("skin.png")));
                TextureManager.getInstance().replaceTexture("LOWR.PNG", new Texture(context.getAssets().open("skin.png")));
                TextureManager.getInstance().replaceTexture("HEAD.PNG", new Texture(context.getAssets().open("skin.png")));

            } catch (IOException e) {
                Log.e(ModelRenderer.class.toString(), e.getMessage());
            }

            rotateModel();

            master=context;
        }
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    }

    public void onDrawFrame(GL10 gl) {
        if (touchTurn != 0) {
            cube.rotateY(touchTurn);
            touchTurn = 0;
        }

        if (touchTurnUp != 0) {
            cube.rotateX(touchTurnUp);
            touchTurnUp = 0;
        }

        fb.clear(back);
        world.renderScene(fb);
        world.draw(fb);
        fb.display();

        if (System.currentTimeMillis() - time >= 1000) {
            Logger.log(fps + "fps");
            fps = 0;
            time = System.currentTimeMillis();
        }
        fps++;
    }

    private Object3D loadModel(String filename, float scale) throws IOException {

        InputStream stream = context.getResources().getAssets().open(filename);

        Object3D[] model = Loader.load3DS(stream, scale);
        Object3D o3d = new Object3D(0);
        Object3D temp = null;
        for (int i = 0; i < model.length; i++) {
            temp = model[i];
            temp.setCenter(SimpleVector.ORIGIN);
            temp.rotateZ((float) (0.5 * Math.PI));
            temp.rotateY((float) (0.5 * Math.PI));
            temp.rotateMesh();
            temp.setRotationMatrix(new Matrix());
            o3d = Object3D.mergeObjects(o3d, temp);
            o3d.build();
        }
        return o3d;
    }

    private void rotateModel(){

        Camera cam = world.getCamera();
        SimpleVector mv=cube.getCenter();
        if(area==1){
            cam.moveCamera(Camera.CAMERA_MOVEOUT, 20);

            mv.y+=20;
            cam.lookAt(mv);

            SimpleVector cv=cam.getPosition();
            cv.y-=20;

            cam.setPosition(cv);

            cube.rotateY((float)(-0.5 * Math.PI));
        }
        else if(area==2){
            cam.moveCamera(Camera.CAMERA_MOVEOUT, 20);

            mv.y+=20;
            cam.lookAt(mv);

            SimpleVector cv=cam.getPosition();
            cv.y-=20;

            cam.setPosition(cv);

            cube.rotateY((float)(0.5 * Math.PI));
        }
        else if(area==3){
            cam.moveCamera(Camera.CAMERA_MOVEOUT, 15);

            mv.y+=4;
            cam.lookAt(mv);

            SimpleVector cv=cam.getPosition();
            cv.y-=6;

            cam.setPosition(cv);

            cube.rotateY((float)(-0.2 * Math.PI));
        }
        else if(area==4){
            cam.moveCamera(Camera.CAMERA_MOVEOUT, 15);

            mv.y+=4;
            cam.lookAt(mv);

            SimpleVector cv=cam.getPosition();
            cv.y-=6;

            cam.setPosition(cv);

            cube.rotateY((float)(0.2 * Math.PI));
        }
        else if(area==5){
            cam.moveCamera(Camera.CAMERA_MOVEOUT, 15);

            mv.y+=5;
            cam.lookAt(mv);

            SimpleVector cv=cam.getPosition();
            cv.y+=10;
            cv.x-=2;

            cam.setPosition(cv);

            cube.rotateY((float)(-0.2 * Math.PI));
        }
        else if(area==6){
            cam.moveCamera(Camera.CAMERA_MOVEOUT, scaleFactor);

            mv.y+=5;
            cam.lookAt(mv);

            SimpleVector cv=cam.getPosition();
            cv.y+=10;
            cv.x+=2;

            cam.setPosition(cv);

            cube.rotateY((float)(0.2 * Math.PI));



        }
        else if(area==7){
            cam.moveCamera(Camera.CAMERA_MOVEOUT, 15);

            mv.y+=3;
            cam.lookAt(mv);

            SimpleVector cv=cam.getPosition();
            cv.x-=2;

            cam.setPosition(cv);

            cube.rotateY((float)(0.9 * Math.PI));
        }
        else if(area==8){
            cam.moveCamera(Camera.CAMERA_MOVEOUT, 15);

            mv.y+=3;
            cam.lookAt(mv);

            SimpleVector cv=cam.getPosition();
            cv.x+=2;

            cam.setPosition(cv);

            cube.rotateY((float)(-0.9 * Math.PI));
        }

    }


}