package com.github.xiaofei_dev.gank.greenDAO.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import static android.R.attr.id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author：xiaofei_dev
 * time：2017/6/3:10:26
 * e-mail：xiaofei.dev@gmail.com
 * desc：coding
 */
@Entity
public final class Collect {
    //不能用int
    @Id(autoincrement = true)
    private Long id;
    //Url
    @Unique
    private String url;
    //title
    private String title;
    //desc
    private String desc;
    @Generated(hash = 717910471)
    public Collect(Long id, String url, String title, String desc) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.desc = desc;
    }
    @Generated(hash = 1726975718)
    public Collect() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }


}
