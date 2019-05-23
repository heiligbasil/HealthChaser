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

import com.lambdaschool.healthchaser.healthpoints.Sleep;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.lambdaschool.healthchaser.MainActivity.Tracking;

public class GenericMasterActivity extends AppCompatActivity implements TimePickerFragment.OnCompleteListener, SeekBarFragment.OnFragmentInteractionListener {

    private static final String TAG = "GenericMasterActivity";
    ArrayList<String> stringArrayList = new ArrayList<>();
    private Tracking trackingType;
    private GenericMasterActivityAdapter genericMasterActivityAdapter;
    //private ArrayList<Object> objectArrayList;
    static String viewToUpdateTime;
    int maxDataToCollect;
    int currentDataCollected;
    Sleep sleep;
    private ImageButton pickerButton;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_master);

        Intent intent = getIntent();
        trackingType = (Tracking) intent.getSerializableExtra("tracking");

        //objectArrayList = new ArrayList<>();
        sleep = new Sleep();
        String trackingNodeName;
        int viewFlipperDisplayChild;

        switch (trackingType) {
            case SLEEP:
                trackingNodeName = "sleep";
                viewFlipperDisplayChild = 0;
                maxDataToCollect = 4;
                currentDataCollected = 0;
                break;
            case MEALS:
                trackingNodeName = "";
                viewFlipperDisplayChild = 0;
                break;
            case MOOD:
                trackingNodeName = "";
                viewFlipperDisplayChild = 0;
                break;
            case WATER:
                trackingNodeName = "";
                viewFlipperDisplayChild = 0;
                break;
            case EXERCISE:
                trackingNodeName = "";
                viewFlipperDisplayChild = 0;
                break;
            case RESTROOM:
                trackingNodeName = "";
                viewFlipperDisplayChild = 0;
                break;
            case HYGIENE:
                trackingNodeName = "";
                viewFlipperDisplayChild = 0;
                break;
            case MEDITATION:
                trackingNodeName = "";
                viewFlipperDisplayChild = 0;
                break;
            default:
                trackingNodeName = "";
                viewFlipperDisplayChild = 0;
                break;
        }

        //stringArrayList=FirebaseDao.getAllEntriesForSpecifiedTrackingCategory(trackingNodeName,trackingType);


        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseViewModel firebaseViewModel = ViewModelProviders.of(this).get(FirebaseViewModel.class);
        firebaseViewModel.getAllEntriesForSpecifiedTrackingCategory(trackingNodeName, trackingType).observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(@Nullable ArrayList<String> stringArrayList) {
                genericMasterActivityAdapter = new GenericMasterActivityAdapter(stringArrayList);
                recyclerView.setAdapter(genericMasterActivityAdapter);
                recyclerView.setHasFixedSize(false);
                genericMasterActivityAdapter.notifyDataSetChanged();
            }
        });







      /*  String path = "users/" + currentLoggedInUser.getUserId() + "/" + trackingNodeName;
        DatabaseReference firebaseReference = FirebaseDatabase.getInstance().getReference(path);


//        Sleep sleep = new Sleep(1558496476412L, 1558497702951L, 0, 3);
//        firebaseReference.child(String.valueOf(System.currentTimeMillis())).setValue(sleep);

        firebaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> childrenNodes = dataSnapshot.getChildren();
                for (DataSnapshot ds : childrenNodes) {
                    switch (trackingType) {
                        case SLEEP:
                            Sleep sleep = ds.getValue(Sleep.class);
                            stringArrayList.add(sleep.toString());
                            break;
                        case MEALS:
                            break;
                        case MOOD:
                            break;
                        case WATER:
                            break;
                        case EXERCISE:
                            break;
                        case RESTROOM:
                            break;
                        case HYGIENE:
                            break;
                        case MEDITATION:
                            break;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read value.", error.toException());
            }
        });*/


        ViewFlipper viewFlipper = findViewById(R.id.view_flipper);
        viewFlipper.setDisplayedChild(viewFlipperDisplayChild);


        buttonSave =

                findViewById(R.id.generic_button_save);
        buttonSave.setEnabled(false);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                genericMasterActivityAdapter.notifyDataSetChanged();
            }
        });
    }

    public void showTimePickerDialog(View v) {
        pickerButton = (ImageButton) v;
        viewToUpdateTime = (String) pickerButton.getTag();

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

        switch (viewToUpdateTime) {
            case "sleep_time": {
                calendar.add(Calendar.DATE, -1);
                long timeInMillis = calendar.getTimeInMillis();
                sleep.setSleepTime(timeInMillis);
                textViewById = findViewById(R.id.sleep_text_view_sleep_time);
                break;
            }
            case "awake_time": {
                long timeInMillis = calendar.getTimeInMillis();
                sleep.setAwakeTime(timeInMillis);
                textViewById = findViewById(R.id.sleep_text_view_awake_time);
                break;
            }
            default: {
                textViewById = findViewById(R.id.generic_text_view_heading);
                break;
            }
        }

        textViewById.append(String.format(Locale.getDefault(), " %d:%02d", hourOfDay, minute));
        textViewById.setBackgroundColor(Color.YELLOW);

        pickerButton.setEnabled(false);
        pickerButton.setImageResource(R.color.colorGray);
        currentDataCollected++;

        if (currentDataCollected == maxDataToCollect) {
            buttonSave.setEnabled(true);
        }
    }

    public void showSeekBarDialog(View v) {
        pickerButton = (ImageButton) v;
        viewToUpdateTime = (String) pickerButton.getTag();

        HashMap<Integer, String> mapOfDescriptions = new HashMap<>();
        String textToDisplayPrefix;

        switch (viewToUpdateTime) {
            case "sleep_quality": {
                mapOfDescriptions.putAll(Sleep.qualities);
                textToDisplayPrefix = "Sleep quality: ";
                break;
            }
            case "awake_feeling": {
                mapOfDescriptions.putAll(Sleep.feelings);
                textToDisplayPrefix = "Awake feeling: ";
                break;
            }
            default: {
                mapOfDescriptions.putAll(Sleep.qualities);
                textToDisplayPrefix = "Sleep quality: ";
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
    public void onFragmentInteraction(int seekBarSelection) {

        TextView textViewById;

        String translation = Sleep.qualities.get(seekBarSelection);

        if (translation == null)
            translation = "indeterminate";

        switch (viewToUpdateTime) {
            case "sleep_quality": {
                sleep.setQuality(seekBarSelection);
                translation = Sleep.qualities.get(seekBarSelection);
                textViewById = findViewById(R.id.sleep_text_view_sleep_quality);
                break;
            }
            case "awake_feeling": {
                sleep.setFeeling(seekBarSelection);
                translation = Sleep.feelings.get(seekBarSelection);
                textViewById = findViewById(R.id.sleep_text_view_awake_feeling);
                break;
            }
            default: {
                sleep.setQuality(seekBarSelection);
                translation = Sleep.qualities.get(seekBarSelection);
                textViewById = findViewById(R.id.sleep_text_view_sleep_quality);
                break;
            }
        }

        textViewById.append(" " + translation);
        textViewById.setBackgroundColor(Color.YELLOW);

        pickerButton.setEnabled(false);
        pickerButton.setImageResource(R.color.colorGray);
        currentDataCollected++;

        if (currentDataCollected == maxDataToCollect) {
            buttonSave.setEnabled(true);
        }
    }
}
