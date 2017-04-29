package com.github.xiaofei_dev.gank.util;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDelegate;

import com.github.xiaofei_dev.gank.ui.activity.MainActivity;

/**
 * Created by Administrator on 2017/3/20.
 */

public final class MyApplication extends Application {

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
        mContext = getApplicationContext();
        if (PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(MainActivity.KEY_NIGHT_MODE, false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }

    public static Context getContext(){
        return mContext;
    }
}
