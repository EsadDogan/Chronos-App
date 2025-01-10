package com.doganesad.chronosapp.api

import com.doganesad.chronosapp.BuildConfig
import com.matlubapps.videoai.api.RetryInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstanceWeather {

    private const val baseUrlWeather = "https://api.openweathermap.org/data/3.0/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(RetryInterceptor(maxRetry = 7))
        .addInterceptor(Interceptor { chain ->
            val originalRequest = chain.request()
            val urlWithApiKey = originalRequest.url.newBuilder()
                .addQueryParameter("appid", BuildConfig.API_KEY_WEATHER_NEW)
                .build()
            val requestWithHeaders = originalRequest.newBuilder()
                .url(urlWithApiKey)
                .build()
            chain.proceed(requestWithHeaders)
        })
        .build()

    val apiWeather: ApiServices by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrlWeather)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServices::class.java)
    }
}

object RetrofitInstancePexels {

    private const val baseUrlPexels = "https://api.pexels.com/v1/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(RetryInterceptor(maxRetry = 7))
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val requestWithHeaders = originalRequest.newBuilder()
                .addHeader("Authorization", BuildConfig.API_KEY_PEXELS)
                .build()
            chain.proceed(requestWithHeaders)
        }
        .build()

    val apiPexels: ApiServices by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrlPexels)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServices::class.java)
    }
}



object RetrofitInstanceNews {

    private const val baseUrlNews= "https://api.mediastack.com/v1/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(RetryInterceptor(maxRetry = 7))
        .addInterceptor(Interceptor { chain ->
            val originalRequest = chain.request()
            val urlWithApiKey = originalRequest.url.newBuilder()
                .addQueryParameter("access_key", BuildConfig.API_KEY_NEWS_NEW)
                .build()
            val requestWithHeaders = originalRequest.newBuilder()
                .url(urlWithApiKey)
                .build()
            chain.proceed(requestWithHeaders)
        })
        .build()

    val apiNews: ApiServices by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrlNews)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServices::class.java)
    }
}

object RetrofitInstancesDogFacts {

    private const val baseUrlDogFacts = "https://dogapi.dog/api/v2/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(RetryInterceptor(maxRetry = 7))
        .build()

    val apiDog: ApiServices by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrlDogFacts)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServices::class.java)
    }
}

object RetrofitInstancesCatFacts {

    private const val baseUrlCatFacts = "https://cat-fact.herokuapp.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(RetryInterceptor(maxRetry = 7))
        .build()

    val apiCat: ApiServices by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrlCatFacts)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServices::class.java)
    }
}
