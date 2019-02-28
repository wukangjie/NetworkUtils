package com.wukangjie.networkutils;

import android.app.Application;

import com.wukangjie.network.NetworkManager;

public class BaseApplication extends Application {
    public static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        NetworkManager.getInstance().init(this);

    }
}
