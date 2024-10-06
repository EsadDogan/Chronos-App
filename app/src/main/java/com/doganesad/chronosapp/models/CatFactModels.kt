package com.doganesad.chronosapp.models

import com.google.gson.annotations.SerializedName



data class CatFact(
    @SerializedName("text") val text: String,
    @SerializedName("type") val type: String,
    @SerializedName("status") val status: Status
)

data class Status(
    @SerializedName("verified") val verified: Boolean?
)