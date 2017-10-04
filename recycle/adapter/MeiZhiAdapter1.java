package com.github.xiaofei_dev.gank.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.model.convert.GankMeizhiAPI;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17.
 */

public final class MeiZhiAdapter1 extends BaseQuickAdapter<GankMeizhiAPI,BaseViewHolder> {

    @NonNull
    private final Fragment mContext;

    public MeiZhiAdapter1(@NonNull Fragment context, int layoutResId,
                          @NonNull List<GankMeizhiAPI> data) {
        super(layoutResId, data);
        mContext = context;
    }

    public void clearData(){
        getData().clear();
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GankMeizhiAPI item) {
        final ImageView imageView = (ImageView)(helper.getConvertView().findViewById(R.id.meizhi));
        imageView.getLayoutParams().width = item.width;
        imageView.getLayoutParams().height = item.height;
        Glide.with(mContext)
                .load(item.url)
                .asBitmap()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .override(mWidth,mWidth)
                .into(imageView);
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
//                        int height = (int) (((float)mWidth)/bitmap.getWidth()*bitmap.getHeight());
//                        imageView.getLayoutParams().height = height;
//                        imageView.setImageBitmap(bitmap);
//                    }
//                });
    }
}
