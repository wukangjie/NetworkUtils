package com.wukangjie.network;

import android.app.Application;

public class BaseApplication extends Application {
    public static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        NetworkManager.getInstance().init(this);

    }
}
