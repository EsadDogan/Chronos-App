package com.doganesad.chronosapp.models

import com.google.gson.annotations.SerializedName

data class DogFactResponse(
    @SerializedName("data") val data: List<DogFact>
)

data class DogFact(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("attributes") val attributes: DogFactAttributes
)

data class DogFactAttributes(
    @SerializedName("body") val body: String
)