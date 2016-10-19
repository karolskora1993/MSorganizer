package com.karolskora.msorgranizer.activities;


import android.os.Bundle;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
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
                .title(R.string.title_0)
                .description(R.string.about_app)
                .background(R.color.color_100)
                .backgroundDark(R.color.color_100)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_1)
                .description(R.string.description_1)
                .image(R.drawable.help0)
                .background(R.color.color_100)
                .backgroundDark(R.color.color_100)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_2)
                .description(R.string.description_2)
                .image(R.drawable.help1)
                .background(R.color.color_300)
                .backgroundDark(R.color.color_300)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_3)
                .description(R.string.description_3)
                .image(R.drawable.help2)
                .background(R.color.color_200)
                .backgroundDark(R.color.color_200)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_4)
                .description(R.string.description_4)
                .image(R.drawable.help3)
                .background(R.color.color_400)
                .backgroundDark(R.color.color_400)
                .build());
    }
}