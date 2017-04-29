package com.github.xiaofei_dev.gank.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.util.FileUtils;
import com.github.xiaofei_dev.gank.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/23.
 */

public final class SimpleMeiZhiActivity extends AppCompatActivity {
    private static final String TAG = "SimpleMeiZhiActivity";
    //private Drawable mDrawable;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.meizhi)
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_meizhi);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
//        Bitmap bitmap = (Bitmap)(getIntent().getBundleExtra("BITMAP").getParcelable("BITMAP"));
//        imageView.setImageBitmap(bitmap);
//        Drawable drawable = new BitmapDrawable(getResources(),bitmap);
        registerForContextMenu(imageView);
        String url = getIntent().getStringExtra("URL");//url作为局部变量
        //Bitmap bitmap = (Bitmap)(getIntent().getParcelableExtra("BITMAP"));
        //mDrawable = new BitmapDrawable(getResources(),bitmap);

        Glide.with(this)
                .load(url)
                .asBitmap()
                .error(R.drawable.error)
                .fitCenter()
                //跳过内存可节省内存但加载动画会不自然。。应该可通过占位图来解决
                .skipMemoryCache(true)
                //.dontAnimate()
                //.thumbnail( 0.2f )
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                //.placeholder(drawable)
                //.centerCrop()
                .into(imageView);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ToastUtils.showShort(R.string.explain);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        }
//                .getSize(new SizeReadyCallback() {
//                    @Override
//                    public void onSizeReady(int width, int height) {
//                        startPostponedEnterTransition();
//                    }
//                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == 1)
        {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
            {
                ToastUtils.showShort(R.string.denideHint);
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 0, Menu.NONE,getString(R.string.saveImage));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        switch (item.getItemId()) {
            case 0:
                FileUtils.saveImageToGallery(this, bitmap);
                break;
        }
        return super.onContextItemSelected(item);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


//    if (ContextCompat.checkSelfPermission(this,
//    Manifest.permission.WRITE_CALENDAR) != ) {
//        // Should we show an explanation?
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                Manifest.permission.READ_CONTACTS)) {
//
//        } else {
//
//            // No explanation needed, we can request the permission.
//
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.READ_CONTACTS},
//                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//        }
//    }

//    private SimpleTarget target = new SimpleTarget<Bitmap>() {
//        @Override
//        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
//            imageView.setImageBitmap(bitmap);
//            mBitmap = bitmap;
//        }
//    };
}


