package com.doganesad.chronosapp.models

import com.google.gson.annotations.SerializedName

data class NewsArticle(
    @SerializedName("author") val author: String? = null,
    @SerializedName("title") val title: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("url") val url: String? = null,
    @SerializedName("source") val source: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("category") val category: String = "",
    @SerializedName("language") val language: String? = null,
    @SerializedName("country") val country: String? = null,
    @SerializedName("published_at") val publishedAt: String = ""
)


data class NewsResponse(
    @SerializedName("pagination") val pagination: Pagination,
    @SerializedName("data") val data: List<NewsArticle>
)

data class Pagination(
    @SerializedName("limit") val limit: Int,
    @SerializedName("offset") val offset: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("total") val total: Int
)