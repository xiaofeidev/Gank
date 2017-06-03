package com.github.xiaofei_dev.gank.ui.adapter;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.model.bean.GankSearchResultBean;
import com.github.xiaofei_dev.gank.util.DateUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/4/22.
 */

public final class SearchResultAdapter extends BaseQuickAdapter<GankSearchResultBean,BaseViewHolder> {

    public SearchResultAdapter(int layoutResId, @NonNull List<GankSearchResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankSearchResultBean item) {
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
        helper
                .setText(R.id.search_title,item.desc)
                .setText(R.id.info,stringBuilder.toString());
//              .setText(R.id.search_type,"tab : " + item.type)
//              .setText(R.id.search_person,item.who == null ?  "by : " + "佚名" : "by : " + item.who)
//              .setText(R.id.search_time, "at : " + DateUtils.dateFormat(item.publishedAt));
    }
}
