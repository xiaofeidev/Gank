package com.github.xiaofei_dev.gank.model.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by Administrator on 2017/4/6.
 */

public final class MySection extends SectionEntity<GankAPI> {

    public MySection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public MySection(GankAPI gankAPI) {
        super(gankAPI);
    }
}
