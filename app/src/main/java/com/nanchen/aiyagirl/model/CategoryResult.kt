package com.nanchen.aiyagirl.model

/**
 * Category 返回model
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  10:17
 */

data class CategoryResult(

        var error: Boolean,
        var results: MutableList<ResultsBean> = mutableListOf()
)

data class ResultsBean(
        var _id: String,
        var createdAt: String,
        var desc: String,
        var publishedAt: String,
        var source: String,
        var type: String,
        var url: String,
        var used: Boolean,
        var who: String,
        var images: List<String> = emptyList()
)


data class BannerResult(
        var status: Int,
        var data: MutableList<Banner> = mutableListOf()
)
data class Banner(    var image: String,
                var title: String,
                var url: String
)
data class PicEntity(
        var url: String?,
        var width: Int = 1,
        var height: Int = 1
)

data class PageBean(
    val data: List<Data>,
    val page: Int,
    val page_count: Int,
    val status: Int,
    val total_counts: Int
)

data class Data(
    val _id: String,
    val author: String,
    val category: String,
    val createdAt: String,
    val desc: String,
    val images: List<String>,
    val likeCounts: Int,
    val publishedAt: String,
    val stars: Int,
    val title: String,
    val type: String,
    val url: String,
    val views: Int
)