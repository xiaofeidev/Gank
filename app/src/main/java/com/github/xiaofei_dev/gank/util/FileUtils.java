package com.github.xiaofei_dev.gank.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.github.xiaofei_dev.gank.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/4/25.
 */

public final class FileUtils {

    /**
     * 保存图片
     */
    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        boolean save = false;
        File appDir = new File(Environment.getExternalStorageDirectory(), "Gank");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            save = bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            save = false;
            e.printStackTrace();
        } catch (IOException e) {
            save = false;
            e.printStackTrace();
        }

//        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    file.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + file.getPath())));// 最后通知图库更新
        //ToastUtils.showShort(R.string.saveImagesSuccess);
        if(save){
            ToastUtils.showShort(context.getString(R.string.saveImagesSuccess) + file.getPath());
        }else {
            ToastUtils.showShort(R.string.saveImagesFault);
        }
        //return save;
    }
//    public static void saveImage(Context context, Bitmap bitmap){
//        MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "", "");
//        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        Uri uri = Uri.fromFile(new File("/sdcard/image.jpg"));
//        intent.setData(uri);
//        context.sendBroadcast(intent);
//
//    }
}
