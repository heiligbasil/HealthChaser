package com.lambdaschool.healthchaser.healthpoints;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Sleep {
    private long sleepTime, awakeTime;
    private int quality, feeling;

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
        String translation = "";

        switch (this.quality) {
            case -3:
                translation = "agitated";
                break;
            case -2:
                translation = "sleepless";
                break;
            case -1:
                translation = "unpleasant";
                break;
            case 0:
                translation = "forgettable";
                break;
            case 1:
                translation = "adequate";
                break;
            case 2:
                translation = "serene";
                break;
            case 3:
                translation = "perfect";
                break;
            default:
                translation = "indeterminate";
                break;
        }

        return translation;
    }

    public int getFeeling() {
        return feeling;
    }

    public void setFeeling(int feeling) {
        this.feeling = feeling;
    }

    public String translateFeeling() {
        String translation = "";

        switch (this.feeling) {
            case -3:
                translation = "horrible";
                break;
            case -2:
                translation = "bad";
                break;
            case -1:
                translation = "unsatisfactory";
                break;
            case 0:
                translation = "ambivalent";
                break;
            case 1:
                translation = "good";
                break;
            case 2:
                translation = "refreshed";
                break;
            case 3:
                translation = "amazing";
                break;
            default:
                translation = "indeterminate";
                break;
        }

        return translation;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(),"You went to bed at %s and woke up at %s. Quality of sleep was %s and you felt %s.",
                translateSleepTime(), translateAwakeTime(), translateQuality(), translateFeeling());
    }
}
