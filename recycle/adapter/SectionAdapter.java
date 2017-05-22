package com.github.xiaofei_dev.gank.ui.adapter;

import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.ui.custom_view.RatioImageView;
import com.github.xiaofei_dev.gank.model.bean.GankAPI;
import com.github.xiaofei_dev.gank.model.bean.MySection;

import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */

public final class SectionAdapter extends BaseSectionQuickAdapter<MySection,BaseViewHolder>  {

    @NonNull
    private final Fragment mContext;

    private final ColorFilter mColorFilter;


    public SectionAdapter(int layoutResId, int sectionHeadResId, List data,
                          @NonNull Fragment context) {
        super(layoutResId, sectionHeadResId, data);
        mContext = context;
        float[]array = new float[]{
                1,0,0,0,-70,
                0,1,0,0,-70,
                0,0,1,0,-70,
                0,0,0,1,0,
        };
        mColorFilter = new ColorMatrixColorFilter(new ColorMatrix(array));
    }

    public void setData(List<MySection> data){
        mData = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, MySection mySection) {

        GankAPI gankAPI = mySection.t;
        if(gankAPI.getImages()!= null){
            Glide.with(mContext)
                    .load(gankAPI.getImages().get(0))
                    .asBitmap()//一律作为普通静态图
                    .fitCenter()
                    .skipMemoryCache(true)
                    .thumbnail( 0.1f )
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    //.centerCrop()
                    .into((ImageView) helper.getView(R.id.card_image))
                    .getSize(new SizeReadyCallback() {
                        @Override
                        public void onSizeReady(int width, int height) {
                            if (!helper.getConvertView().isShown()) {
                                helper.getConvertView().setVisibility(View.VISIBLE);
                            }
                            ((RatioImageView) helper.getConvertView().findViewById(R.id.card_image))
                                    .setColorFilter(mColorFilter);
                        }
                    });
        }else {
            Glide.with(mContext)
                    .load(R.mipmap.ic_launcher)
                    .asBitmap()
                    .fitCenter()
                    .skipMemoryCache(true)
                    //.thumbnail( 0.1f )
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    //.centerCrop()
                    //.into((ImageView) helper.getView(R.id.card_image));
                    .into((ImageView)helper.getConvertView().findViewById(R.id.card_image));
        }
        helper.setText(R.id.title,gankAPI.getDesc())
              .setText(R.id.person,gankAPI.getWho())
              .setText(R.id.date,gankAPI.getPublishedAt());

    }

    @Override
    protected void convertHead(BaseViewHolder helper, final MySection mySection) {
        helper.setText(R.id.item_title,mySection.header);

    }

}












