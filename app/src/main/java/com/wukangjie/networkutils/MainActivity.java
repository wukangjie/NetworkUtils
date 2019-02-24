package com.wukangjie.networkutils;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.wukangjie.network.NetworkManager;
import com.wukangjie.network.NetworkType;
import com.wukangjie.network.annotation.Network;
import com.wukangjie.network.util.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkManager.getInstance().registerObserver(this);
    }

    @Network(target = NetworkType.AUTO)
    public void onNetwork(NetworkType networkType) {
        switch (networkType) {
            case WIFI:
                Toast.makeText(this, "onConnection", Toast.LENGTH_SHORT).show();
                break;
            case GPRS:
                Toast.makeText(this, "连接GPS", Toast.LENGTH_SHORT).show();
                break;
            case NO:
                Toast.makeText(this, "onDisconnection", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getInstance().unRegisterObserver(this);
    }
}
