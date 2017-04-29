package com.github.xiaofei_dev.gank.model;

import com.github.xiaofei_dev.gank.model.bean.GankAPI;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/20.
 */

public final class GankRandom {


    /**
     * error : false
     * results : [{"_id":"56cc6d1d421aa95caa70765b","createdAt":"2015-05-18T17:11:02.636Z","desc":"博客园客户端","publishedAt":"2015-05-20T03:51:09.190Z","type":"Android","url":"http://git.oschina.net/wlemuel/Cotable","used":true,"who":"有时放纵"},{"_id":"56e2947b67765979a6ee79e9","createdAt":"2016-03-11T17:48:43.16Z","desc":"RecyclerView源码分析","publishedAt":"2016-03-15T11:45:57.350Z","source":"web","type":"Android","url":"http://blog.csdn.net/qq_23012315/article/details/50807224","used":true,"who":"曾志刚"},{"_id":"582a6e78421aa9140a8ec98c","createdAt":"2016-11-15T10:10:00.749Z","desc":"AS2.2使用CMake方式进行JNI/NDK开发","publishedAt":"2016-11-15T11:26:11.821Z","source":"web","type":"Android","url":"http://blog.csdn.net/yulianlin/article/details/53168350","used":true,"who":"FMVP"},{"_id":"56cc6d29421aa95caa7082b6","createdAt":"2016-02-16T03:19:57.547Z","desc":"一系列有用的android网络工具","publishedAt":"2016-02-16T04:46:45.166Z","type":"Android","url":"https://github.com/stealthcopter/AndroidNetworkTools","used":true,"who":"MVP"},{"_id":"56cc6d29421aa95caa70812e","createdAt":"2016-01-09T04:43:47.839Z","desc":"很不错的一个插件！自带很多脚本 Mac OS X Menu Bar\n","publishedAt":"2016-01-13T04:49:14.812Z","type":"Android","url":"https://github.com/matryer/bitbar#installing-plugins","used":true,"who":"andyiac"},{"_id":"56cc6d23421aa95caa707c19","createdAt":"2015-07-18T00:07:49.171Z","desc":"方便地在App中集成天气","publishedAt":"2015-07-22T03:59:20.646Z","type":"Android","url":"https://github.com/pwittchen/WeatherIconView","used":true,"who":"mthli"},{"_id":"56cc6d23421aa95caa707abc","createdAt":"2015-06-10T04:45:12.156Z","desc":"为Android提供不可改变的数据集支持","publishedAt":"2015-06-11T03:30:39.976Z","type":"Android","url":"https://github.com/konmik/solid","used":true,"who":"mthli"},{"_id":"572a1a4167765974fca830f3","createdAt":"2016-05-04T23:50:25.761Z","desc":"PagerBottomTabStrip 是一个基本按谷歌Material Design规范完成的安卓底部导航栏控件","publishedAt":"2016-06-15T11:55:46.992Z","source":"chrome","type":"Android","url":"https://github.com/tyzlmjj/PagerBottomTabStrip","used":true,"who":"Jason"},{"_id":"573bfb4b6776591ca681f8b1","createdAt":"2016-05-18T13:19:07.170Z","desc":"教你写一个炫酷的Material Design 风格的登录和注册页面","publishedAt":"2016-05-20T10:05:09.959Z","source":"web","type":"Android","url":"http://tikitoo.github.io/2016/05/17/beautiful-android-login-and-signup-screens-with-material-design-zh/","used":true,"who":"Tikitoo"},{"_id":"58f80a04421aa9544b774030","createdAt":"2017-04-20T09:08:20.177Z","desc":"A well-designed local image selector for Android ","images":["http://img.gank.io/d5212e26-53d9-45e6-9ff1-9174ec32d494"],"publishedAt":"2017-04-20T14:03:06.490Z","source":"web","type":"Android","url":"https://github.com/zhihu/Matisse","used":true,"who":"liuzheng"},{"_id":"56f91eb667765933d9b0a97d","createdAt":"2016-03-28T20:08:22.706Z","desc":"对LruCache的源码进行了详尽的分析。","publishedAt":"2016-03-30T15:17:02.228Z","source":"web","type":"Android","url":"http://blog.csdn.net/luoyanglizi/article/details/50994469","used":true,"who":"lypeer"},{"_id":"56cc6d1d421aa95caa707832","createdAt":"2015-09-02T04:22:45.344Z","desc":"另一种toast","publishedAt":"2015-09-14T03:55:03.700Z","type":"Android","url":"https://github.com/pyricau/frenchtoast","used":true,"who":"Jason"},{"_id":"56cc6d1d421aa95caa707646","createdAt":"2015-06-29T03:18:55.167Z","desc":"通过传感器使视图表现出视差效果的类库","publishedAt":"2015-06-30T11:20:10.939Z","type":"Android","url":"https://github.com/nvanbenschoten/motion","used":true,"who":"Jason"},{"_id":"56cc6d1d421aa95caa707676","createdAt":"2015-06-01T16:24:43.485Z","desc":"ButterKnife 生成器，使用简单方便","publishedAt":"2015-06-02T03:44:53.460Z","type":"Android","url":"https://github.com/avast/android-butterknife-zelezny","used":true,"who":"大城小黄"},{"_id":"5895d845421aa970b845238c","createdAt":"2017-02-04T21:33:57.847Z","desc":" TabLayout 和 CoordinatorLayout 相结合的折叠控件","publishedAt":"2017-02-06T11:36:12.36Z","source":"web","type":"Android","url":"https://github.com/hugeterry/CoordinatorTabLayout","used":true,"who":null},{"_id":"56cc6d22421aa95caa7078ff","createdAt":"2015-10-07T13:03:02.124Z","desc":"android菜单控制器view","publishedAt":"2015-10-08T04:29:48.0Z","type":"Android","url":"https://github.com/brucetoo/PinterestView","used":true,"who":"Jason"},{"_id":"56cc6d1d421aa95caa707647","createdAt":"2015-06-29T16:35:11.307Z","desc":"一款支持多选的图片选择器","publishedAt":"2015-06-30T11:20:11.64Z","type":"Android","url":"https://git.oschina.net/xiao-lifan/MutiPhotoChoser","used":true,"who":"有时放纵"},{"_id":"579187f5421aa90d39e7091d","createdAt":"2016-07-22T10:41:57.457Z","desc":"继承自 UIVisualEffectsView 的支持动态调整模糊度的库","publishedAt":"2016-07-22T11:04:44.305Z","source":"web","type":"Android","url":"https://github.com/ML-Works/Bluuur","used":true,"who":"代码家"},{"_id":"5865b721421aa94dc1ac0ad7","createdAt":"2016-12-30T09:23:45.38Z","desc":"可能是目前最简单、灵活的路由框架。","publishedAt":"2016-12-30T16:16:11.125Z","source":"web","type":"Android","url":"https://github.com/chenenyu/Router","used":true,"who":"cey"},{"_id":"56cc6d29421aa95caa7081ac","createdAt":"2016-01-19T03:01:12.114Z","desc":"RxVolley，支持RxJava，OKhttp，内置了一个RxBus，移除了httpclient相关API","publishedAt":"2016-01-19T04:06:04.449Z","type":"Android","url":"https://github.com/kymjs/RxVolley","used":true,"who":"MVP"}]
     */

    private boolean error;
    @SerializedName("results") private ArrayList<GankAPI> dataList;

    public boolean isError() {
        return error;
    }

    public ArrayList<GankAPI> getDataList() {
        return dataList;
    }
}
