package com.lambdaschool.healthchaser;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lambdaschool.healthchaser.firebase.FirebaseViewModel;
import com.lambdaschool.healthchaser.fragments.NumberPickerFragment;
import com.lambdaschool.healthchaser.fragments.SeekBarFragment;
import com.lambdaschool.healthchaser.fragments.TimePickerFragment;
import com.lambdaschool.healthchaser.healthpoints.Exercise;
import com.lambdaschool.healthchaser.healthpoints.Hygiene;
import com.lambdaschool.healthchaser.healthpoints.Meals;
import com.lambdaschool.healthchaser.healthpoints.Meditation;
import com.lambdaschool.healthchaser.healthpoints.Mood;
import com.lambdaschool.healthchaser.healthpoints.Restroom;
import com.lambdaschool.healthchaser.healthpoints.Sleep;
import com.lambdaschool.healthchaser.healthpoints.Water;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import static com.lambdaschool.healthchaser.MainActivity.Tracking;
import static com.lambdaschool.healthchaser.MainActivity.currentLoggedInUser;

public class GenericMasterActivity extends AppCompatActivity implements TimePickerFragment.OnCompleteListener, SeekBarFragment.OnSeekBarFragmentInteractionListener, NumberPickerFragment.OnNumberPickerFragmentInteractionListener {

    private Tracking trackingType;
    private String path;
    private GenericMasterActivityAdapter genericMasterActivityAdapter;
    static String corespondingTaggedView;
    private Object object;
    private ImageButton pickerButton;
    private Button buttonSave;
    private TextView textViewResults;
    int maxDataToCollect;
    int currentDataCollected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_master);

        TextView textViewHeading = findViewById(R.id.generic_text_view_heading);
        textViewResults = findViewById(R.id.generic_text_view_results);

        Intent intent = getIntent();
        trackingType = (Tracking) intent.getSerializableExtra("tracking");
        String trackingNodeName;
        int viewFlipperDisplayChild;

        switch (trackingType) {
            case SLEEP:
                object = new Sleep();
                trackingNodeName = "sleep";
                viewFlipperDisplayChild = 0;
                maxDataToCollect = 4;
                currentDataCollected = 0;
                textViewHeading.setText(R.string.menu_sleep);
                break;
            case MEALS:
                object = new Meals();
                trackingNodeName = "meals";
                viewFlipperDisplayChild = 1;
                maxDataToCollect = 4;
                currentDataCollected = 0;
                textViewHeading.setText(R.string.menu_meals);
                break;
            case MOOD:
                object = new Mood();
                trackingNodeName = "mood";
                viewFlipperDisplayChild = 2;
                maxDataToCollect = 2;
                currentDataCollected = 0;
                textViewHeading.setText(R.string.menu_mood);
                break;
            case WATER:
                object = new Water();
                trackingNodeName = "water";
                viewFlipperDisplayChild = 3;
                maxDataToCollect = 2;
                currentDataCollected = 0;
                textViewHeading.setText(R.string.menu_water);
                break;
            case EXERCISE:
                object = new Exercise();
                trackingNodeName = "exercise";
                viewFlipperDisplayChild = 4;
                maxDataToCollect = 4;
                currentDataCollected = 0;
                textViewHeading.setText(R.string.menu_exercise);
                break;
            case RESTROOM:
                object = new Restroom();
                trackingNodeName = "restroom";
                viewFlipperDisplayChild = 5;
                maxDataToCollect = 4;
                currentDataCollected = 0;
                textViewHeading.setText(R.string.menu_restroom);
                break;
            case HYGIENE:
                object = new Hygiene();
                trackingNodeName = "hygiene";
                viewFlipperDisplayChild = 6;
                maxDataToCollect = 4;
                currentDataCollected = 0;
                textViewHeading.setText(R.string.menu_hygiene);
                break;
            case MEDITATION:
                object = new Meditation();
                trackingNodeName = "meditation";
                viewFlipperDisplayChild = 7;
                maxDataToCollect = 4;
                currentDataCollected = 0;
                textViewHeading.setText(R.string.menu_meditation);
                break;
            default:
                object = new Sleep();
                trackingNodeName = "sleep";
                viewFlipperDisplayChild = 1;
                maxDataToCollect = 4;
                currentDataCollected = 0;
                textViewHeading.setText(R.string.menu_sleep);
                break;
        }

        path = "users/" + currentLoggedInUser.getUserId() + "/" + trackingNodeName;

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseViewModel firebaseViewModel = ViewModelProviders.of(this).get(FirebaseViewModel.class);
        firebaseViewModel.getAllEntriesForSpecifiedTrackingCategory(path, trackingType).observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(@Nullable ArrayList<String> stringArrayList) {
                genericMasterActivityAdapter = new GenericMasterActivityAdapter(stringArrayList);
                recyclerView.setAdapter(genericMasterActivityAdapter);
                recyclerView.setHasFixedSize(false);
                genericMasterActivityAdapter.notifyDataSetChanged();
            }
        });

        ViewFlipper viewFlipper = findViewById(R.id.view_flipper);
        viewFlipper.setDisplayedChild(viewFlipperDisplayChild);


        buttonSave = findViewById(R.id.generic_button_save);
        buttonSave.setEnabled(false);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference firebaseReference = FirebaseDatabase.getInstance().getReference(path);
                firebaseReference.child(String.valueOf(System.currentTimeMillis())).setValue(object);

                genericMasterActivityAdapter.notifyDataSetChanged();

                v.setEnabled(false);
            }
        });
    }

    public void showTimePickerDialog(View v) {
        pickerButton = (ImageButton) v;
        corespondingTaggedView = (String) pickerButton.getTag();

        DialogFragment newFragment = new TimePickerFragment();
        newFragment.setCancelable(false);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onComplete(int hourOfDay, int minute) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        TextView textViewById;
        String textToAppend;

        switch (corespondingTaggedView) {
            case "sleep_time": {
                calendar.add(Calendar.DATE, -1);
                long timeInMillis = calendar.getTimeInMillis();
                ((Sleep) object).setSleepTime(timeInMillis);
                textViewById = findViewById(R.id.sleep_text_view_sleep_time);
                textToAppend = String.format(Locale.getDefault(), "On %d/%d/%d you slept at %d:%02d. ",
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE));
                break;
            }
            case "awake_time": {
                long timeInMillis = calendar.getTimeInMillis();
                ((Sleep) object).setAwakeTime(timeInMillis);
                textViewById = findViewById(R.id.sleep_text_view_awake_time);
                textToAppend = String.format(Locale.getDefault(), "On %d/%d/%d you awoke at %d:%02d. ",
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE));
                break;
            }
            case "exercise_time": {
                long timeInMillis = calendar.getTimeInMillis();
                ((Exercise) object).setExerciseTime(timeInMillis);
                textViewById = findViewById(R.id.exercise_text_view_time);
                textToAppend = String.format(Locale.getDefault(), "On %d/%d/%d you exercised at %d:%02d. ",
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE));
                break;
            }
            case "restroom_time": {
                long timeInMillis = calendar.getTimeInMillis();
                ((Restroom) object).setRestroomTime(timeInMillis);
                textViewById = findViewById(R.id.restroom_text_view_time);
                textToAppend = String.format(Locale.getDefault(), "On %d/%d/%d you restroomed at %d:%02d. ",
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE));
                break;
            }
            case "hygiene_time": {
                long timeInMillis = calendar.getTimeInMillis();
                ((Hygiene) object).setHygieneTime(timeInMillis);
                textViewById = findViewById(R.id.hygiene_text_view_time);
                textToAppend = String.format(Locale.getDefault(), "On %d/%d/%d you bathed at %d:%02d. ",
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE));
                break;
            }
            case "meditation_time": {
                long timeInMillis = calendar.getTimeInMillis();
                ((Meditation) object).setMeditationTime(timeInMillis);
                textViewById = findViewById(R.id.meditation_text_view_time);
                textToAppend = String.format(Locale.getDefault(), "On %d/%d/%d you meditated at %d:%02d. ",
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE));
                break;
            }
            default: {
                textViewById = findViewById(R.id.generic_text_view_results);
                textToAppend = "An unexpected occurrence. ";
                break;
            }
        }

        textViewById.append(String.format(Locale.getDefault(), " %d:%02d", hourOfDay, minute));
        textViewById.setBackgroundColor(Color.YELLOW);
        textViewResults.append(" " + textToAppend);

        pickerButton.setEnabled(false);
        pickerButton.setImageResource(R.color.colorGray);
        currentDataCollected++;

        if (currentDataCollected == maxDataToCollect) {
            buttonSave.setEnabled(true);
        }
    }

    public void showSeekBarDialog(View v) {
        pickerButton = (ImageButton) v;
        corespondingTaggedView = (String) pickerButton.getTag();

        HashMap<Integer, String> mapOfDescriptions = new HashMap<>();
        String textToDisplayPrefix;

        switch (corespondingTaggedView) {
            case "sleep_quality": {
                mapOfDescriptions.putAll(Sleep.sleepQualities);
                textToDisplayPrefix = "Sleep quality: ";
                break;
            }
            case "awake_feeling": {
                mapOfDescriptions.putAll(Sleep.awakeFeelings);
                textToDisplayPrefix = "Awake feeling: ";
                break;
            }
            case "food_quality": {
                mapOfDescriptions.putAll(Meals.foodQualities);
                textToDisplayPrefix = "Food quality: ";
                break;
            }
            case "hungry_overate": {
                mapOfDescriptions.putAll(Meals.hungryOverateChoices);
                textToDisplayPrefix = "Hungry/overate: ";
                break;
            }
            case "meals_after_feeling": {
                mapOfDescriptions.putAll(Meals.afterFeelings);
                textToDisplayPrefix = "After meals feeling: ";
                break;
            }
            case "mood_type": {
                mapOfDescriptions.putAll(Mood.moodTypes);
                textToDisplayPrefix = "Mood type: ";
                break;
            }
            case "mood_reason": {
                mapOfDescriptions.putAll(Mood.moodReasons);
                textToDisplayPrefix = "Mood reason: ";
                break;
            }
            case "intake_frequency": {
                mapOfDescriptions.putAll(Water.intakeFrequencies);
                textToDisplayPrefix = "Intake frequency: ";
                break;
            }
            case "exercise_type": {
                mapOfDescriptions.putAll(Exercise.exerciseTypes);
                textToDisplayPrefix = "Exercise type: ";
                break;
            }
            case "exercise_after_feeling": {
                mapOfDescriptions.putAll(Exercise.afterFeelings);
                textToDisplayPrefix = "After exercise feeling: ";
                break;
            }
            case "restroom_type": {
                mapOfDescriptions.putAll(Restroom.restroomTypes);
                textToDisplayPrefix = "Restroom type: ";
                break;
            }
            case "hygiene_type": {
                mapOfDescriptions.putAll(Hygiene.hygieneTypes);
                textToDisplayPrefix = "Hygiene type: ";
                break;
            }
            case "hygiene_temperature": {
                mapOfDescriptions.putAll(Hygiene.hygieneTemperatures);
                textToDisplayPrefix = "Hygiene temperature: ";
                break;
            }
            case "meditation_type": {
                mapOfDescriptions.putAll(Meditation.meditationTypes);
                textToDisplayPrefix = "Meditation type: ";
                break;
            }
            case "meditation_after_feeling": {
                mapOfDescriptions.putAll(Meditation.afterFeelings);
                textToDisplayPrefix = "After meditation feeling: ";
                break;
            }
            default: {
                mapOfDescriptions.put(0, "error");
                textToDisplayPrefix = "An unexpected occurrence: ";
                break;
            }
        }

        SeekBarFragment seekBarFragment = new SeekBarFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SeekBarFragment.ARG_PARAM1, mapOfDescriptions);
        bundle.putString(SeekBarFragment.ARG_PARAM2, textToDisplayPrefix);
        seekBarFragment.setArguments(bundle);

        seekBarFragment.setStyle(DialogFragment.STYLE_NORMAL, 0);
        seekBarFragment.show(getSupportFragmentManager(), SeekBarFragment.ARG_PARAM1);
    }

    @Override
    public void onSeekBarFragmentInteraction(int seekBarSelection) {

        TextView textViewById;
        String textToAppend;

        String translation;

        switch (corespondingTaggedView) {
            case "sleep_quality": {
                ((Sleep) object).setSleepQuality(seekBarSelection);
                translation = Sleep.sleepQualities.get(seekBarSelection);
                textViewById = findViewById(R.id.sleep_text_view_sleep_quality);
                textToAppend = "Quality of sleep was " + translation + ". ";
                break;
            }
            case "awake_feeling": {
                ((Sleep) object).setAwakeFeeling(seekBarSelection);
                translation = Sleep.awakeFeelings.get(seekBarSelection);
                textViewById = findViewById(R.id.sleep_text_view_awake_feeling);
                textToAppend = "You awoke feeling " + translation + ". ";
                break;
            }
            case "food_quality": {
                ((Meals) object).setFoodQuality(seekBarSelection);
                translation = Meals.foodQualities.get(seekBarSelection);
                textViewById = findViewById(R.id.meals_text_view_food_quality);
                textToAppend = "Your food quality was " + translation + ". ";
                break;
            }
            case "hungry_overate": {
                ((Meals) object).setHungryOverate(seekBarSelection);
                translation = Meals.hungryOverateChoices.get(seekBarSelection);
                textViewById = findViewById(R.id.meals_text_view_hungry_overate);
                textToAppend = "Your hunger/overeat status was " + translation + ". ";
                break;
            }
            case "meals_after_feeling": {
                ((Meals) object).setAfterFeeling(seekBarSelection);
                translation = Meals.afterFeelings.get(seekBarSelection);
                textViewById = findViewById(R.id.meals_text_view_after_feeling);
                textToAppend = "Your meal left you feeling " + translation + ". ";
                break;
            }
            case "mood_type": {
                ((Mood) object).setMoodType(seekBarSelection);
                translation = Mood.moodTypes.get(seekBarSelection);
                textViewById = findViewById(R.id.mood_text_view_type);
                textToAppend = "Your mood is " + translation + ". ";
                break;
            }
            case "mood_reason": {
                ((Mood) object).setMoodReason(seekBarSelection);
                translation = Mood.moodReasons.get(seekBarSelection);
                textViewById = findViewById(R.id.mood_text_view_reason);
                textToAppend = "The reason for your mood is " + translation + ". ";
                break;
            }
            case "intake_frequency": {
                ((Water) object).setIntakeFrequency(seekBarSelection);
                translation = Water.intakeFrequencies.get(seekBarSelection);
                textViewById = findViewById(R.id.water_text_view_intake_frequency);
                textToAppend = "You described your water intake frequency as " + translation + ". ";
                break;
            }
            case "exercise_type": {
                ((Exercise) object).setExerciseType(seekBarSelection);
                translation = Exercise.exerciseTypes.get(seekBarSelection);
                textViewById = findViewById(R.id.exercise_text_view_type);
                textToAppend = "You did " + translation + ". ";
                break;
            }
            case "exercise_after_feeling": {
                ((Exercise) object).setAfterFeeling(seekBarSelection);
                translation = Exercise.afterFeelings.get(seekBarSelection);
                textViewById = findViewById(R.id.exercise_text_view_after_feeling);
                textToAppend = "The exercise left you feeling " + translation + ". ";
                break;
            }
            case "restroom_type": {
                ((Restroom) object).setRestroomType(seekBarSelection);
                translation = Restroom.restroomTypes.get(seekBarSelection);
                textViewById = findViewById(R.id.restroom_text_view_type);
                textToAppend = "Your restroom visit was " + translation + ". ";
                break;
            }
            case "hygiene_type": {
                ((Hygiene) object).setHygieneType(seekBarSelection);
                translation = Hygiene.hygieneTypes.get(seekBarSelection);
                textViewById = findViewById(R.id.hygiene_text_view_type);
                textToAppend = "Your hygiene method was " + translation + ". ";
                break;
            }
            case "hygiene_temperature": {
                ((Hygiene) object).setHygieneTemperature(seekBarSelection);
                translation = Hygiene.hygieneTemperatures.get(seekBarSelection);
                textViewById = findViewById(R.id.hygiene_text_view_temperature);
                textToAppend = "Your hygiene temperature was " + translation + ". ";
                break;
            }
            case "meditation_type": {
                ((Meditation) object).setMeditationType(seekBarSelection);
                translation = Meditation.meditationTypes.get(seekBarSelection);
                textViewById = findViewById(R.id.meditation_text_view_type);
                textToAppend = "You meditated using " + translation + ". ";
                break;
            }
            case "meditation_after_feeling": {
                ((Meditation) object).setAfterFeeling(seekBarSelection);
                translation = Meditation.afterFeelings.get(seekBarSelection);
                textViewById = findViewById(R.id.meditation_text_view_after_feeling);
                textToAppend = "Meditation left you feeling " + translation + ". ";
                break;
            }
            default: {
                textViewById = findViewById(R.id.generic_text_view_results);
                textToAppend = "error. ";
                translation = "An unexpected occurrence: ";
                break;
            }
        }

        textViewById.append(" " + translation);
        textViewById.setBackgroundColor(Color.YELLOW);
        textViewResults.append(" " + textToAppend);

        pickerButton.setEnabled(false);
        pickerButton.setImageResource(R.color.colorGray);
        currentDataCollected++;

        if (currentDataCollected == maxDataToCollect) {
            buttonSave.setEnabled(true);
        }
    }

    public void showNumberPickerDialog(View v) {
        pickerButton = (ImageButton) v;
        corespondingTaggedView = (String) pickerButton.getTag();

        String unitsForNumberPicker;
        int minValueForNumberPicker;
        int maxValueForNumberPicker;

        switch (corespondingTaggedView) {
            case "food_amount": {
                unitsForNumberPicker = "portions";
                minValueForNumberPicker = 0;
                maxValueForNumberPicker = 9;
                break;
            }
            case "water_quantity": {
                unitsForNumberPicker = "glasses";
                minValueForNumberPicker = 0;
                maxValueForNumberPicker = 10;
                break;
            }
            case "exercise_duration": {
                unitsForNumberPicker = "minutes";
                minValueForNumberPicker = 0;
                maxValueForNumberPicker = 120;
                break;
            }
            case "restroom_duration": {
                unitsForNumberPicker = "minutes";
                minValueForNumberPicker = 0;
                maxValueForNumberPicker = 20;
                break;
            }
            case "restroom_amount": {
                unitsForNumberPicker = "visits";
                minValueForNumberPicker = 0;
                maxValueForNumberPicker = 5;
                break;
            }
            case "hygiene_duration": {
                unitsForNumberPicker = "minutes";
                minValueForNumberPicker = 0;
                maxValueForNumberPicker = 45;
                break;
            }
            case "meditation_duration": {
                unitsForNumberPicker = "minutes";
                minValueForNumberPicker = 0;
                maxValueForNumberPicker = 60;
                break;
            }
            default: {
                unitsForNumberPicker = "error";
                minValueForNumberPicker = 0;
                maxValueForNumberPicker = 0;
                break;
            }
        }

        NumberPickerFragment numberPickerFragment = new NumberPickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NumberPickerFragment.ARG_PARAM1, unitsForNumberPicker);
        bundle.putInt(NumberPickerFragment.ARG_PARAM2, minValueForNumberPicker);
        bundle.putInt(NumberPickerFragment.ARG_PARAM3, maxValueForNumberPicker);
        numberPickerFragment.setArguments(bundle);

        numberPickerFragment.setStyle(DialogFragment.STYLE_NORMAL, 0);
        numberPickerFragment.show(getSupportFragmentManager(), NumberPickerFragment.ARG_PARAM1);
    }

    @Override
    public void onNumberPickerFragmentInteraction(int numberPickerSelection) {

        TextView textViewById;
        String textToAppend;
        String translation;

        switch (corespondingTaggedView) {
            case "food_amount": {
                ((Meals) object).setFoodAmount(numberPickerSelection);
                translation = " "+numberPickerSelection+" portions";
                textViewById = findViewById(R.id.meals_text_view_food_amount);
                textToAppend = " You ate " + translation + ". ";
                break;
            }
            case "water_quantity": {
                ((Water) object).setWaterQuantity(numberPickerSelection);
                translation = " "+numberPickerSelection+ " glasses";
                textViewById = findViewById(R.id.water_text_view_quantity);
                textToAppend = " You drank " + translation + ". ";
                break;
            }
            case "exercise_duration": {
                ((Exercise) object).setExerciseDuration(numberPickerSelection);
                translation = " "+numberPickerSelection+" minutes";
                textViewById = findViewById(R.id.exercise_text_view_duration);
                textToAppend = " You exercised for " + translation + ". ";
                break;
            }
            case "restroom_duration": {
                ((Restroom) object).setRestroomDuration(numberPickerSelection);
                translation = " "+numberPickerSelection+" minutes";
                textViewById = findViewById(R.id.restroom_text_view_duration);
                textToAppend = " Your restroom visits lasted an average of " + translation + ". ";
                break;
            }
            case "restroom_amount": {
                ((Restroom) object).setRestroomAmount(numberPickerSelection);
                translation = " "+numberPickerSelection+" times";
                textViewById = findViewById(R.id.restroom_text_view_amount);
                textToAppend = " You visited the restroom " + translation + ". ";
                break;
            }
            case "hygiene_duration": {
                ((Hygiene) object).setHygieneDuration(numberPickerSelection);
                translation = " "+numberPickerSelection+" minutes";
                textViewById = findViewById(R.id.hygiene_text_view_duration);
                textToAppend = " Your hygiene time lasted " + translation + ". ";
                break;
            }
            case "meditation_duration": {
                ((Meditation) object).setMeditationDuration(numberPickerSelection);
                translation = " "+numberPickerSelection+" minutes";
                textViewById = findViewById(R.id.meditation_text_view_duration);
                textToAppend = " You spent " + translation + " in meditation. ";
                break;
            }
            default: {
                textViewById = findViewById(R.id.generic_text_view_results);
                textToAppend = " error. ";
                translation = " An unexpected occurrence: ";
                break;
            }
        }

        textViewById.append(translation);
        textViewById.setBackgroundColor(Color.YELLOW);
        textViewResults.append(textToAppend);

        pickerButton.setEnabled(false);
        pickerButton.setImageResource(R.color.colorGray);
        currentDataCollected++;

        if (currentDataCollected == maxDataToCollect) {
            buttonSave.setEnabled(true);
        }
    }
}
