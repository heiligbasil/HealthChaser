package com.lambdaschool.healthchaser.connectivity;

import android.graphics.Bitmap;

public class LoggedInUserDao {

    public Bitmap getImage(final String url) {
        final Bitmap[] bitmap = new Bitmap[1];

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                bitmap[0] = NetworkAdapter.httpImageRequest(url);
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        try {
            thread.join();
        } catch (
                InterruptedException e) {
            e.printStackTrace();
        }

        return bitmap[0];
    }
}

