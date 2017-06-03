package com.github.xiaofei_dev.gank.greenDAO;

import com.github.xiaofei_dev.gank.greenDAO.bean.Collect;
import com.github.xiaofei_dev.gank.greenDAO.bean.CollectDao;
import com.github.xiaofei_dev.gank.util.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * author：xiaofei_dev
 * time：2017/6/3:10:49
 * e-mail：xiaofei.dev@gmail.com
 * desc：coding
 */
public final class VisitDao {
    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param collect
     */
    public static void insertCollections(Collect collect){
        MyApplication.getDaoInstant().getCollectDao().insertOrReplace(collect);
    }
    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteCollectionByKey(long id){
        MyApplication.getDaoInstant().getCollectDao().deleteByKey(id);
    }

    /**
     * 删除数据
     *
     * @param collect
     */
    public static void deleteCollection(Collect collect){
        MyApplication.getDaoInstant().getCollectDao().delete(collect);
    }
    /**
     * 查询全部数据
     */
    public static List<Collect> queryAll(){
        return MyApplication.getDaoInstant().getCollectDao().loadAll();
    }

    /**
     * 根据 url 查询数据
     */
    public static List<Collect>query(String url){
        return MyApplication.getDaoInstant().getCollectDao().queryBuilder().where(CollectDao.Properties.Url.eq(url)).list();
    }
}



















