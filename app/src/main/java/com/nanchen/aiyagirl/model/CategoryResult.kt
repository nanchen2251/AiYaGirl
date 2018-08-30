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
        var results: List<ResultsBean> = ArrayList()
)

data class ResultsBean(
        var _id: String,
        var createdAt: String,
        var desc: String = "",
        var publishedAt: String,
        var source: String = "",
        var type: String,
        var url: String,
        var used: Boolean,
        var who: String = "",
        var images: List<String>?
)

