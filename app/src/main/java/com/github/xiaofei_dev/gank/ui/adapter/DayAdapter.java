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
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.model.bean.GankDayBean;
import com.github.xiaofei_dev.gank.model.bean.GankAPI;
import com.github.xiaofei_dev.gank.ui.customview.RatioImageView;
import com.github.xiaofei_dev.gank.util.DateUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public final class DayAdapter extends BaseMultiItemQuickAdapter<GankDayBean,BaseViewHolder> {

    @NonNull
    private final Fragment mContext;

    private final ColorFilter mColorFilter;

    public DayAdapter(@NonNull Fragment context, @NonNull List data) {
        super(data);
        addItemType(GankDayBean.DATE, R.layout.item_gank_date);
        addItemType(GankDayBean.CATEGORY,R.layout.item_gank_category);
        addItemType(GankDayBean.ITEM,R.layout.item_gank_content);
        mContext = context;
        float[]array = new float[]{
                1,0,0,0,-70,
                0,1,0,0,-70,
                0,0,1,0,-70,
                0,0,0,1,0,
        };
        mColorFilter = new ColorMatrixColorFilter(new ColorMatrix(array));
    }

    @Override
    protected void convert(final BaseViewHolder helper, GankDayBean item) {
        switch (helper.getItemViewType()){
            case GankDayBean.DATE:
                helper.setText(R.id.item_date, DateUtils.dateFormat(item.content));
                break;
            case GankDayBean.CATEGORY:
                helper.setText(R.id.item_title,item.content);
                break;
            case GankDayBean.ITEM:
                GankAPI gankAPI = item.mGankAPI;
                if(gankAPI.images!= null){
                    Glide.with(mContext)
                            .load(gankAPI.images.get(0))
                            .asBitmap()//一律作为普通静态图
                            .error(R.drawable.error)
                            .fitCenter()
                            .skipMemoryCache(true)
                            .thumbnail( 0.1f )
                            .diskCacheStrategy(DiskCacheStrategy.RESULT)
                            //.centerCrop()
                            .into((ImageView) helper.getView(R.id.card_image))
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
                StringBuilder stringBuilder = new StringBuilder(40);
                stringBuilder
                        .append("by : ")
                        .append(gankAPI.who == null ? "佚名" : gankAPI.who)
                        .append("    ")
                        .append("at : ")
                        .append(DateUtils.dateFormat(gankAPI.publishedAt));
                helper.setText(R.id.title,gankAPI.desc)
                        .setText(R.id.person,stringBuilder.toString());
//                        .setText(R.id.person,gankAPI.getWho() == null ? "by : " + "佚名" : "by : " + gankAPI.getWho()
//                                + "    " + DateUtils.dateFormat(gankAPI.getPublishedAt()));
//                        .setText(R.id.date,"at : " + DateUtils.dateFormat(gankAPI.getPublishedAt()));
                break;
            default:
                break;
        }

    }

}
