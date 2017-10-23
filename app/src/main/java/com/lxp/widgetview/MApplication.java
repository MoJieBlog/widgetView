package com.lxp.widgetview;

import android.app.Application;

import com.lxp.utils.BiuldUtils;
import com.lxp.utils.LogUtils;

/**
 * Created by Li Xiaopeng on 17/10/23.
 */

public class MApplication extends Application {
    private static final String TAG = "MApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        boolean apkDebugable = BiuldUtils.isApkDebugable(this);
        LogUtils.logE(TAG,apkDebugable+"");
    }
}
