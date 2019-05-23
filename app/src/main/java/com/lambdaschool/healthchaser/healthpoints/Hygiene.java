package com.lambdaschool.healthchaser.healthpoints;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Hygiene {

    private long hygieneTime;
    private int hygieneType, hygieneDuration, hygieneTemperature;

    public static final Map<Integer, String> hygieneTypes = new HashMap<Integer, String>() {
        {
            put(-3, "none");
            put(-2, "sponge");
            put(-1, "rain");
            put(0, "shower");
            put(1, "bath");
            put(2, "sauna");
            put(3, "springs");
        }
    };
    public static final Map<Integer, String> hygieneTemperatures = new HashMap<Integer, String>() {
        {
            put(-3, "icy");
            put(-2, "cold");
            put(-1, "cool");
            put(0, "average");
            put(1, "warm");
            put(2, "hot");
            put(3, "blistering");
        }
    };

    public Hygiene() {
    }

    public long getHygieneTime() {
        return hygieneTime;
    }

    public void setHygieneTime(long hygieneTime) {
        this.hygieneTime = hygieneTime;
    }

    public String translateHygieneTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        return simpleDateFormat.format(this.hygieneTime);
    }

    public String translateHygieneDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

        return simpleDateFormat.format(this.hygieneTime);
    }

    public int getHygieneType() {
        return hygieneType;
    }

    public void setHygieneType(int hygieneType) {
        this.hygieneType = hygieneType;
    }

    public String translateHygieneType() {
        String translation = hygieneTypes.get(this.hygieneType);

        if (translation == null)
            translation = "indeterminate";

        return translation;
    }

    public int getHygieneDuration() {
        return hygieneDuration;
    }

    public void setHygieneDuration(int hygieneDuration) {
        this.hygieneDuration = hygieneDuration;
    }

    public int getHygieneTemperature() {
        return hygieneTemperature;
    }

    public void setHygieneTemperature(int hygieneTemperature) {
        this.hygieneTemperature = hygieneTemperature;
    }

    public String translateHygieneTemperature() {
        String translation = hygieneTemperatures.get(this.hygieneTemperature);

        if (translation == null)
            translation = "indeterminate";

        return translation;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "On %s at %s your hygiene of choice was %s with a temperature of %s which lasted %d minutes.",
                translateHygieneDate(), translateHygieneTime(), translateHygieneType(), translateHygieneTemperature(), this.hygieneDuration);
    }
}
