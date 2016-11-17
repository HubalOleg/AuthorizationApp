package com.oleg.hubal.authorizationapp;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by User on 16.11.2016.
 */

public class AuthorizationAppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
