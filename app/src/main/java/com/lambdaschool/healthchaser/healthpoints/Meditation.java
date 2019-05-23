package com.lambdaschool.healthchaser.healthpoints;

import java.util.HashMap;
import java.util.Map;

public class Meditation {

    private long meditationTime;
    private int meditationType,meditationDuration,meditationFeeling;

    public static final Map<Integer, String> meditationTypes = new HashMap<Integer, String>() {
        {
            put(-3, "mantra");
            put(-2, "images");
            put(-1, "nature");
            put(0, "still");
            put(1, "thoughts");
            put(2, "breath");
            put(3, "body");
        }
    };

    public static final Map<Integer, String> afterFeelings = new HashMap<Integer, String>() {
        {
            put(-3, "angry");
            put(-2, "agitated");
            put(-1, "spacey");
            put(0, "normal");
            put(1, "calm");
            put(2, "relaxed");
            put(3, "confident");
        }
    };

    public Meditation() {
    }

    public long getMeditationTime() {
        return meditationTime;
    }

    public void setMeditationTime(long meditationTime) {
        this.meditationTime = meditationTime;
    }

    public int getMeditationType() {
        return meditationType;
    }

    public void setMeditationType(int meditationType) {
        this.meditationType = meditationType;
    }

    public String translateMeditationType() {
        String translation = meditationTypes.get(this.meditationType);

        if (translation == null)
            translation = "indeterminate";

        return translation;
    }

    public int getMeditationDuration() {
        return meditationDuration;
    }

    public void setMeditationDuration(int meditationDuration) {
        this.meditationDuration = meditationDuration;
    }

    public int getMeditationFeeling() {
        return meditationFeeling;
    }

    public void setMeditationFeeling(int meditationFeeling) {
        this.meditationFeeling = meditationFeeling;
    }

    public String translateAfterFeeling() {
        String translation = afterFeelings.get(this.meditationFeeling);

        if (translation == null)
            translation = "indeterminate";

        return translation;
    }
}
