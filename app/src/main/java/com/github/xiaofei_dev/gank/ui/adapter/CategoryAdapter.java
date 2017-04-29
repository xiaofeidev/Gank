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
import com.github.xiaofei_dev.gank.util.DateUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */

public final class CategoryAdapter extends BaseQuickAdapter<GankAPI,BaseViewHolder> {

    @NonNull
    private final Fragment mContext;

    private final ColorFilter mColorFilter;

    public CategoryAdapter(@NonNull Fragment context, int layoutResId,
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

   /* public void setData(List<GankAPI> data){
        mData = data;
    }*/


    @Override
    protected void convert(final BaseViewHolder helper, GankAPI item) {

        StringBuilder stringBuilder = new StringBuilder(40);
        stringBuilder
                .append("by : ")
                .append(item.who == null ? "佚名" : item.who)
                .append("    ")
                .append("at : ")
                .append(DateUtils.dateFormat(item.publishedAt));
        helper.setText(R.id.title,item.desc)
                .setText(R.id.person,stringBuilder.toString());
                //.setText(R.id.person,item.getWho() == null ? "by : " + "佚名" : "by : " + item.getWho());
                //.setText(R.id.date, "at : " + DateUtils.dateFormat(item.getPublishedAt()));
        if(item.images!= null){
            Glide.with(mContext)
                    .load(item.images.get(0))
                    .asBitmap()
                    .error(R.drawable.error)
                    .fitCenter()
                    .skipMemoryCache(true)
                    .thumbnail( 0.1f )
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    //.centerCrop()
                    //.into((ImageView) helper.getView(R.id.card_image));
                    .into((ImageView)helper.getConvertView().findViewById(R.id.card_image))
                    .getSize(new SizeReadyCallback() {
                        @Override
                        public void onSizeReady(int width, int height) {
                           /* if (!helper.getConvertView().isShown()) {
                                helper.getConvertView().setVisibility(View.VISIBLE);
                            }*/
                            ((RatioImageView) helper.getConvertView().findViewById(R.id.card_image))
                                    .setColorFilter(mColorFilter);
                        }
                    });
        }else {
            Glide.with(mContext)
                    .load(R.drawable.ic_place)
                    .asBitmap()
                    .error(R.drawable.error)
                    //.fitCenter()
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
