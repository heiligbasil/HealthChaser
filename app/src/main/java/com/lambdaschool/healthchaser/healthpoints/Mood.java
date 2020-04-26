package com.lambdaschool.healthchaser.healthpoints;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Mood {

    private int moodType, moodReason;

    public static final Map<Integer, String> moodTypes = new HashMap<Integer, String>() {
        {
            put(-3, "fearful");
            put(-2, "anxious");
            put(-1, "angry");
            put(0, "average");
            put(1, "happy");
            put(2, "serene");
            put(3, "excited");
        }
    };
    public static final Map<Integer, String> moodReasons = new HashMap<Integer, String>() {
        {
            put(-3, "life");
            put(-2, "someone");
            put(-1, "something");
            put(0, "nothing");
            put(1, "circumstances");
            put(2, "choices");
            put(3, "dreams");
        }
    };

    public Mood() {
    }

    public int getMoodType() {
        return moodType;
    }

    public void setMoodType(int moodType) {
        this.moodType = moodType;
    }

    public String translateMoodType() {
        String translation = moodTypes.get(this.moodType);

        if (translation == null)
            translation = "indeterminate";

        return translation;
    }

    public int getMoodReason() {
        return moodReason;
    }

    public void setMoodReason(int moodReason) {
        this.moodReason = moodReason;
    }

    public String translateMoodReasons() {
        String translation = moodReasons.get(this.moodReason);

        if (translation == null)
            translation = "indeterminate";

        return translation;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Your mood is %s and the reason for that is because of your %s.",
                translateMoodType(), translateMoodReasons());
    }
}
