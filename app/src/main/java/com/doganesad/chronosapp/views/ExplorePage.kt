package com.doganesad.chronosapp.views

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberAsyncImagePainter
import com.doganesad.chronosapp.models.BookModel
import com.doganesad.chronosapp.utils.Screens
import com.doganesad.chronosapp.viewmodels.MainViewModel

@Composable
fun ExploreScreen(modifier: Modifier = Modifier,mainViewModel: MainViewModel) {

    Text(text = "Library", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(10.dp))
    BookGrid(books = mainViewModel.books.value,mainViewModel)

    
}

@Composable
fun BookGrid(books: List<BookModel>,mainViewModel: MainViewModel) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize().padding(top = 40.dp)
    ) {
        items(books) { book ->
            BookCard(book = book,mainViewModel)
        }
    }
}


@Composable
fun BookCard(book: BookModel,mainViewModel: MainViewModel) {
    Card(
        onClick = {
          mainViewModel.selectedBook.value = book
          mainViewModel.navController.navigate(Screens.READING_PAGE)

        },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp).heightIn(min = 360.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(book.bookImageUrl),
                contentDescription = "Book Cover",
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = book.bookName,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = book.bookAuthor,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
            )
        }
    }
}

