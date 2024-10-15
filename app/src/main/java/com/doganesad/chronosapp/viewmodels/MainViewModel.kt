package com.doganesad.chronosapp.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.doganesad.chronosapp.BuildConfig
import com.doganesad.chronosapp.api.RetrofitInstanceNews
import com.doganesad.chronosapp.api.RetrofitInstancePexels
import com.doganesad.chronosapp.api.RetrofitInstanceWeather
import com.doganesad.chronosapp.api.RetrofitInstancesCatFacts
import com.doganesad.chronosapp.api.RetrofitInstancesDogFacts
import com.doganesad.chronosapp.models.CatFact
import com.doganesad.chronosapp.models.CurrentWeather
import com.doganesad.chronosapp.models.DogFact
import com.doganesad.chronosapp.models.NewsArticle
import com.doganesad.chronosapp.models.Photo
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

    val searchBarText = mutableStateOf("")


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

    val emptyWeatherResponse = WeatherResponse(
        lat = 0.0,
        lon = 0.0,
        timezone = "",
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

    val weatherResponse = mutableStateOf(emptyWeatherResponse)
    val catFacts = mutableStateOf(emptyList<CatFact>())
    val dogFacts = mutableStateOf(emptyList<DogFact>())
    val news = mutableStateOf(emptyList<NewsArticle>())
    val curratedPhotos = mutableStateOf(emptyList<Photo>())



    init {

        Log.d(TAG, "main: init called ")
        viewModelScope.launch {
            Log.d(TAG, "main: init called 2")

            getCatFacts()
            getDogFacts()
            getCuratedPhotos()
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

        val newsResponse = mutableListOf<NewsArticle>()

        try {
            db.collection("news")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        newsResponse.add(document.toObject(NewsArticle::class.java))
                    }
                    Log.d(TAG, "checkNewsDataUptoDate: $newsResponse")
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }.addOnCompleteListener {

                    if (newsResponse.isNotEmpty()) {
                        val publishedAt = newsResponse.first().publishedAt
                        val isOneDayLater = if(BuildConfig.DEBUG) false else isOneDayLater(publishedAt)

                        println("Is one day later: $isOneDayLater")

                        if (isOneDayLater ) {
                            viewModelScope.launch(Dispatchers.IO) {
                                getNewsAndUploadToDB(db)
                            }
                        }else{
                            news.value = newsResponse
                        }


                    }

                }
        }catch (e:Exception){
            Log.d(TAG, "checkNewsDataUptoDate: exception: ${e.message}")
        }



    }

    suspend fun searchForImage() {
        try {

            val response = RetrofitInstancePexels.apiPexels.getSearchPhotos(
                page = 1,
                perPage = 30,
                query = searchBarText.value
            )

            curratedPhotos.value = response.photos
            Log.d(TAG, "getCuratedPhotos: searchedPhotosSize" + curratedPhotos.value.size)

            Log.d(TAG, "getCuratedPhotos: $response")
        } catch (e: Exception) {
            Log.d(TAG, "getCuratedPhotos: error " + e.message)
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

            news.value = response.data


            Log.d(TAG, "getNews: $response")
        } catch (e: Exception) {
            Log.d(TAG, "getNews: error" + e.message)
        }


    }


    private suspend fun getDogFacts() {
        try {
            val response = RetrofitInstancesDogFacts.apiDog.getDogFacts(5)

            // Create a new mutable list from the current state
            val updatedList = dogFacts.value.toMutableList()

            // Add new facts to the list
            response.data.forEach {
                updatedList.add(it)
            }

            // Assign the updated list back to dogFacts.value
            dogFacts.value = updatedList

            Log.d(TAG, "getDogFacts: ${response}")
            Log.d(TAG, "getDogFacts: ${dogFacts.value}")

        } catch (e: Exception) {
            Log.d(TAG, "getDogFacts: error" + e.message)
        }
    }


    private suspend fun getCatFacts() {

        try {
            val response = RetrofitInstancesCatFacts.apiCat.getCatFacts(100)

            val updatedList = catFacts.value.toMutableList()

            response.forEach {
                if (it.status.verified == true) {
                    updatedList.add(it)
                }
            }

            catFacts.value = updatedList

            Log.d(TAG, "getCatFacts: ${catFacts.value} ")
            Log.d(TAG, "getCatFacts: ${catFacts.value.size} ")
        } catch (e: Exception) {
            Log.d(TAG, "getCatFacts: error: ${e.message}")
        }
    }


    private suspend fun getWeather() {


        try {
            val response = if (BuildConfig.DEBUG) {
               weatherResponse.value = dummyWeatherResponse
            } else {

                weatherResponse.value = RetrofitInstanceWeather.apiWeather.getWeatherData(
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


    suspend fun getCuratedPhotos() {
        try {

            val response = RetrofitInstancePexels.apiPexels.getCuratedPhotos(
                    page = 1,
                    perPage = 30,
                )

           curratedPhotos.value = response.photos
            Log.d(TAG, "getCuratedPhotos: curratedPhotosSize" + curratedPhotos.value.size)

            Log.d(TAG, "getCuratedPhotos: $response")
        } catch (e: Exception) {
            Log.d(TAG, "getCuratedPhotos: error " + e.message)
        }
    }



}