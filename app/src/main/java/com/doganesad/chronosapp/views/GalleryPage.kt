package com.doganesad.chronosapp.views

import android.provider.CalendarContract
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.doganesad.chronosapp.models.Photo
import com.doganesad.chronosapp.viewmodels.MainViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalleryScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel) {
    val photos = mainViewModel.curratedPhotos.value

    Column {


        Card(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextField(
                    value = mainViewModel.searchBarText.value,
                    onValueChange = {
                        mainViewModel.searchBarText.value = it
                        if (it.isNotEmpty()) {

                            mainViewModel.searchForImage()

                        } else {

                            mainViewModel.getCuratedPhotos()

                        }
                    },
                    label = { Text("Search by Pexels") }, // Label is a composable function
                    modifier = Modifier.weight(1f),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "search"
                        )
                    }
                )
            }
        }

        // Handle empty state
        if (photos.isNullOrEmpty()) {
            // Show some placeholder or empty state text
            Text(text = "No photos available", modifier = Modifier.fillMaxSize())
        } else {
            // Render the grid with 1 item per row (full width image)
            LazyVerticalGrid(
                columns = GridCells.Fixed(1), // 1 item per row for full width
                modifier = Modifier
                    .weight(1f)
                    .then(modifier) // Fill the available space
            ) {
                items(photos.size) { index ->
                    // Add debugging to see if items are being displayed
                    if (photos[index].src.original.isNotEmpty()) {
                        PhotosItem(
                            photo = photos[index],
                            modifier = Modifier
                                .fillMaxWidth() // Full width image
                                .aspectRatio(4f / 5f)// Set aspect ratio (e.g., 1:1 for square images)
                            // Add padding
                        )
                    } else {
                        Text(
                            text = "Image not available",
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        }
    }


}

@Composable
fun PhotosItem(modifier: Modifier = Modifier, photo: Photo) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .height(450.dp),
        elevation = CardDefaults.cardElevation(12.dp),
        shape = MaterialTheme.shapes.large,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Image(
                painter = rememberAsyncImagePainter(model = photo.src.large),
                contentDescription = "Photo", // Provide meaningful descriptions for accessibility
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.large), // Use the modifier passed from parent
                contentScale = ContentScale.Crop // Scale the image to fill the space properly
            )


            // Overlay content (pet info)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        // siyahlastirma
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 1000f
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                Text(
                    text = "Photographer: " + photo.photographer,
                    modifier = Modifier.padding(start = 10.dp,end = 10.dp,bottom = 20.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
        }
    }

}

