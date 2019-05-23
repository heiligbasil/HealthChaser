package com.lambdaschool.healthchaser;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lambdaschool.healthchaser.healthpoints.Sleep;

import java.util.ArrayList;

import static com.lambdaschool.healthchaser.MainActivity.Tracking;

class FirebaseDao {

    public interface TrackingCategoryCallback {
        void onNodesObtained(ArrayList<String> stringArrayList);
    }

    static void getAllEntriesForSpecifiedTrackingCategory(String nodePath, final Tracking trackingType, final TrackingCategoryCallback callback) {

        final ArrayList<String> stringArrayList = new ArrayList<>();

        DatabaseReference firebaseReference = FirebaseDatabase.getInstance().getReference(nodePath);

        firebaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                /*GenericTypeIndicator<List<Sleep>> ids = new GenericTypeIndicator<List<Sleep>>() {};
                List<Sleep> sleepArrayList=dataSnapshot.getValue(ids);*/
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
