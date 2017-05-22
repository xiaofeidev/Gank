package com.github.xiaofei_dev.gank.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.view.View;

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
    public static void saveImageToGallery(final Context context, View view,Bitmap bmp) {
        // 首先保存图片
        boolean save = false;
        File appDir = new File(Environment.getExternalStorageDirectory(), "Gank");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        final File file = new File(appDir, fileName);
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
        //ToastUtils.showShort(R.string.save_image_success);
        if(save){
            //ToastUtils.showShort(context.getString(R.string.save_image_success) + file.getPath());
            Snackbar.make(view, R.string.save_image_success,Snackbar.LENGTH_LONG)
                    .setAction(R.string.open, new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            Intent intentImage = new Intent(Intent.ACTION_VIEW);
                            intentImage.addCategory(Intent.CATEGORY_DEFAULT);
                            intentImage.setDataAndType(Uri.fromFile(file), "image/*");
                            context.startActivity(intentImage);
                        }
                    })
                    .show();
        }else {
//            ToastUtils.showShort(R.string.save_image_fault);
            Snackbar.make(view, R.string.save_image_fault,Snackbar.LENGTH_SHORT)
                    .show();
        }
        //return save;
    }
//    public static void save_image(Context context, Bitmap bitmap){
//        MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "",a "");
//        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        Uri uri = Uri.fromFile(new File("/sdcard/image.jpg"));
//        intent.setData(uri);
//        context.sendBroadcast(intent);
//
//    }
}
