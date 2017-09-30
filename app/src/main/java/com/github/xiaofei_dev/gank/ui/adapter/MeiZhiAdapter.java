package com.github.xiaofei_dev.gank.ui.adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.model.bean.GankAPI;
import com.github.xiaofei_dev.gank.util.DensityUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17.
 */

public final class MeiZhiAdapter extends BaseQuickAdapter<GankAPI,BaseViewHolder> {

    @NonNull
    private final Fragment mContext;
    private final int screenWidth;

    public MeiZhiAdapter(@NonNull Fragment context, int layoutResId,
                         @NonNull List<GankAPI> data,
                         int width) {
        super(layoutResId, data);
        mContext = context;
        screenWidth = width;
    }

    public void clearData(){
        getData().clear();
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GankAPI item) {
        final int width = (screenWidth - DensityUtil.dp2px(mContext.getActivity(),30f))/2;
        final ImageView imageView = (ImageView)(helper.getConvertView().findViewById(R.id.meizhi));
        imageView.getLayoutParams().width = width;
        Glide.with(mContext)
                .load(item.url)
                .asBitmap()
                .fitCenter()
                .override(width,width*2)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        int height = (int) (((float)width)/bitmap.getWidth()*bitmap.getHeight());
                        imageView.getLayoutParams().height = height;
                        imageView.setImageBitmap(bitmap);
                    }
                });
    }
}
