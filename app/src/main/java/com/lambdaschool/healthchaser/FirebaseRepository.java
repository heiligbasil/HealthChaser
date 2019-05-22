package com.lambdaschool.healthchaser;

import android.arch.lifecycle.MutableLiveData;

import com.lambdaschool.healthchaser.MainActivity.Tracking;

import java.util.ArrayList;

class FirebaseRepository {

    MutableLiveData<ArrayList<String>> getTheGoods(String trackingNodeName, Tracking trackingType) {
        MutableLiveData<ArrayList<String>> mutableLiveDataList = new MutableLiveData<>();

        mutableLiveDataList.setValue(FirebaseDao.getAllEntriesForSpecifiedTrackingCategory(trackingNodeName,trackingType));

        return mutableLiveDataList;
    }

}
