package com.lambdaschool.healthchaser.firebase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lambdaschool.healthchaser.MainActivity.Tracking;

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
