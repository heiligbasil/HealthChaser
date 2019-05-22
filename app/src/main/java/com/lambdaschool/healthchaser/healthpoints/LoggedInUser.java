package com.lambdaschool.healthchaser.healthpoints;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

public class LoggedInUser {

    private static String userId, displayName, email;
    private static Uri photoUrl;

    public LoggedInUser(FirebaseUser firebaseUser) {
        userId = firebaseUser.getUid();
        displayName = firebaseUser.getDisplayName();
        email = firebaseUser.getEmail();
        photoUrl = firebaseUser.getPhotoUrl();
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    @Override
    public String toString() {
        return "LoggedInUser{" +
                "userId='" + userId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", photoUrl=" + photoUrl +
                '}';
    }
}
