package com.lambdaschool.healthchaser.healthpoints;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Restroom {

    private long restroomTime;
    private int restroomType, restroomDuration, restroomAmount;

    public static final Map<Integer, String> restroomTypes = new HashMap<Integer, String>() {
        {
            put(-3, "explosive");
            put(-2, "runny");
            put(-1, "loose");
            put(0, "normal");
            put(1, "heavy");
            put(2, "hard");
            put(3, "constipated");
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

    public String translateRestroomTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        return simpleDateFormat.format(this.restroomTime);
    }

    public String translateRestroomDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

        return simpleDateFormat.format(this.restroomTime);
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

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "On %s at %s you had %d restroom visits lasting an average of %d minutes which broadly could be described as %s.",
                translateRestroomDate(), translateRestroomTime(), this.restroomAmount, this.restroomDuration, translateRestroomType());
    }
}
