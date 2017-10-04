package com.github.xiaofei_dev.gank.model.convert;

import java.util.List;

/**
 * Created by Administrator on 2017/10/4.
 */

public class GankMeizhiAPI {
    public String id;
    public String createdAt;
    public String desc;
    public String publishedAt;
    public String source;
    public String type;
    public String url;
    //新加字段，用于表示妹子图的高宽
    public Integer width;
    public Integer height;
    ///////////////////////////////
    public boolean used;
    public String who;
    public List<String> images;
}
