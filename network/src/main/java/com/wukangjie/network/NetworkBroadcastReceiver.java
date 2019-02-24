package com.wukangjie.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wukangjie.network.constant.Constants;
import com.wukangjie.network.util.NetworkUtils;

/**
 * @author wkjie
 */
public class NetworkBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //ignoreCase 忽略大小写
        if (Constants.MONITOR_INTENT_FILETER_ACTION.equalsIgnoreCase(action)) {
            NetworkType networkType = NetworkUtils.getNetworkType();
            NetworkManager.getInstance().notifyAllObserver(networkType);
        }
    }
}
