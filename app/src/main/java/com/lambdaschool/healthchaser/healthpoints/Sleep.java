package com.lambdaschool.healthchaser.healthpoints;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Sleep {

    private long sleepTime, awakeTime;
    private int quality, feeling;

    public static final Map<Integer, String> feelings = new HashMap<Integer, String>() {
        {
            put(-3, "horrible");
            put(-2, "bad");
            put(-1, "unsatisfactory");
            put(0, "ambivalent");
            put(1, "good");
            put(2, "refreshed");
            put(3, "amazing");
        }
    };
    public static final Map<Integer, String> qualities = new HashMap<Integer, String>() {
        {
            put(-3, "agitated");
            put(-2, "sleepless");
            put(-1, "unpleasant");
            put(0, "forgettable");
            put(1, "adequate");
            put(2, "serene");
            put(3, "perfect");
        }
    };

    public Sleep() {
    }

    public Sleep(long sleepTime, long awakeTime, int quality, int feeling) {
        this.sleepTime = sleepTime;
        this.awakeTime = awakeTime;
        this.quality = quality;
        this.feeling = feeling;
    }

    public long getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    public String translateSleepTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        return simpleDateFormat.format(this.sleepTime);
    }

    public String translateSleepDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

        return simpleDateFormat.format(this.sleepTime);
    }

    public long getAwakeTime() {
        return awakeTime;
    }

    public void setAwakeTime(long awakeTime) {
        this.awakeTime = awakeTime;
    }

    public String translateAwakeTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        return simpleDateFormat.format(this.awakeTime);
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String translateQuality() {
        String translation = qualities.get(this.quality);

        if (translation == null)
            translation = "indeterminate";

        return translation;
    }

    public int getFeeling() {
        return feeling;
    }

    public void setFeeling(int feeling) {
        this.feeling = feeling;
    }

    public String translateFeeling() {
        String translation = feelings.get(this.feeling);

        if (translation == null)
            translation = "indeterminate";

        return translation;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "On %s you slept at %s and awoke at %s. Your quality of sleep was %s and you felt %s.",
                translateSleepDate(), translateSleepTime(), translateAwakeTime(), translateQuality(), translateFeeling());
    }
}
