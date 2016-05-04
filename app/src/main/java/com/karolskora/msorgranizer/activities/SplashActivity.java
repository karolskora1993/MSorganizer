package com.karolskora.msorgranizer.activities;


import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.karolskora.msorgranizer.R;
import com.karolskora.msorgranizer.java.Constants;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashActivity extends AwesomeSplash {

    @Override
    public void initSplash(ConfigSplash configSplash) {


        configSplash.setBackgroundColor(R.color.primary);
        configSplash.setAnimCircularRevealDuration(2000);
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM);


        configSplash.setPathSplash(Constants.DROID_LOGO);
        configSplash.setOriginalHeight(400);
        configSplash.setOriginalWidth(400);
        configSplash.setAnimPathStrokeDrawingDuration(800);
        configSplash.setPathSplashStrokeSize(3);
        configSplash.setPathSplashStrokeColor(R.color.accent);
        configSplash.setAnimPathFillingDuration(1000);
        configSplash.setPathSplashFillColor(R.color.color_bg);

        configSplash.setTitleSplash("Asystent zastrzyków");
        configSplash.setTitleTextColor(R.color.md_black_1000);
        configSplash.setTitleTextSize(30f);
        configSplash.setAnimTitleDuration(1000);
        configSplash.setAnimTitleTechnique(Techniques.FadeIn);
        configSplash.setTitleFont(Constants.DITI_SWEET_FONT);

    }

    @Override
    public void animationsFinished() {

        Intent intent=new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}