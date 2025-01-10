package com.doganesad.chronosapp.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.doganesad.chronosapp.R
import com.doganesad.chronosapp.composables.CustomHtmlWebView
import com.doganesad.chronosapp.composables.DogCatFactsPagerItem
import com.doganesad.chronosapp.composables.NewsArticleCard
import com.doganesad.chronosapp.viewmodels.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun HomeScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel) {

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()


    systemUiController.setStatusBarColor(
        color = MaterialTheme.colorScheme.background,
        darkIcons = useDarkIcons
    )

    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colorScheme.onSecondary,
        darkIcons = useDarkIcons
    )

    val verticalScrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(state = verticalScrollState)
    ) {
        GreetingText()

        // DOG FACTS

        DogFact(
            modifier = Modifier
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                .fillMaxWidth(),
            mainViewModel = mainViewModel
        )

        // CAT FACTS

        CatFact(
            modifier = Modifier
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                .fillMaxWidth(),
            mainViewModel = mainViewModel
        )

        // WEATHER

        Text(
            text = "Weather Today",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 20.dp, start = 10.dp, end = 10.dp)
        )
        WeatherCard(
            modifier = Modifier
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                .fillMaxWidth(), mainViewModel = mainViewModel
        )

        // PODCAST

        Text(
            text = "Featured Podcast",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 20.dp, start = 10.dp, end = 10.dp)
        )
        CustomHtmlWebView(modifier = Modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .height(160.dp) )


        // SONG

        Text(
            text = "Featured Song",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 20.dp, start = 10.dp, end = 10.dp)
        )
        CustomHtmlWebView(modifier = Modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .height(160.dp), innerHeight = "220px", innerIframe = "<iframe style=\"border-radius:12px\" src=\"https://open.spotify.com/embed/track/5QO3UJc1gF1ummP75n2b3R?utm_source=generator\" width=\"100%\" height=\"352\" frameBorder=\"0\" allowfullscreen=\"\" allow=\"autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture\" loading=\"lazy\"></iframe>" )


        Text(
            text = "Featured Artist",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 20.dp, start = 10.dp, end = 10.dp)
        )
        CustomHtmlWebView(modifier = Modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .height(400.dp), innerHeight = "400px", innerIframe = "<iframe style=\"border-radius:12px\" src=\"https://open.spotify.com/embed/artist/6M2wZ9GZgrQXHCFfjv46we?utm_source=generator\" width=\"100%\" height=\"352\" frameBorder=\"0\" allowfullscreen=\"\" allow=\"autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture\" loading=\"lazy\"></iframe>" )






        // NEWS

        Text(
            text = "News from World",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 20.dp, start = 10.dp, end = 10.dp)
        )
        if (mainViewModel.news.value.isNotEmpty()) {
            // Compute the filtered and limited list once
            val articlesWithImages = mainViewModel.news.value
                .filter { it.image != null }
                .take(20)

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(articlesWithImages.size) { index ->
                    NewsArticleCard(article = articlesWithImages[index])
                }
            }
        }

    }

}


@Composable
fun GreetingText() {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greeting = when (currentHour) {
        in 0..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        in 17..20 -> "Good Evening"
        else -> "Good Night"
    }

    Text(
        text = greeting,
        modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp),
        style = MaterialTheme.typography.displaySmall.copy(fontSize = 23.sp, fontWeight = FontWeight.Normal),
        maxLines = 1
    )
}



@Composable
fun WeatherCard(modifier: Modifier = Modifier, mainViewModel: MainViewModel) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "How's the weather in ${mainViewModel.weatherResponse.value.timezone} today",
                style = MaterialTheme.typography.bodySmall
            )

            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ensure image has a proper size
//                AsyncImage(model = ImageRequest.Builder(LocalContext.current).data("https://openweathermap.org/img/wn/10n@2x.png").build(),
//                    modifier = Modifier
//                    .size(84.dp) // Specify a size for the image
//                    .padding(end = 8.dp),
//                    contentScale = ContentScale.Fit,
//                    contentDescription = "", )
                Image(
                    painter = rememberAsyncImagePainter(model = "https://openweathermap.org/img/wn/${mainViewModel.weatherResponse.value.current.weather.first().icon}@2x.png"),
                    contentDescription = "Weather Icon",
                    modifier = Modifier
                        .size(84.dp) // Specify a size for the image
                        .padding(end = 8.dp),
                    contentScale = ContentScale.Fit
                )

                // Temperature text
                Text(
                    text = mainViewModel.weatherResponse.value.current.temp.toInt()
                        .toString() + "°C",
                    style = MaterialTheme.typography.displaySmall,
                )

                Column(modifier = Modifier.padding(start = 20.dp)) {
                    Text(
                        text = mainViewModel.weatherResponse.value.current.weather.first().main,
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = mainViewModel.weatherResponse.value.current.weather.first().description,
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = "Feels like: " + mainViewModel.weatherResponse.value.current.feelsLike.toInt()
                            .toString(),
                        style = MaterialTheme.typography.bodySmall,
                    )

                }
            }
        }

    }
}


@Composable
fun DogFact(modifier: Modifier = Modifier, mainViewModel: MainViewModel) {
    val isExpanded = remember { mutableStateOf(false) }
    Column(modifier = modifier) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 20.dp,
                bottomEnd = 10.dp,
                bottomStart = 10.dp
            ),
//            colors = CardColors(
//                containerColor = MaterialTheme.colorScheme.secondary,
//                contentColor = MaterialTheme.colorScheme.onSecondary,
//                disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
//                disabledContentColor = MaterialTheme.colorScheme.onPrimary
//            )
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dog),
                        contentDescription = "dog",
                        modifier = Modifier.size(90.dp)
                    )
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(text = "Daily Dog facts", style = MaterialTheme.typography.bodyMedium)

                    }
                    Icon(
                        imageVector = if (isExpanded.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "expand",
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                isExpanded.value = !isExpanded.value
                            }
                    )

                }
            }
            AnimatedVisibility(visible = isExpanded.value) {
                DogCatFactsPagerItem(
                    list = mainViewModel.dogFacts.value.map { it.attributes.body },
                    modifier = Modifier.padding(top = 10.dp)
                )
            }


        }
    }
}

@Composable
fun CatFact(modifier: Modifier = Modifier, mainViewModel: MainViewModel) {
    val isExpanded = remember { mutableStateOf(false) }
    Column(modifier = modifier) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 20.dp,
                bottomEnd = 10.dp,
                bottomStart = 10.dp
            ),
//            colors = CardColors(
//                containerColor = MaterialTheme.colorScheme.secondary,
//                contentColor = MaterialTheme.colorScheme.onSecondary,
//                disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
//                disabledContentColor = MaterialTheme.colorScheme.onPrimary
//            )
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.scratch),
                        contentDescription = "cat",
                        modifier = Modifier.size(90.dp)
                    )
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(text = "Daily Cat facts", style = MaterialTheme.typography.bodyMedium)

                    }
                    Icon(
                        imageVector = if (isExpanded.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Previous",
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                isExpanded.value = !isExpanded.value
                            }
                    )

                }
            }
            AnimatedVisibility(visible = isExpanded.value) {
                DogCatFactsPagerItem(
                    list = mainViewModel.catFacts.value.map { it.text },
                    modifier = Modifier.padding(top = 10.dp)
                )
            }


        }
    }

}






