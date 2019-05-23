package com.lambdaschool.healthchaser.healthpoints;

import java.util.HashMap;
import java.util.Map;

public class Restroom {

    private long restroomTime;
    private int restroomType,restroomDuration,restroomAmount;

    public static final Map<Integer, String> restroomTypes = new HashMap<Integer, String>() {
        {
            put(-3, "explosive");
            put(-2, "runny");
            put(-1, "loose");
            put(0, "normal");
            put(1, "heavy");
            put(2, "hard");
            put(3, "constipation");
        }
    };

    public Restroom() {
    }

    public long getRestroomTime() {
        return restroomTime;
    }

    public void setRestroomTime(long restroomTime) {
        this.restroomTime = restroomTime;
    }

    public int getRestroomType() {
        return restroomType;
    }

    public void setRestroomType(int restroomType) {
        this.restroomType = restroomType;
    }

    public String translateRestroomType() {
        String translation = restroomTypes.get(this.restroomType);

        if (translation == null)
            translation = "indeterminate";

        return translation;
    }

    public int getRestroomDuration() {
        return restroomDuration;
    }

    public void setRestroomDuration(int restroomDuration) {
        this.restroomDuration = restroomDuration;
    }

    public int getRestroomAmount() {
        return restroomAmount;
    }

    public void setRestroomAmount(int restroomAmount) {
        this.restroomAmount = restroomAmount;
    }

}
