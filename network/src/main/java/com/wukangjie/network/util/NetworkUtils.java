package com.wukangjie.network.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import com.wukangjie.network.BaseApplication;
import com.wukangjie.network.NetworkType;

public class NetworkUtils {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) BaseApplication
                .application.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network[] allNetworks = cm.getAllNetworks();
        for (Network network : allNetworks) {
            NetworkInfo networkInfo = cm.getNetworkInfo(network);
            if (networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static NetworkType getNetworkType() {
        NetworkType networkType;
        ConnectivityManager cm = (ConnectivityManager) BaseApplication
                .application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return NetworkType.NO;
        }
        switch (activeNetworkInfo.getType()) {
            case ConnectivityManager.TYPE_WIFI:
                networkType = NetworkType.WIFI;
                break;
            case ConnectivityManager.TYPE_MOBILE:
                networkType = NetworkType.GPRS;
                break;
            default:
                networkType = NetworkType.NO;
                break;
        }
        return networkType;
    }
}
