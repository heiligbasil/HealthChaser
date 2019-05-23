package com.lambdaschool.healthchaser;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import android.content.Intent;
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

import static com.lambdaschool.healthchaser.MainActivity.Tracking;

public class GenericMasterActivity extends AppCompatActivity implements TimePickerFragment.OnCompleteListener {

    private static final String TAG = "GenericMasterActivity";
    ArrayList<String> stringArrayList = new ArrayList<>();
    private Tracking trackingType;
    private GenericMasterActivityAdapter genericMasterActivityAdapter;
    private ArrayList<Object> objectArrayList;
    static String viewToUpdateTime;
    Sleep sleep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_master);

        Intent intent = getIntent();
        trackingType = (Tracking) intent.getSerializableExtra("tracking");

        objectArrayList = new ArrayList<>();
        sleep=new Sleep();
        String trackingNodeName = "";
        int viewFlipperDisplayChild = 0;

        switch (trackingType) {
            case SLEEP:
                trackingNodeName = "sleep";
                viewFlipperDisplayChild = 0;
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


        ((Button) findViewById(R.id.generic_button_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                genericMasterActivityAdapter.notifyDataSetChanged();
            }
        });
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.setCancelable(false);
        viewToUpdateTime = (String) v.getTag();
        newFragment.show(getSupportFragmentManager(), "timePicker");

        /*if (v.getTag().toString().equals("bed_time")) {
            ((TextView)findViewById(R.id.sleep_text_view_sleep_time)).append(timeFromPickerDialog);
        }*/
    }

    @Override
    public void onComplete(String time) {
        String[] tc = time.split(":");
        ((TextView) findViewById(R.id.sleep_text_view_sleep_time)).append(time);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tc[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(tc[1]));
        calendar.set(Calendar.SECOND, 0);
        long timeInMillis = calendar.getTimeInMillis();
        Sleep sleep=new Sleep();
        sleep.setSleepTime(timeInMillis);
        objectArrayList.add(sleep);
    }
}
