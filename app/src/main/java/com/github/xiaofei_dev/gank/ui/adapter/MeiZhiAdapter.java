package com.github.xiaofei_dev.gank.ui.adapter;

import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.ui.customview.RatioImageView;
import com.github.xiaofei_dev.gank.model.bean.GankAPI;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17.
 */

public final class MeiZhiAdapter extends BaseQuickAdapter<GankAPI,BaseViewHolder> {

    @NonNull
    private final Fragment mContext;

    private final ColorFilter mColorFilter;

    public MeiZhiAdapter(@NonNull Fragment context, int layoutResId,
                         @NonNull List<GankAPI> data) {
        super(layoutResId, data);
        mContext = context;
        float[]array = new float[]{
                1,0,0,0,-70,
                0,1,0,0,-70,
                0,0,1,0,-70,
                0,0,0,1,0,
        };
        mColorFilter = new ColorMatrixColorFilter(new ColorMatrix(array));
    }

    public void clearData(){
        getData().clear();
    }


    @Override
    protected void convert(final BaseViewHolder helper, GankAPI item) {
        if(item.url!= null){
            Glide.with(mContext)
                    .load(item.url)
                    .asBitmap()
                    .error(R.drawable.error)
                    .fitCenter()
                    //.centerCrop()//??
                    .skipMemoryCache(true)
                    //.thumbnail( 0.1f )
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(R.drawable.ic_loading)
                    //.centerCrop()
                    //.into((ImageView) helper.getView(R.id.card_image));
                    .into((ImageView)helper.getConvertView().findViewById(R.id.meizhi))
                    .getSize(new SizeReadyCallback() {
                        @Override
                        public void onSizeReady(int width, int height) {
//                            if (!helper.getConvertView().isShown()) {
//                                helper.getConvertView().setVisibility(View.VISIBLE);
//                            }
                            ((RatioImageView) helper.getConvertView().findViewById(R.id.meizhi))
                                    .setColorFilter(mColorFilter);
                        }
                    });

        }else {
            Glide.with(mContext)
                    .load(R.drawable.ic_place)
                    .asBitmap()
                    .error(R.drawable.error)
                    .fitCenter()
                    //.centerCrop()//??
                    .skipMemoryCache(true)
                    //.thumbnail( 0.1f )
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    //.centerCrop()
                    //.into((ImageView) helper.getView(R.id.card_image));
                    .into((ImageView)helper.getConvertView().findViewById(R.id.card_image))
                    .getSize(new SizeReadyCallback() {
                        @Override
                        public void onSizeReady(int width, int height) {
                            /*if (!helper.getConvertView().isShown()) {
                                helper.getConvertView().setVisibility(View.VISIBLE);
                            }*/
                            ((RatioImageView) helper.getConvertView().findViewById(R.id.card_image))
                                    .setColorFilter(mColorFilter);
                        }
                    });
        }
    }
}
