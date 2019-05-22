package com.lambdaschool.healthchaser;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.lambdaschool.healthchaser.MainActivity.Tracking;

import java.util.ArrayList;

public class FirebaseViewModel extends ViewModel {

    private MutableLiveData<ArrayList<String>> mutableLiveDataList;
    private FirebaseRepository firebaseRepository;

    public LiveData<ArrayList<String>> getAllEntriesForSpecifiedTrackingCategory(String trackingNodeName, Tracking trackingType) {

        if (mutableLiveDataList == null) {
            stuffTheGoods(trackingNodeName, trackingType);
        }

        return mutableLiveDataList;
    }

    private void stuffTheGoods(String trackingNodeName, Tracking trackingType) {
        firebaseRepository = new FirebaseRepository();
        mutableLiveDataList = firebaseRepository.getTheGoods(trackingNodeName, trackingType);
    }

}
