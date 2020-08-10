package com.nanchen.aiyagirl.net.api

import com.nanchen.aiyagirl.model.BannerResult
import com.nanchen.aiyagirl.model.CategoryResult
import com.nanchen.aiyagirl.model.PageBean

import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 *
 * 代码家的gank.io接口
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  11:23
 */

interface GankApi {

    /**
     * 根据category获取Android、iOS等干货数据
     * @param category  类别
     * @param count     条目数目
     * @param page      页数
     */
    @GET("v2/data/category/All/type/{type}/page/{page}/count/{count}")
    fun getCategoryData(@Path("type") type: String, @Path("count") count: Int, @Path("page") page: Int): Observable<PageBean>

    @GET("v2/banners")
    fun getBanners(): Observable<BannerResult>
}
