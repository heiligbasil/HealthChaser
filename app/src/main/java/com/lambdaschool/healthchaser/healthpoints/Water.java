package com.lambdaschool.healthchaser.healthpoints;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Water {

    private int waterQuantity, intakeFrequency;

    public static final Map<Integer, String> intakeFrequencies = new HashMap<Integer, String>() {
        {
            put(-3, "rarely");
            put(-2, "infrequently");
            put(-1, "sporadically");
            put(0, "occasionally");
            put(1, "evenly");
            put(2, "often");
            put(3, "constantly");
        }
    };

    public Water() {
    }

    public int getWaterQuantity() {
        return waterQuantity;
    }

    public void setWaterQuantity(int waterQuantity) {
        this.waterQuantity = waterQuantity;
    }

    public int getIntakeFrequency() {
        return intakeFrequency;
    }

    public void setIntakeFrequency(int intakeFrequency) {
        this.intakeFrequency = intakeFrequency;
    }

    public String translateIntakeFrequency() {
        String translation = intakeFrequencies.get(this.intakeFrequency);

        if (translation == null)
            translation = "indeterminate";

        return translation;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "You drank %d glasses of water at a frequency described as %s.",
                this.waterQuantity, translateIntakeFrequency());
    }

}
