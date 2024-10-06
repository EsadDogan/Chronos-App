package com.doganesad.chronosapp.api


import com.doganesad.chronosapp.models.CatFact
import com.doganesad.chronosapp.models.DogFactResponse
import com.doganesad.chronosapp.models.NewsResponse
import com.doganesad.chronosapp.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {


//    @GET("auth/register")
//    suspend fun registerUser(
//        @Body userInfo: UserInfo
//    ) : WeatherResponse


    // GET GENERAL WEATHER DATA FOR CURRENT LOCATION
    @GET("onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric"
    ): WeatherResponse


    // GET DAILY DOG FACTS
    @GET("facts")
    suspend fun getDogFacts(@Query("limit") limit: Int): DogFactResponse // Update to accept limit

    @GET("facts/random")
    suspend fun getCatFacts(@Query("amount") limit: Int): List<CatFact>

    @GET("news")
    suspend fun getNews(
        @Query("limit") limit: Int,
        @Query("languages") languages: String
    ): NewsResponse


}