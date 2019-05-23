package com.lambdaschool.healthchaser.healthpoints;

import android.support.annotation.NonNull;

import java.util.Locale;

public class Water {

    private int waterQuantity, intakeFrequency;

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

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "You drank %d glasses of water at an average frequency of %d times.",
                this.waterQuantity, this.intakeFrequency);
    }

}
