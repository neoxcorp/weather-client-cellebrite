package com.cellebrite.weatherclient.application;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}