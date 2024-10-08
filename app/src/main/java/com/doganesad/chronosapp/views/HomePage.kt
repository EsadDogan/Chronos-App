package com.doganesad.chronosapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.doganesad.chronosapp.R
import com.doganesad.chronosapp.models.CatFact
import com.doganesad.chronosapp.models.DogFact
import com.doganesad.chronosapp.viewmodels.MainViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel) {

    Column(modifier = modifier.padding(10.dp)) {
        Text(
            text = "Good Afternoon Edwin",
            modifier = Modifier.padding(top = 10.dp),
            style = MaterialTheme.typography.displaySmall,
            fontSize = 22.sp
        )

        DogFact(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
        )

        CatFact(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        )

    }

}


@Composable
fun DogFact(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 20.dp,
            bottomEnd = 10.dp,
            bottomStart = 10.dp
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary,
            disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.dog),
                contentDescription = "dog",
                modifier = Modifier.size(90.dp)
            )
            Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                Text(text = "Daily Dog facts", style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Dogs Can Walk 800 Miles a Day",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

    }
}

@Composable
fun CatFact(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 20.dp,
            bottomEnd = 10.dp,
            bottomStart = 10.dp
        ),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.scratch),
                contentDescription = "cat",
                modifier = Modifier.size(90.dp)
            )
            Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                Text(text = "Daily Cat facts", style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Cats can jump higher than 72 inches",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

    }
}