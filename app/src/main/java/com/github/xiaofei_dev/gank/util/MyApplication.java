package com.github.xiaofei_dev.gank.util;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDelegate;

import com.github.xiaofei_dev.gank.greenDAO.bean.DaoMaster;
import com.github.xiaofei_dev.gank.greenDAO.bean.DaoSession;
import com.github.xiaofei_dev.gank.ui.activity.MainActivity;

/**
 * Created by Administrator on 2017/3/20.
 */

public final class MyApplication extends Application {

    private static Context mContext;
    private static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        setupDatabase();
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
    private void setupDatabase(){
        //创建数据库collect.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"collect.db",null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }
    public static DaoSession getDaoInstant() {
        return daoSession;
    }

    public static Context getContext(){
        return mContext;
    }
}
