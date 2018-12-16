package com.github.xiaofei_dev.gank.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.ui.view.RefreshView;
import com.github.xiaofei_dev.gank.util.FileUtils;
import com.github.xiaofei_dev.gank.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/23.
 */

public final class SimpleMeiZhiActivity extends AppCompatActivity implements RefreshView {
    private static final String TAG = "SimpleMeiZhiActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
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
        //图片还未加载完成时显示正在加载
        showRefresh();
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefresh.setRefreshing(false);
            }
        });

//        registerForContextMenu(imageView);
        String url = getIntent().getStringExtra("URL");
        Glide.with(this)
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        //图片加载完成
                        hideRefresh();
                        return false;
                    }//这个用于监听图片是否加载完成
                })
                .error(R.drawable.error)
                //.asBitmap()
                .fitCenter()
                //跳过内存可节省内存但加载动画会不自然。。应该可通过占位图来解决
                .skipMemoryCache(true)
                //.dontAnimate()
                //.thumbnail( 0.2f )
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                //.placeholder(drawable)
                //.centerCrop()
                .into(imageView);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == 1)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                saveImage();
            }else {
                ToastUtils.showShort(R.string.deny_hint);
            }
        }
    }

    @Override
    public void showRefresh() {
        mRefresh.setRefreshing(true);
    }

    @Override
    public void hideRefresh() {
        mRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mRefresh != null){
                    mRefresh.setRefreshing(false);
                }
            }
        },500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_simple_meizhi, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.save:
                saveImage();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //保存妹纸图
    private void saveImage(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ToastUtils.showShort(R.string.explain);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        }else {
            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache(true);
            Bitmap bitmap =
                    Bitmap.createBitmap(imageView.getDrawingCache(true),
                            0, 0, imageView.getMeasuredWidth(), imageView.getMeasuredHeight());
            FileUtils.saveImageToGallery(this, imageView ,bitmap);
            imageView.setDrawingCacheEnabled(false);
            imageView.destroyDrawingCache();
        }
    }
}


