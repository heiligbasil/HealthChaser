package com.lambdaschool.healthchaser;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.lambdaschool.healthchaser.MainActivity;
import com.lambdaschool.healthchaser.healthpoints.Sleep;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static com.lambdaschool.healthchaser.MainActivity.*;

class FirebaseDao {

    public interface TrackingCategoryCallback {
        void onNodesObtained(ArrayList<String> stringArrayList);
    }

    static void getAllEntriesForSpecifiedTrackingCategory(String trackingNodeName, final Tracking trackingType, final TrackingCategoryCallback callback) {

        final ArrayList<String> stringArrayList = new ArrayList<>();

        String path = "users/" + currentLoggedInUser.getUserId() + "/" + trackingNodeName;
        DatabaseReference firebaseReference = FirebaseDatabase.getInstance().getReference(path);

//        Sleep sleep = new Sleep(1558496476412L, 1558497702951L, 0, 3);
//        firebaseReference.child(String.valueOf(System.currentTimeMillis())).setValue(sleep);

        firebaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                /*GenericTypeIndicator<ArrayList<Sleep>> ids = new GenericTypeIndicator<ArrayList<Sleep>>() {};
                ArrayList<Sleep> sleepArrayList=dataSnapshot.getValue(ids);*/
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

                callback.onNodesObtained(stringArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onNodesObtained(new ArrayList<String>());
            }
        });
    }
}
