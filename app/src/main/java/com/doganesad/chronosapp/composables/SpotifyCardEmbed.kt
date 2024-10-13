package com.doganesad.chronosapp.composables

import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CustomHtmlWebView(modifier: Modifier = Modifier,innerHeight: String = "220px",innerIframe: String = "<iframe style=\"border-radius:12px\" src=\"https://open.spotify.com/embed/episode/5kdVKBDU2xHajcLUHcIWlP?utm_source=generator&theme=0\" width=\"100%\" height=\"352\" frameBorder=\"0\" allowfullscreen=\"\" allow=\"autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture\" loading=\"lazy\"></iframe>") {
    // Use remember to persist the WebView across recompositions
    val context = LocalContext.current
    val webView = remember { WebView(context) }

    // Define your custom HTML content with a transparent background
    val customHtml = """
        <html>
        <head>
            <style>
                body {
                    margin: 0;
                    padding: 0;
                    background-color: transparent; /* Set transparent background */
                }
                iframe {
                    border-radius: 12px;
                    width: 100%;
                    height: ${innerHeight};
                    border: none;
                    background-color: transparent; /* Set iframe background to transparent */
                }
            </style>
        </head>
        <body>
                       ${innerIframe}
            </body>  
         </html>
    """.trimIndent()

    // This effect will ensure the WebView is cleaned up when the Composable is disposed
    DisposableEffect(Unit) {
        // Cleanup the WebView when the composable is removed
        onDispose {
            webView.stopLoading()
            webView.clearHistory()
            webView.clearCache(true)
            webView.loadUrl("about:blank")
            webView.removeAllViews()
            webView.destroy()
        }
    }

    // WebView component in Compose using AndroidView
    AndroidView(
        factory = {
            webView.apply {
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                        // Allow Spotify URLs to be loaded inside WebView
                        if (url != null && url.startsWith("https://open.spotify.com")) {
                            return false
                        } else {
                            // Open other links in the external browser
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                            return true
                        }
                    }
                }

                // Enable JavaScript and DOM storage
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true

                // Set WebView background to be transparent
                setBackgroundColor(0x00000000) // Fully transparent

                // Load the custom HTML content
                loadDataWithBaseURL(null, customHtml, "text/html", "utf-8", null)
            }
        },
        modifier = modifier
            .fillMaxWidth() // Set the height (adjust as needed)
            // Set the height (adjust as needed)
    )
}