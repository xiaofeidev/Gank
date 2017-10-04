package com.github.xiaofei_dev.gank.model.convert

import android.graphics.Bitmap
import android.support.v4.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.github.xiaofei_dev.gank.model.bean.GankAPI
import java.util.*

/**
 * Created by Administrator on 2017/10/4.
 */
fun GankAPI2GankMeizhiAPI(mContext:Fragment,gankAPI: GankAPI?):GankMeizhiAPI{
    var isDone = false
    val gankMeizhiAPI = GankMeizhiAPI()
    gankMeizhiAPI.id = gankAPI?.id
    gankMeizhiAPI.createdAt = gankAPI?.createdAt
    gankMeizhiAPI.desc = gankAPI?.desc
    gankMeizhiAPI.publishedAt = gankAPI?.publishedAt
    gankMeizhiAPI.source = gankAPI?.source
    gankMeizhiAPI.type = gankAPI?.type
    gankMeizhiAPI.url = gankAPI?.url
    Glide.with(mContext)
            .load(gankAPI?.url)
            .asBitmap()
            .fitCenter()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.RESULT)
            .into(object :SimpleTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                    gankMeizhiAPI.width = resource?.width
                    gankMeizhiAPI.height = resource?.height
                    isDone = true
                }
            })

    while(true){
        if (isDone){
            return gankMeizhiAPI
        }
    }
}

fun GankAPIList2GankMeizhiAPIList(mContext:Fragment,gankAPIList: ArrayList<GankAPI>):ArrayList<GankMeizhiAPI>{

    return gankAPIList.map { GankAPI2GankMeizhiAPI(mContext,it) } as ArrayList<GankMeizhiAPI>

}