package com.github.xiaofei_dev.gank.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.model.bean.GankAPI;
import com.github.xiaofei_dev.gank.util.DateUtils;

import java.util.List;

/**
 * Created by xiaofei on 2017/4/10.
 */

public final class CategoryAdapter extends BaseQuickAdapter<GankAPI,BaseViewHolder> {

    @NonNull
    private final Fragment mContext;

    public CategoryAdapter(@NonNull Fragment context, int layoutResId,
                           @NonNull List<GankAPI> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, GankAPI item) {
        StringBuilder stringBuilder = new StringBuilder(40);
        stringBuilder
                .append("tag : ")
                .append(item.type)
                .append("    ")
                .append("by : ")
                .append(item.who == null ? "佚名" : item.who)
                .append("    ")
                .append("at : ")
                .append(DateUtils.dateFormat(item.publishedAt));
        helper.setText(R.id.title,item.desc)
                .setText(R.id.person,stringBuilder.toString());
        if(item.images!= null && !item.images.isEmpty()){
            Glide.with(mContext)
                    .load(item.images.get(0))
                    .asBitmap()
                    .error(R.drawable.error)
                    .fitCenter()
                    .skipMemoryCache(true)
                    .thumbnail( 0.1f )
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    //.centerCrop()
                    .into((ImageView)helper.getConvertView().findViewById(R.id.card_image));
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
                    .into((ImageView)helper.getConvertView().findViewById(R.id.card_image));
        }

    }
}
