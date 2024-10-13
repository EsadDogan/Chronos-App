package com.doganesad.chronosapp.models

// Data class for response
data class CuratedPhotosResponse(
    val total_results: Int,
    val photos: List<Photo>
)

data class Photo(
    val id: Int,
    val photographer: String,
    val src: Src
)

data class Src(
    val original: String,
    val medium: String,
    val small: String,
    val large: String,
    val large2x: String

)