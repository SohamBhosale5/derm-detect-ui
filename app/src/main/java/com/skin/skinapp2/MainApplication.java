package com.skin.skinapp2;

import android.app.Application;

import com.skin.skinapp2.retrofit.ApiManager;

public class MainApplication extends Application {
    public static ApiManager apiManager;

    @Override
    public void onCreate() {
        super.onCreate();
        apiManager = ApiManager.getInstance();

    }
}
