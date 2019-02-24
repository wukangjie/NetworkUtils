package com.wukangjie.network.bean;

import com.wukangjie.network.NetworkType;

import java.lang.reflect.Method;

public class MethodManager {
    private Class<?> mNetworkType;
    private Method mMethod;
    private NetworkType target;

    public MethodManager(Class<?> networkType, Method method, NetworkType target) {
        mNetworkType = networkType;
        mMethod = method;
        this.target = target;
    }

    public Class<?> getNetworkType() {
        return mNetworkType;
    }

    public void setNetworkType(Class<?> networkType) {
        mNetworkType = networkType;
    }

    public Method getMethod() {
        return mMethod;
    }

    public void setMethod(Method method) {
        mMethod = method;
    }

    public NetworkType getTarget() {
        return target;
    }

    public void setTarget(NetworkType target) {
        this.target = target;
    }
}
