package com.lambdaschool.healthchaser;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lambdaschool.healthchaser.MainActivity;
import com.lambdaschool.healthchaser.healthpoints.Sleep;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static com.lambdaschool.healthchaser.MainActivity.*;

class FirebaseDao {

    static ArrayList<String> getAllEntriesForSpecifiedTrackingCategory(String trackingNodeName, final Tracking trackingType) {

        //final CountDownLatch countDownLatch=new CountDownLatch(1);
        final ArrayList<String> stringArrayList = new ArrayList<>();

        String path = "users/" + currentLoggedInUser.getUserId() + "/" + trackingNodeName;
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
                //countDownLatch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("DAO", "Failed to read value.", error.toException());
            }
        });

/*        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        return stringArrayList;
    }
}
