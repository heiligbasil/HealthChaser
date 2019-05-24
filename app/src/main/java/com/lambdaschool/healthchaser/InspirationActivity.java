package com.lambdaschool.healthchaser;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

    public void onClickToView(View view) {

        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(view.getTag().toString()));
        startActivity(intent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notifMgr = (NotificationManager) view.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notifChannel = new NotificationChannel(getPackageName() + ".action", getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
            notifChannel.setDescription("Where people come to take stock of their life");
            notifMgr.createNotificationChannel(notifChannel);
            NotificationCompat.Builder notifCompatBuilder = new NotificationCompat.Builder(view.getContext(), getPackageName() + ".action");
            notifCompatBuilder.setPriority(NotificationManager.IMPORTANCE_HIGH);
            notifCompatBuilder.setContentTitle(getString(R.string.app_name));
            notifCompatBuilder.setContentText("You've seen what the app can do for you. Now share it");
            notifCompatBuilder.setSmallIcon(R.drawable.ic_run_24dp);
            notifCompatBuilder.setColor(Color.BLUE);
            notifCompatBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
            notifMgr.notify(75, notifCompatBuilder.build());
        }
    }
}
