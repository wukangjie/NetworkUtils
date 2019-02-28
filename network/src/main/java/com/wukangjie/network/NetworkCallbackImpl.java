package com.wukangjie.network;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.util.Log;

import com.wukangjie.network.util.NetworkUtils;

import androidx.annotation.RequiresApi;

/**
 * @author wkjie
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {
    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        Log.d("NetworkCallbackImpl", "网络已连接");
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        Log.d("NetworkCallbackImpl", "网络已中断");
    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.d("NetworkCallbackImpl", "网络发生变更, 类型为：wifi");
            } else {
                Log.d("NetworkCallbackImpl", "网络发生变更, 类型为：其他");
            }
        }
    }
}
