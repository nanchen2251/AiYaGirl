package com.nanchen.aiyagirl.net

import com.nanchen.aiyagirl.net.api.GankApi

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 网络操作类
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  11:21
 */

object NetWork {

    private var gankApi: GankApi? = null
    private val okHttpClient = OkHttpClient()

    fun getGankApi(): GankApi {
        if (gankApi == null) {
            val retrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://gank.io/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
            gankApi = retrofit.create(GankApi::class.java)
        }
        return gankApi!!
    }
}
