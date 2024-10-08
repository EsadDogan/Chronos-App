package com.doganesad.chronosapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.doganesad.chronosapp.BuildConfig
import com.doganesad.chronosapp.api.RetrofitInstanceNews
import com.doganesad.chronosapp.api.RetrofitInstanceWeather
import com.doganesad.chronosapp.api.RetrofitInstancesCatFacts
import com.doganesad.chronosapp.api.RetrofitInstancesDogFacts
import com.doganesad.chronosapp.models.CatFact
import com.doganesad.chronosapp.models.CurrentWeather
import com.doganesad.chronosapp.models.DogFact
import com.doganesad.chronosapp.models.NewsArticle
import com.doganesad.chronosapp.models.RainVolume
import com.doganesad.chronosapp.models.WeatherCondition
import com.doganesad.chronosapp.models.WeatherResponse
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class MainViewModel() : ViewModel() {

    val TAG = "MainVM"

    lateinit var navController: NavHostController


    private val db = Firebase.firestore

    val dummyWeatherResponse = WeatherResponse(
        lat = 52.237,
        lon = 21.0175,
        timezone = "Europe/Warsaw",
        current = CurrentWeather(
            temp = 14.21,
            feelsLike = 13.96,
            humidity = 87,
            clouds = 100,
            visibility = 10000,
            windSpeed = 7.6,
            weather = listOf(
                WeatherCondition(
                    id = 501,
                    main = "Rain",
                    description = "moderate rain",
                    icon = "10n"
                )
            ),
            rain = RainVolume(oneHour = 1.78)
        )
    )

    init {
        viewModelScope.launch {

            Log.d("API_KEY_WEATHER", BuildConfig.API_KEY_WEATHER_NEW)
            Log.d("API_KEY_NEWS", BuildConfig.API_KEY_NEWS_NEW)
            getCatFacts()
            getDogFacts()
            //Call weather from activity when location ready
            getWeather()
            checkNewsDataUptoDate(db)
        }
    }



    private fun isOneDayLater(publishedAt: String): Boolean {
        // Define the formatter for the input date string
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")

        // Parse the publishedAt string to ZonedDateTime
        val publishedDateTime = ZonedDateTime.parse(publishedAt, formatter)

        // Get the current time in the same zone (UTC in this case)
        val currentTime = ZonedDateTime.now(ZoneId.of("UTC"))

        // Calculate the difference in time
        val duration = Duration.between(publishedDateTime, currentTime)

        // Check if the difference is 24 hours or more
        return duration.toHours() >= 24
    }


    private fun checkNewsDataUptoDate(db: FirebaseFirestore) {

        val news = mutableListOf<NewsArticle>()

        try {
            db.collection("news")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        news.add(document.toObject(NewsArticle::class.java))
                    }
                    Log.d(TAG, "checkNewsDataUptoDate: $news")
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }.addOnCompleteListener {

                    if (news.isNotEmpty()) {
                        val publishedAt = news.first().publishedAt
                        val isOneDayLater = isOneDayLater(publishedAt)

                        println("Is one day later: $isOneDayLater")

                        if (isOneDayLater) {
                            viewModelScope.launch(Dispatchers.IO) {
                                getNewsAndUploadToDB(db)
                            }
                        }


                    }

                }
        }catch (e:Exception){
            Log.d(TAG, "checkNewsDataUptoDate: exception: ${e.message}")
        }



    }


    private suspend fun getNewsAndUploadToDB(db: FirebaseFirestore) {


        try {
            val response = RetrofitInstanceNews.apiNews.getNews(
                languages = "en",
                limit = 100
            )

            response.data.forEach {
                db.collection("news")
                    .add(it)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document: " + e.message, e)
                    }
            }


            Log.d(TAG, "getNews: $response")
        } catch (e: Exception) {
            Log.d(TAG, "getNews: error" + e.message)
        }


    }


    private suspend fun getDogFacts() {
        val dogFacts = mutableListOf<DogFact>()


        try {
            val response = RetrofitInstancesDogFacts.apiDog.getDogFacts(5)
            response.data.forEach {
                dogFacts.add(it)
            }
            Log.d(TAG, "getDogFacts: $dogFacts ")

        } catch (e: Exception) {
            Log.d(TAG, "getDogFacts: error" + e.message)
        }
    }


    private suspend fun getCatFacts() {
        val catFacts = mutableListOf<CatFact>()

        try {
            val response = RetrofitInstancesCatFacts.apiCat.getCatFacts(100)
            response.forEach {
                if (it.status.verified == true) {
                    catFacts.add(it)
                }
            }
            Log.d(TAG, "getCatFacts: $catFacts ")
            Log.d(TAG, "getCatFacts: ${catFacts.size} ")
        } catch (e: Exception) {
            Log.d(TAG, "getCatFacts: error: ${e.message}")
        }
    }


    private suspend fun getWeather() {


        try {
            val response = if (BuildConfig.DEBUG) {
                dummyWeatherResponse
            } else {
                RetrofitInstanceWeather.apiWeather.getWeatherData(
                    lat = 52.237049,
                    lon = 21.017532,
                    units = "metric"
                )
            }


            Log.d(TAG, "getWeather: $response")
        } catch (e: Exception) {
            Log.d(TAG, "getWeather: error" + e.message)
        }


    }


}