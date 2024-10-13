package com.doganesad.chronosapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.doganesad.chronosapp.models.NewsArticle

@Composable
fun NewsArticleCard(article: NewsArticle, modifier: Modifier = Modifier) {
    Card(
        onClick = {
            // TODO: see details about news
        },
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .width(300.dp) // Fixed width for better horizontal scrolling experience
            .height(350.dp) // Fixed height for better horizontal scrolling experience
            .padding(8.dp), // Padding between cards in LazyRow
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Slightly lower elevation for performance
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            // Article Image

            if (article.image == null) {
                Image(
                    painter = rememberAsyncImagePainter(model = "https://firebasestorage.googleapis.com/v0/b/chronos-app-4df21.appspot.com/o/rb_22696.png?alt=media&token=8d08bb08-4397-4dda-9314-90c9f1c4be9e"),
                    contentDescription = "News Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),  // Image height can stay fixed
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
            } else {
                Image(
                    painter = rememberAsyncImagePainter(model = article.image),
                    contentDescription = "News Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),  // Image height can stay fixed
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Title
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp), // Reduced padding for compact layout
                maxLines = 2 // Limit the title to 2 lines for better readability in a row
            )

            // Author
            article.author?.let { author ->
                Text(
                    text = "Author: $author",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            // Description
            Text(
                text = article.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2, // Limit description to 2 lines to save space
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Published Date
            Text(
                text = "Published on: ${article.publishedAt}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}