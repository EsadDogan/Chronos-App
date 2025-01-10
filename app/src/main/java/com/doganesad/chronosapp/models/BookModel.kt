package com.doganesad.chronosapp.models

import com.google.gson.annotations.SerializedName


data class BookModel(
    @SerializedName("bookName") val bookName: String = "",
    @SerializedName("bookImageUrl") val bookImageUrl: String = "",
    @SerializedName("bookPdfUrl") val bookPdfUrl: String = "",
    @SerializedName("bookAuthor") val bookAuthor: String = ""
)
