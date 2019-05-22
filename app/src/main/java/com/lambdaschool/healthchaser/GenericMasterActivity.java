package com.lambdaschool.healthchaser;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lambdaschool.healthchaser.healthpoints.Sleep;

import java.util.ArrayList;

public class GenericMasterActivity extends AppCompatActivity {

    private static final String TAG = "GenericMasterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_master);


        ViewFlipper viewFlipper = findViewById(R.id.view_flipper);
        viewFlipper.setDisplayedChild(0);


/*        String displayName = currentUser.getDisplayName();
        String email = currentUser.getEmail();
        Uri photoUrl = currentUser.getPhotoUrl();
        String uid = currentUser.getUid();*/


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/" + "sUkjbGqEqTQgljCKI66qfPEzJ2P2" + "/sleep");
        //DatabaseReference myRef2 = myRef.child(uid);
        Sleep sleep = new Sleep(1558496486412L, 1558497802951L, -2, -3);
        myRef.child(String.valueOf(System.currentTimeMillis())).setValue(sleep);


/*                final DatabaseReference dinosaursRef = database.getReference("dinosaurs");
                dinosaursRef.orderByChild("sleepTime").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                        Sleep sleep = dataSnapshot.getValue(Dinosaur.class);
                        System.out.println(dataSnapshot.getKey() + " was " + dinosaur.height + " meters tall.");
                    }
                });*/

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String key = dataSnapshot.getKey();
                String value = dataSnapshot.getValue().toString();
                ArrayList<Sleep> sleepArrayList = new ArrayList<>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                long childrenCount = dataSnapshot.getChildrenCount();
                for (DataSnapshot ds : children) {
                    String key1 = ds.getKey();
                    Object value1 = ds.getValue();
                    Sleep sleep1 = ds.getValue(Sleep.class);
                    sleepArrayList.add(sleep1);
                }

                Log.d(TAG, "Key is: " + key);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read value.", error.toException());
            }
        });


        DatabaseReference myRef3 = database.getReference("message");
        //myRef.setValue(uid);


    }
}
