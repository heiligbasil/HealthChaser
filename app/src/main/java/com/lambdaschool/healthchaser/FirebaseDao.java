package com.lambdaschool.healthchaser;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lambdaschool.healthchaser.healthpoints.*;

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
                            Meals meal = ds.getValue(Meals.class);
                            stringArrayList.add(meal.toString());
                            break;
                        case MOOD:
                            Mood mood = ds.getValue(Mood.class);
                            stringArrayList.add(mood.toString());
                            break;
                        case WATER:
                            Water water = ds.getValue(Water.class);
                            stringArrayList.add(water.toString());
                            break;
                        case EXERCISE:
                            Exercise exercise = ds.getValue(Exercise.class);
                            stringArrayList.add(exercise.toString());
                            break;
                        case RESTROOM:
                            Restroom restroom = ds.getValue(Restroom.class);
                            stringArrayList.add(restroom.toString());
                            break;
                        case HYGIENE:
                            Hygiene hygiene = ds.getValue(Hygiene.class);
                            stringArrayList.add(hygiene.toString());
                            break;
                        case MEDITATION:
                            Meditation meditation = ds.getValue(Meditation.class);
                            stringArrayList.add(meditation.toString());
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
