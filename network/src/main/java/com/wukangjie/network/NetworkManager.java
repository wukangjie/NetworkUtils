package com.wukangjie.network;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;

import com.wukangjie.network.annotation.Network;
import com.wukangjie.network.bean.MethodManager;
import com.wukangjie.network.constant.Constants;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkManager {
    private Application mApplication;
    private NetworkBroadcastReceiver mNetworkBroadcastReceiver;

    private final Map<Object, List<MethodManager>> mManagerMap = new HashMap<>();


    private static final class NetworkManaferHolder {
        private static final NetworkManager INSTANCE = new NetworkManager();
    }

    private NetworkManager() {
        mNetworkBroadcastReceiver = new NetworkBroadcastReceiver();
    }

    public static final NetworkManager getInstance() {
        return NetworkManaferHolder.INSTANCE;
    }

    public void init(Application application) {
        mApplication = application;
        //第一种方式
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Constants.MONITOR_INTENT_FILETER_ACTION);
//        mNetworkBroadcastReceiver.setApplication(application);
//        application.registerReceiver(mNetworkBroadcastReceiver, intentFilter);

        //第二种方式监听，ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager.NetworkCallback networkCallback = new NetworkCallbackImpl();
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest networkRequest = builder.build();
            ConnectivityManager cmgr = (ConnectivityManager) mApplication.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cmgr != null) {
                cmgr.registerNetworkCallback(networkRequest, networkCallback);
            }
        } else {

        }

    }

//    public void setNetworkListener(NetworkBroadcastReceiver.NetworkListener networkListener) {
//        mNetworkBroadcastReceiver.setNetworkListener(networkListener);
//    }

    public void registerObserver(Object observer) {
        List<MethodManager> methodManagerList = mManagerMap.get(observer);
        if (methodManagerList != null) {
            return;
        }
        methodManagerList = getMethodManagerList(observer);
        mManagerMap.put(observer, methodManagerList);
    }

    private List<MethodManager> getMethodManagerList(Object observer) {
        List<MethodManager> methodManagerList = new ArrayList<>();
        Class<?> aClass = observer.getClass();
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            Network annotation = method.getAnnotation(Network.class);
            if (annotation == null) {
                continue;
            }
            Class<?> returnType = method.getReturnType();
            if (!returnType.getName().equals("void")) {
                continue;
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                continue;
            }
            MethodManager methodManager = new MethodManager(parameterTypes[0], method, annotation.target());
            methodManagerList.add(methodManager);
        }
        return methodManagerList;
    }

    public void unRegisterObserver(Object observer) {
        mManagerMap.remove(observer);
    }

    public void notifyAllObserver(NetworkType networkType) {
        for (Object object : mManagerMap.keySet()) {
            List<MethodManager> list = mManagerMap.get(object);
            for (MethodManager methodManager : list) {
                if (!methodManager.getNetworkType().getName().equals("com.wukangjie.network.NetworkType")) {
                    continue;
                }
                NetworkType target = methodManager.getTarget();
                Method method = methodManager.getMethod();
                try {
                    switch (target) {
                        case AUTO:
                            method.invoke(object, networkType);
                            break;
                        case WIFI:
                            if (NetworkType.WIFI == networkType || NetworkType.NO == networkType) {
                                method.invoke(object, networkType);
                            }
                            break;
                        default:
                            break;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void unRegisterAllObserver() {
        mManagerMap.clear();
    }
}
