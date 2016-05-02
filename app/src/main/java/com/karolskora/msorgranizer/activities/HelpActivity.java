package com.karolskora.msorgranizer.activities;


import android.Manifest;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.app.NavigationPolicy;
import com.heinrichreimersoftware.materialintro.app.OnNavigationBlockedListener;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;
import com.karolskora.msorgranizer.R;

public class HelpActivity extends IntroActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){

        setFullscreen(true);

        super.onCreate(savedInstanceState);

        setSkipEnabled(true);

        setFinishEnabled(true);

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_1)
                .description(R.string.description_1)
                .image(R.drawable.bg)
                .background(R.color.color_100)
                .backgroundDark(R.color.color_100)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_2)
                .description(R.string.description_2)
                .image(R.drawable.bg)
                .background(R.color.color_300)
                .backgroundDark(R.color.color_300)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_3)
                .description(R.string.description_3)
                .image(R.drawable.bg)
                .background(R.color.color_200)
                .backgroundDark(R.color.color_200)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_4)
                .description(R.string.description_4)
                .image(R.drawable.bg)
                .background(R.color.color_400)
                .backgroundDark(R.color.color_400)
                .build());
    }

}