package com.doganesad.chronosapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.doganesad.chronosapp.R
import com.doganesad.chronosapp.utils.Screens
import com.doganesad.chronosapp.viewmodels.MainViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun MoreScreen(modifier: Modifier = Modifier,mainViewModel: MainViewModel) {
    val user = Firebase.auth.currentUser
    val displayName = user?.displayName ?: "Stranger Danger"

    Text(text = "More", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(10.dp))

    Column(modifier = modifier
        .padding(start = 10.dp, end = 10.dp, top = 60.dp)
        .fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(painter = painterResource(id = R.drawable.user), contentDescription = "profile_pic", modifier = Modifier
            .padding(bottom = 10.dp)
            .size(120.dp))
        Text(text = "Hello, $displayName!",style = MaterialTheme.typography.headlineMedium)
        Button(modifier = Modifier.padding(top = 20.dp),onClick = {
            Firebase.auth.signOut()
            mainViewModel.navController.navigate(Screens.LOGIN_PAGE)
        }) {
            Text(text = "Sign Out")

        }

        Card(modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()) {
            Column(modifier = Modifier.padding(5.dp)) {
                Text(text = "About Developer",modifier = Modifier.padding(vertical = 10.dp).fillMaxWidth(), textAlign = TextAlign.Center)
                Text(text = "Esad Dogan",modifier = Modifier.padding(bottom = 5.dp),style = MaterialTheme.typography.bodySmall,color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                Text(text = "esadddogan@gmail.com",modifier = Modifier.padding(bottom = 5.dp),style = MaterialTheme.typography.bodySmall,color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            }


        }
    }


}