package com.doganesad.chronosapp.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.doganesad.chronosapp.viewmodels.MainViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun MoreScreen(modifier: Modifier = Modifier,mainViewModel: MainViewModel) {
    val user = Firebase.auth.currentUser
    val displayName = user?.displayName ?: "someone"
    Text(text = "Hello, $displayName!")

}