package com.lambdaschool.healthchaser.firebase;

import android.arch.lifecycle.MutableLiveData;

import com.lambdaschool.healthchaser.MainActivity.Tracking;
import com.lambdaschool.healthchaser.firebase.FirebaseDao;

import java.util.ArrayList;

class FirebaseRepository {

    MutableLiveData<ArrayList<String>> getTheGoods(String nodePath, Tracking trackingType) {

        final MutableLiveData<ArrayList<String>> mutableLiveDataList = new MutableLiveData<>();

        FirebaseDao.getAllEntriesForSpecifiedTrackingCategory(nodePath, trackingType, new FirebaseDao.TrackingCategoryCallback() {
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
