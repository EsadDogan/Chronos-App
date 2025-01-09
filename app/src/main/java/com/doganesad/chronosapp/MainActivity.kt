package com.doganesad.chronosapp

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.doganesad.chronosapp.api.RetrofitInstanceNews
import com.doganesad.chronosapp.api.RetrofitInstanceWeather
import com.doganesad.chronosapp.api.RetrofitInstancesCatFacts
import com.doganesad.chronosapp.api.RetrofitInstancesDogFacts
import com.doganesad.chronosapp.models.CatFact
import com.doganesad.chronosapp.models.CurrentWeather
import com.doganesad.chronosapp.models.DogFact
import com.doganesad.chronosapp.models.NewsArticle
import com.doganesad.chronosapp.models.NewsResponse
import com.doganesad.chronosapp.models.RainVolume
import com.doganesad.chronosapp.models.WeatherCondition
import com.doganesad.chronosapp.models.WeatherResponse
import com.doganesad.chronosapp.ui.theme.AppTheme
import com.doganesad.chronosapp.utils.Screens
import com.doganesad.chronosapp.viewmodels.MainViewModel
import com.doganesad.chronosapp.views.HomeTabScreen
import com.doganesad.chronosapp.views.LoginScreenMain
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.delay

import kotlinx.coroutines.launch
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class MainActivity : ComponentActivity() {
    val TAG = "MainActivity"

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]


        installSplashScreen().apply {

//            setOnExitAnimationListener { screen ->
//                val zoomX = ObjectAnimator.ofFloat(
//                    screen.view,
//                    View.SCALE_X,
//                    1f,
//                    0f
//                )
//
//                zoomX.duration = 200L
//                zoomX.doOnEnd { screen.remove() }
//
//                val zoomY = ObjectAnimator.ofFloat(
//                    screen.view,
//                    View.SCALE_Y,
//                    1f,
//                    0f
//                )
//
//                zoomY.duration = 200L
//                zoomY.doOnEnd { screen.remove() }
//
//                zoomX.start()
//                zoomY.start()
//            }

            setOnExitAnimationListener { screen ->
                // Circular reveal animation
                val cx = screen.view.width / 2
                val cy = screen.view.height / 2



                val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
                val circularReveal =
                    ViewAnimationUtils.createCircularReveal(screen.view, cx, cy, finalRadius, 0f)

                circularReveal.duration = 300L
                circularReveal.doOnEnd { screen.remove() }

                circularReveal.start()
            }


        }
        setContent {
            AppTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }


    }


    @Composable
    fun MyApp(modifier: Modifier = Modifier) {

        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()

//        systemUiController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//
//        // Hide the navigation bar
//        systemUiController.isNavigationBarVisible = false
//        systemUiController.isStatusBarVisible = false

        systemUiController.setStatusBarColor(
            color = MaterialTheme.colorScheme.surface,
            darkIcons = useDarkIcons
        )

        systemUiController.setNavigationBarColor(
            color = MaterialTheme.colorScheme.primaryContainer,
            darkIcons = useDarkIcons
        )

        val navController = rememberNavController()
        mainViewModel.navController = navController

        // Observe authentication state
        val isUserAuthenticated = Firebase.auth.currentUser != null

        // GOOGLE SIGN IN NOT FUNCTIONAL AT THİS MOMENT

//        // Firebase Authentication Check
//        LaunchedEffect(Unit) {
//            val currentUser = Firebase.auth.currentUser
//            isUserAuthenticated.value = currentUser != null
//
//            // Navigate based on authentication status
//            if (isUserAuthenticated.value) {
//                navController.navigate(Screens.HOME_TABS_PAGE) {
//                    popUpTo(0) // Clear backstack
//                }
//            } else {
//                navController.navigate(Screens.LOGIN_PAGE) {
//                    popUpTo(0) // Clear backstack
//                }
//            }
//        }

        NavHost(
            navController = navController,
            startDestination = if (isUserAuthenticated) Screens.HOME_TABS_PAGE else Screens.LOGIN_PAGE,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            },
        ) {
            composable(Screens.HOME_TABS_PAGE) {
                HomeTabScreen(mainViewModel = mainViewModel)
            }
            composable(Screens.LOGIN_PAGE) {
                LoginScreenMain(mainViewModel = mainViewModel)
            }
        }
    }



}