package com.lambdaschool.healthchaser.healthpoints;

import java.util.HashMap;
import java.util.Map;

public class Meals {

    private int foodQuality, foodAmount, hungryOverate, afterFeeling;

    public static final Map<Integer, String> foodQualities = new HashMap<Integer, String>() {
        {
            put(-3, "trash");
            put(-2, "junk");
            put(-1, "iffy");
            put(0, "average");
            put(1, "healthy");
            put(2, "nutritious");
            put(3, "rejuvenating");
        }
    };
    public static final Map<Integer, String> hungryOverateChoices = new HashMap<Integer, String>() {
        {
            put(-3, "painful");
            put(-2, "stuffed");
            put(-1, "full");
            put(0, "perfect");
            put(1, "room");
            put(2, "hungry");
            put(3, "starving");
        }
    };
    public static final Map<Integer, String> afterFeelings = new HashMap<Integer, String>() {
        {
            put(-3, "bloated");
            put(-2, "sleepy");
            put(-1, "guilty");
            put(0, "satisfied");
            put(1, "good");
            put(2, "energized");
            put(3, "rejuvenated");
        }
    };

    public Meals() {
    }

    public int getFoodQuality() {
        return foodQuality;
    }

    public void setFoodQuality(int foodQuality) {
        this.foodQuality = foodQuality;
    }

    public String translateFoodQuality() {
        String translation = foodQualities.get(this.foodQuality);

        if (translation == null)
            translation = "indeterminate";

        return translation;
    }

    public int getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(int foodAmount) {
        this.foodAmount = foodAmount;
    }

    public int getHungryOverate() {
        return hungryOverate;
    }

    public void setHungryOverate(int hungryOverate) {
        this.hungryOverate = hungryOverate;
    }

    public String translateHungryOverate() {
        String translation = hungryOverateChoices.get(this.hungryOverate);

        if (translation == null)
            translation = "indeterminate";

        return translation;
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
