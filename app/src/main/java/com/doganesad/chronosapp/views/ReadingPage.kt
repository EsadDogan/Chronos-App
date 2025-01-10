package com.doganesad.chronosapp.views

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.doganesad.chronosapp.models.BookModel
import com.doganesad.chronosapp.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingPage(modifier: Modifier = Modifier, mainViewModel: MainViewModel) {
    val pdfUrl = mainViewModel.selectedBook.value.bookPdfUrl
    val googleDocsUrl = "https://docs.google.com/gview?embedded=true&url=$pdfUrl"


    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                  IconButton(onClick = { mainViewModel.navController.popBackStack() }) {
                      Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                  }
                },
                title = {
                Text(text = mainViewModel.selectedBook.value.bookName, style = MaterialTheme.typography.bodyMedium)
                })
        },
        content = { paddingValues ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {

                AndroidView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    factory = { context ->
                        WebView(context).apply {
                            settings.javaScriptEnabled = true
                            canZoomIn()
                            canZoomOut()
                            webViewClient = WebViewClient()
                            loadUrl(googleDocsUrl)
                        }
                    },
                    update = {
                        it.loadUrl(googleDocsUrl)
                    }
                )
            }
        }
    )




}