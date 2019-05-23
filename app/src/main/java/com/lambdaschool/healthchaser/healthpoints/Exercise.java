package com.lambdaschool.healthchaser.healthpoints;

import java.util.HashMap;
import java.util.Map;

public class Exercise {

    private long exerciseTime;
    private int exerciseType,exerciseDuration,afterFeeling;

    public static final Map<Integer, String> exerciseTypes = new HashMap<Integer, String>() {
        {
            put(-3, "breathing");
            put(-2, "sitting");
            put(-1, "walking");
            put(0, "hiking");
            put(1, "running");
            put(2, "swimming");
            put(3, "sports");
        }
    };
    public static final Map<Integer, String> afterFeelings = new HashMap<Integer, String>() {
        {
            put(-3, "destroyed");
            put(-2, "exhausted");
            put(-1, "beat");
            put(0, "nothing");
            put(1, "good");
            put(2, "ready");
            put(3, "strong");
        }
    };

    public Exercise() {
    }

    public long getExerciseTime() {
        return exerciseTime;
    }

    public void setExerciseTime(long exerciseTime) {
        this.exerciseTime = exerciseTime;
    }

    public int getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(int exerciseType) {
        this.exerciseType = exerciseType;
    }

    public String translateExerciseType() {
        String translation = exerciseTypes.get(this.exerciseType);

        if (translation == null)
            translation = "indeterminate";

        return translation;
    }

    public int getExerciseDuration() {
        return exerciseDuration;
    }

    public void setExerciseDuration(int exerciseDuration) {
        this.exerciseDuration = exerciseDuration;
    }

    public int getAfterFeeling() {
        return afterFeeling;
    }

    public void setAfterFeeling(int afterFeeling) {
        this.afterFeeling = afterFeeling;
    }

    public String translateAfterFeeling() {
        String translation = afterFeelings.get(this.afterFeeling);

        if (translation == null)
            translation = "indeterminate";

        return translation;
    }
}
