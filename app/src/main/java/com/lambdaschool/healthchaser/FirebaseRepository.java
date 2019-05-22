package com.lambdaschool.healthchaser;

import android.arch.lifecycle.MutableLiveData;

import com.lambdaschool.healthchaser.MainActivity.Tracking;

import java.util.ArrayList;

class FirebaseRepository {

    MutableLiveData<ArrayList<String>> getTheGoods(String trackingNodeName, Tracking trackingType) {
        final MutableLiveData<ArrayList<String>> mutableLiveDataList = new MutableLiveData<>();

        //mutableLiveDataList.setValue(FirebaseDao.getAllEntriesForSpecifiedTrackingCategory(trackingNodeName,trackingType));

        FirebaseDao.getAllEntriesForSpecifiedTrackingCategory(trackingNodeName,trackingType,new FirebaseDao.TrackingCategoryCallback() {
            @Override
            public void onNodesObtained(final ArrayList<String> stringArrayList) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mutableLiveDataList.postValue(stringArrayList);
                    }
                }).start();

            }
        });

        return mutableLiveDataList;
    }
}
