package com.github.xiaofei_dev.gank.ui.adapter;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.xiaofei_dev.gank.R;
import com.github.xiaofei_dev.gank.greenDAO.bean.Collect;

import java.util.List;

/**
 * author：xiaofei_dev
 * time：2017/6/3:14:33
 * e-mail：xiaofei.dev@gmail.com
 * desc：coding
 */
public final class CollectionAdapter extends BaseItemDraggableAdapter<Collect,BaseViewHolder> {
    public CollectionAdapter(int layoutResId, @NonNull List<Collect> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Collect item) {
        helper
                .setText(R.id.search_title,item.getTitle())
                .setText(R.id.info,item.getDesc());
    }
}
