package com.lambdaschool.healthchaser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lambdaschool.healthchaser.healthpoints.Sleep;

import java.util.ArrayList;

import static com.lambdaschool.healthchaser.MainActivity.Tracking;
import static com.lambdaschool.healthchaser.MainActivity.currentLoggedInUser;

public class GenericMasterActivity extends AppCompatActivity {

    private static final String TAG = "GenericMasterActivity";
    ArrayList<Object> objectArrayList = new ArrayList<>();
    private Tracking trackingType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_master);

        Intent intent = getIntent();
        trackingType = (Tracking) intent.getSerializableExtra("tracking");

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

        String path = "users/" + currentLoggedInUser.getUserId() + "/" + trackingNodeName;
        DatabaseReference firebaseReference = FirebaseDatabase.getInstance().getReference(path);

//        Sleep sleep = new Sleep(1558496476412L, 1558497702951L, 0, 3);
//        firebaseReference.child(String.valueOf(System.currentTimeMillis())).setValue(sleep);

        firebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> childrenNodes = dataSnapshot.getChildren();
                for (DataSnapshot ds : childrenNodes) {
                    switch (trackingType) {
                        case SLEEP:
                            Sleep sleep = ds.getValue(Sleep.class);
                            objectArrayList.add(sleep);
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
        });


        ViewFlipper viewFlipper = findViewById(R.id.view_flipper);
        viewFlipper.setDisplayedChild(viewFlipperDisplayChild);

    }
}
