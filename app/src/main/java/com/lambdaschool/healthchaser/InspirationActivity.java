package com.lambdaschool.healthchaser;

import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class InspirationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspiration);

        ImageView imageViewGif1 = findViewById(R.id.inspiration_gif_1);
        ImageView imageViewGif2 = findViewById(R.id.inspiration_gif_2);
        ImageView imageViewGif3 = findViewById(R.id.inspiration_gif_3);

        Drawable drawable = imageViewGif1.getDrawable();
        AnimatedImageDrawable animatedImageDrawable = (AnimatedImageDrawable) drawable;
        animatedImageDrawable.start();

        drawable = imageViewGif2.getDrawable();
        animatedImageDrawable = (AnimatedImageDrawable) drawable;
        animatedImageDrawable.start();

        drawable = imageViewGif3.getDrawable();
        animatedImageDrawable = (AnimatedImageDrawable) drawable;
        animatedImageDrawable.start();


    }
}
