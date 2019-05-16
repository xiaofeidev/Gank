package com.github.xiaofei_dev.gank.model;

import com.github.xiaofei_dev.gank.model.bean.GankAPI;
import com.github.xiaofei_dev.gank.model.bean.GankDayBean;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public final class GankDay {


    /**
     * category : ["休息视频","iOS","福利","前端","Android"]
     * error : false
     * results : {"Android":[{"_id":"58dc6b6e421aa969fd8a3ded","createdAt":"2017-03-30T10:20:30.597Z","desc":"Android 按钮进度条效果","images":["http://img.gank.io/f95959ff-5e9a-4d32-a7d6-09b4c2997376"],"publishedAt":"2017-03-30T11:46:55.192Z","source":"chrome","type":"Android","url":"https://github.com/ishaan1995/ButtonProgressBar","used":true,"who":"Allen"},{"_id":"58dc7e5b421aa969fb0fbede","createdAt":"2017-03-30T11:41:15.386Z","desc":"Android 动画管理库，辅助你管理动画效果。","images":["http://img.gank.io/703b7fe0-dda6-45d9-8e73-89ad824b969c"],"publishedAt":"2017-03-30T11:46:55.192Z","source":"chrome","type":"Android","url":"https://github.com/willowtreeapps/spruce-android","used":true,"who":"带马甲"}],"iOS":[{"_id":"58dbc186421aa969f75cee03","createdAt":"2017-03-29T22:15:34.750Z","desc":"Let's play Pac-Man.","images":["http://img.gank.io/0cf25e74-308d-4379-9d9b-e7f63c9cd3ba"],"publishedAt":"2017-03-30T11:46:55.192Z","source":"web","type":"iOS","url":"https://github.com/atuooo/PacmanPageControl","used":true,"who":"oOatuo"},{"_id":"58dc6a00421aa969fd8a3deb","createdAt":"2017-03-30T10:14:24.710Z","desc":"iOS Material Design 风格的组件库","images":["http://img.gank.io/a9781165-f6fd-4245-bee3-73fdeea6c5bf"],"publishedAt":"2017-03-30T11:46:55.192Z","source":"chrome","type":"iOS","url":"https://github.com/material-components/material-components-ios","used":true,"who":"代码家"},{"_id":"58dc6a34421aa969fd8a3dec","createdAt":"2017-03-30T10:15:16.537Z","desc":"iOS Material Design 风格的动画库，做的好细腻，我给满分。","images":["http://img.gank.io/627220ba-4e59-4c7e-849a-4b897a094588"],"publishedAt":"2017-03-30T11:46:55.192Z","source":"chrome","type":"iOS","url":"https://github.com/material-motion/material-motion-swift","used":true,"who":"代码家"},{"_id":"58dc6ab1421aa969f75cee08","createdAt":"2017-03-30T10:17:21.958Z","desc":"Apple TV 图像视差效果高清重置","images":["http://img.gank.io/bfd6293c-51c7-4514-a139-61d1e5729fb7"],"publishedAt":"2017-03-30T11:46:55.192Z","source":"chrome","type":"iOS","url":"https://github.com/asynchrony/Re-Lax","used":true,"who":"malaboom"}],"休息视频":[{"_id":"58d0a884421aa90f033451ae","createdAt":"2017-03-21T12:13:56.54Z","desc":"史上最厉害的台球玩家，全程跪着看完","publishedAt":"2017-03-30T11:46:55.192Z","source":"chrome","type":"休息视频","url":"http://www.miaopai.com/show/1prlWR4mDKZNP~mfatfEuw__.htm","used":true,"who":"lxxself"}],"前端":[{"_id":"58dc6b4e421aa969f75cee09","createdAt":"2017-03-30T10:19:58.544Z","desc":"Vue - ECharts","publishedAt":"2017-03-30T11:46:55.192Z","source":"chrome","type":"前端","url":"https://justineo.github.io/vue-echarts/demo/","used":true,"who":"daimajia "}],"福利":[{"_id":"58dc5645421aa969fd8a3dea","createdAt":"2017-03-30T08:50:13.178Z","desc":"3-30","publishedAt":"2017-03-30T11:46:55.192Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-03-30-17265582_1877445642507654_3057988544061505536_n.jpg","used":true,"who":"dmj"}]}
     */

    @Expose
    private boolean error;
    @Expose
    private Results results;
    @Expose
    private List<String> category;

    public final static class Results {
        @Expose
        @SerializedName("Android") public List<GankAPI> androidList;
        @Expose
        @SerializedName("iOS") public List<GankAPI> iOSList;
        @Expose
        @SerializedName("App") public List<GankAPI> AppList;
        @Expose
        @SerializedName("前端") public List<GankAPI> webList;
        @Expose
        @SerializedName("拓展资源") public List<GankAPI> expandList;
        @Expose
        @SerializedName("瞎推荐") public List<GankAPI> xiatuijianList;
        @Expose
        @SerializedName("休息视频") public List<GankAPI> videoList;
        @Expose
        @SerializedName("福利") public List<GankAPI> girlList;

        @Override
        public String toString() {
            return "Results{" +
                    "androidList=" + androidList +
                    ", iOSList=" + iOSList +
                    ", AppList=" + AppList +
                    ", webList=" + webList +
                    ", expandList=" + expandList +
                    ", xiatuijianList=" + xiatuijianList +
                    ", videoList=" + videoList +
                    ", girlList=" + girlList +
                    '}';
        }
    }

    public List<String> getCategory() {
        return category;
    }

//    public static ArrayList<MySection> getSctionData(Results gankDayData){
//
//        ArrayList<MySection> list = new ArrayList<>();
//
//        if(gankDayData.getAndroidList() != null){
//            list.add(new MySection(true,"Android"));
//            for(GankAPI gankAPI:gankDayData.getAndroidList()){
//                list.add(new MySection(gankAPI));
//            }
//        }
//        if(gankDayData.getiOSList() != null){
//            list.add(new MySection(true,"iOS"));
//            for(GankAPI gankAPI:gankDayData.getiOSList()){
//                list.add(new MySection(gankAPI));
//            }
//        }
//        if(gankDayData.getAppList() != null){
//            list.add(new MySection(true,"App"));
//            for(GankAPI gankAPI:gankDayData.getAppList()){
//                list.add(new MySection(gankAPI));
//            }
//        }
//        if(gankDayData.getWebList() != null){
//            list.add(new MySection(true,"前端"));
//            for(GankAPI gankAPI:gankDayData.getWebList()){
//                list.add(new MySection(gankAPI));
//            }
//        }
//        if(gankDayData.getExpandList() != null){
//            list.add(new MySection(true,"扩展资源"));
//            for(GankAPI gankAPI:gankDayData.getExpandList()){
//                list.add(new MySection(gankAPI));
//            }
//        }
//        if(gankDayData.getXiatuijianList() != null){
//            list.add(new MySection(true,"瞎推荐"));
//            for(GankAPI gankAPI:gankDayData.getXiatuijianList()){
//                list.add(new MySection(gankAPI));
//            }
//        }
//        return list;
//    }

    public ArrayList<GankDayBean> getMultiGankData(){
        ArrayList<GankDayBean> list = new ArrayList<>();
        if(results == null){
            return null;
        }

        if(results.androidList != null){
            String date = results.androidList.get(0).publishedAt;
            list.add(new GankDayBean(GankDayBean.DATE,date));
        }else if (results.iOSList != null){
            String date = results.iOSList.get(0).publishedAt;
            list.add(new GankDayBean(GankDayBean.DATE,date));
        }else if (results.AppList != null){
            String date = results.AppList.get(0).publishedAt;
            list.add(new GankDayBean(GankDayBean.DATE,date));
        }else if (results.webList != null){
            String date = results.webList.get(0).publishedAt;
            list.add(new GankDayBean(GankDayBean.DATE,date));
        }else if (results.expandList != null){
            String date = results.expandList.get(0).publishedAt;
            list.add(new GankDayBean(GankDayBean.DATE,date));
        }else if (results.xiatuijianList != null){
            String date = results.xiatuijianList.get(0).publishedAt;
            list.add(new GankDayBean(GankDayBean.DATE,date));
        }


        if(category.contains("Android")){
            list.add(new GankDayBean(GankDayBean.CATEGORY,"Android"));
            for (GankAPI gankAPI:results.androidList){
                list.add(new GankDayBean(GankDayBean.ITEM,gankAPI));
            }
        }
        if(category.contains("iOS")){
            list.add(new GankDayBean(GankDayBean.CATEGORY,"iOS"));
            for (GankAPI gankAPI:results.iOSList){
                list.add(new GankDayBean(GankDayBean.ITEM,gankAPI));
            }
        }
        if(category.contains("App")){
            list.add(new GankDayBean(GankDayBean.CATEGORY,"App"));
            for (GankAPI gankAPI:results.AppList){
                list.add(new GankDayBean(GankDayBean.ITEM,gankAPI));
            }
        }
        if(category.contains("前端")){
            list.add(new GankDayBean(GankDayBean.CATEGORY,"前端"));
            for (GankAPI gankAPI:results.webList){
                list.add(new GankDayBean(GankDayBean.ITEM,gankAPI));
            }
        }
        if(category.contains("拓展资源") || category.contains("扩展资源")){
            list.add(new GankDayBean(GankDayBean.CATEGORY,"扩展资源"));
            for (GankAPI gankAPI:results.expandList){
                list.add(new GankDayBean(GankDayBean.ITEM,gankAPI));
            }
        }
        if(category.contains("瞎推荐")){
            list.add(new GankDayBean(GankDayBean.CATEGORY,"瞎推荐"));
            for (GankAPI gankAPI:results.xiatuijianList){
                list.add(new GankDayBean(GankDayBean.ITEM,gankAPI));
            }
        }
        return list;

    }

    public boolean isError() {
        return error;
    }

    public Results getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "GankDay{" +
                "results=" + results +
                '}'+ "Categoty:"+category;
    }
}
