package com.doganesad.chronosapp.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.doganesad.chronosapp.R
import com.doganesad.chronosapp.viewmodels.MainViewModel


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeTabScreen(mainViewModel: MainViewModel) {

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(

        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp),
                containerColor = MaterialTheme.colorScheme.onSecondary,
            ) {
                NavigationBarItem(
                    icon = {

                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.home_nav_bar),
                            contentDescription = "Ev"
                        )


                    },
                    label = {
                        Text(
                            "Home",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp
                        )
                    },
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    alwaysShowLabel = false
                )

                NavigationBarItem(
                    icon = {

                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.book_nav_bar),
                            contentDescription = "library"
                        )

                    },
                    label = {
                        Text(
                            "Library",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp
                        )
                    },
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    alwaysShowLabel = false
                )

                NavigationBarItem(
                    icon = {

                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.gallery_nav_bar),
                            contentDescription = "Gallery"
                        )

                    },
                    label = {
                        Text(
                            "Gallery",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp
                        )
                    },
                    selected = selectedTabIndex == 2,
                    onClick = { selectedTabIndex = 2 },
                    alwaysShowLabel = false
                )

                NavigationBarItem(
                    icon = {

                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.more_nav_bar),
                            contentDescription = "Gallery"
                        )

                    },
                    label = {
                        Text(
                            "More",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp
                        )
                    },
                    selected = selectedTabIndex == 3,
                    onClick = { selectedTabIndex = 3 },
                    alwaysShowLabel = false
                )


            }
        },
        content = { innerPadding ->
            AnimatedContent(
                modifier = Modifier.padding(innerPadding),
                targetState = selectedTabIndex,
//                transitionSpec = {
//                    fadeIn(tween(300)) with fadeOut(tween(300)) // You can define your transition here
//                },
                label = ""
            ) { targetTab ->
                when (targetTab) {
                    0 -> HomeScreen(
                        modifier = Modifier.padding(),
                        mainViewModel = mainViewModel
                    )

                    1 -> ExploreScreen(
                        modifier = Modifier.padding(),
                        mainViewModel = mainViewModel
                    )

                    2 -> GalleryScreen(
                        modifier = Modifier.padding(),
                        mainViewModel = mainViewModel
                    )

                    3 -> MoreScreen(
                        modifier = Modifier.padding(),
                        mainViewModel = mainViewModel
                    )
                }
            }
        }
    )

}