package com.doganesad.chronosapp.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DogCatFactsPagerItem(list: List<String>, modifier: Modifier) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()





    Row(
        modifier = modifier
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Previous icon button
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "Previous",
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    scope.launch {
                        if (pagerState.currentPage > 0) {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                }
        )

        // Horizontal Pager
        HorizontalPager(
            count = list.size, // Example page count
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .heightIn(min = 200.dp)// Take up remaining space
        ) { page ->
            // Example content: display the page number
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = list[page],style = MaterialTheme.typography.bodySmall)
                    Text(text = "${page+1}/${list.size}",style = MaterialTheme.typography.bodySmall,modifier = Modifier.padding(top = 10.dp))
                }
            }
        }

        // Next icon button
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Next",
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    scope.launch {
                        if (pagerState.currentPage < pagerState.pageCount - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                }
        )
    }
}