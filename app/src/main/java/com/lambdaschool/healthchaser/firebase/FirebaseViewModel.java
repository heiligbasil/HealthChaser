package com.lambdaschool.healthchaser.firebase;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.lambdaschool.healthchaser.MainActivity.Tracking;
import com.lambdaschool.healthchaser.firebase.FirebaseRepository;

import java.util.ArrayList;

public class FirebaseViewModel extends ViewModel {

    private MutableLiveData<ArrayList<String>> mutableLiveDataList;
    private FirebaseRepository firebaseRepository;

    public LiveData<ArrayList<String>> getAllEntriesForSpecifiedTrackingCategory(String nodePath, Tracking trackingType) {

        if (mutableLiveDataList == null) {
            stuffTheGoods(nodePath, trackingType);
        }

        return mutableLiveDataList;
    }

    private void stuffTheGoods(String nodePath, Tracking trackingType) {
        firebaseRepository = new FirebaseRepository();
        mutableLiveDataList = firebaseRepository.getTheGoods(nodePath, trackingType);
    }

}
