package com.github.xiaofei_dev.gank.ui.adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.model.bean.GankAPI;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17.
 */

public final class MeiZhiAdapter extends BaseQuickAdapter<GankAPI,BaseViewHolder> {

    @NonNull
    private final Fragment mContext;
    private final int mWidth;

    public MeiZhiAdapter(@NonNull Fragment context, int layoutResId,
                         @NonNull List<GankAPI> data, int width) {
        super(layoutResId, data);
        mContext = context;
        mWidth = width;
    }

    public void clearData(){
        getData().clear();
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GankAPI item) {
        final ImageView imageView = (ImageView)(helper.getConvertView().findViewById(R.id.meizhi));
        imageView.getLayoutParams().width = mWidth;
        Glide.with(mContext)
                .load(item.url)
                .asBitmap()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .override(mWidth,mWidth)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        int height = (int) (((float)mWidth)/bitmap.getWidth()*bitmap.getHeight());
                        imageView.getLayoutParams().height = height;
                        imageView.setImageBitmap(bitmap);
                    }
                });
    }
}
