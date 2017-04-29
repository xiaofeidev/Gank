package com.github.xiaofei_dev.gank.util;

import android.widget.Toast;


/**
 * Created by Administrator on 2017/4/24.
 */

public final class ToastUtils {

    public static void showShort(int stringId) {
        Toast.makeText(MyApplication.getContext(), stringId, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(int stringId) {
        Toast.makeText(MyApplication.getContext(), stringId, Toast.LENGTH_LONG).show();
    }

    public static void showShort(String toast) {
        Toast.makeText(MyApplication.getContext(), toast, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String toast) {
        Toast.makeText(MyApplication.getContext(), toast, Toast.LENGTH_LONG).show();
    }
}
