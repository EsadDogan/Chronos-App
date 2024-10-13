package com.doganesad.chronosapp.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.doganesad.chronosapp.viewmodels.MainViewModel



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalleryScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel) {
    val photos = mainViewModel.curratedPhotos.value

    // Handle empty state
    if (photos.isNullOrEmpty()) {
        // Show some placeholder or empty state text
        Text(text = "No photos available", modifier = Modifier.fillMaxSize())
    } else {
        // Render the grid with 1 item per row (full width image)
        LazyVerticalGrid(
            columns = GridCells.Fixed(1), // 1 item per row for full width
            modifier = Modifier
                .fillMaxSize()
                .then(modifier) // Fill the available space
        ) {
            items(photos.size) { index ->
                // Add debugging to see if items are being displayed
                if (photos[index].src.original.isNotEmpty()) {
                    PhotosItem(
                        src = photos[index].src.large,
                        modifier = Modifier
                            .fillMaxWidth() // Full width image
                            .aspectRatio(4f / 5f)// Set aspect ratio (e.g., 1:1 for square images)
                            .padding(10.dp) // Add padding
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

@Composable
fun PhotosItem(modifier: Modifier = Modifier, src: String) {
    Card(onClick = { /*TODO*/ }, modifier = modifier.fillMaxWidth()) {
        Image(
            painter = rememberAsyncImagePainter(model = src),
            contentDescription = "Photo", // Provide meaningful descriptions for accessibility
            modifier = Modifier.fillMaxWidth(), // Use the modifier passed from parent
            contentScale = ContentScale.Crop // Scale the image to fill the space properly
        )
    }

}

