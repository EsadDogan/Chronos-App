package com.doganesad.chronosapp.views

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.doganesad.chronosapp.R
import com.doganesad.chronosapp.ui.theme.AppTheme
import com.doganesad.chronosapp.ui.theme.Shapes
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.doganesad.chronosapp.ui.theme.backgroundDark
import com.doganesad.chronosapp.utils.GoogleAuthHelper
import com.doganesad.chronosapp.utils.Screens
import com.doganesad.chronosapp.viewmodels.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.delay


fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
fun LoginScreenMain(modifier: Modifier = Modifier,mainViewModel: MainViewModel = MainViewModel()) {

    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var displayName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        mainViewModel.handleSignInResult(task)
    }

//    val signInState by mainViewModel.signInState.observeAsState()

    Box(Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center,) {
        Image(painter = painterResource(id = R.drawable.login_bg), contentDescription ="", modifier.fillMaxSize(),alignment = Alignment.TopCenter )
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Bottom) {



            Text(text = context.getString(R.string.app_name), color = Color.White,style = MaterialTheme.typography.displayLarge,modifier = Modifier.padding(bottom = 16.dp).padding(horizontal = 10.dp))
            TypewriterText(text = context.getString(R.string.login_title))

            Spacer(modifier = Modifier.height(20.dp))


            TextField(
                value = displayName,
                onValueChange = { displayName = it },
                label = { Text(text=context.getString(R.string.lbl_sign_in_dislay_name), color = Color.White) }, // Label color set to white
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, disabledContainerColor = Color.Transparent)
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.White) }, // Label color set to white
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, disabledContainerColor = Color.Transparent)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password",color = Color.White) },
                colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, disabledContainerColor = Color.Transparent),
                        visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LoginButton(
                onClick = {
                    if (!isValidEmail(email)) {
                        errorMessage = context.getString(R.string.lbl_error_email)
                    } else if (password.length !in 6..10) {
                        errorMessage = context.getString(R.string.lbl_error_password)
                    }else if (displayName.isEmpty()) {
                        errorMessage = context.getString(R.string.lbl_error_name)
                    } else {
                        mainViewModel.signUpWithEmail(email, password) { success, error ->
                            if (success) {
                                mainViewModel.navController.navigate(Screens.HOME_TABS_PAGE) {
                                    popUpTo(0)
                                }
                            } else {
                                errorMessage = error ?: context.getString(R.string.lbl_error_unknown)
                            }
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            errorMessage?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = context.getString(R.string.lbl_or), color = Color.White,style = MaterialTheme.typography.titleSmall,modifier = Modifier.padding(bottom = 16.dp).padding(horizontal = 10.dp))

            Spacer(modifier = Modifier.height(20.dp))



            GoogleButton {
                val googleSignInClient = GoogleAuthHelper.getGoogleSignInClient(context)
                val signInIntent = googleSignInClient.signInIntent
                launcher.launch(signInIntent)
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(text = context.getString(R.string.login_desc), color = Color.White,style = MaterialTheme.typography.titleSmall,modifier = Modifier.padding(bottom = 16.dp).padding(horizontal = 10.dp))


            Spacer(modifier = Modifier.height(40.dp))

//            // React to sign-in state
//            LaunchedEffect(signInState) {
//                if (signInState == true) {
//                    mainViewModel.navController.navigate(Screens.HOME_TABS_PAGE) {
//                        popUpTo(0)
//                    }
//                }
//            }

        }
    }

}

@Composable
fun TypewriterText(text: String, modifier: Modifier = Modifier, delay: Int = 50) {
    var displayedText by remember { mutableStateOf("") }

    // Animate the text character by character
    LaunchedEffect(text) {
        for (i in text.indices) {
            delay(delay.toLong())  // Delay between each character
            displayedText = text.substring(0, i + 1)  // Update the displayed text
        }
    }

    Text(
        text = displayedText,
        color = Color.White,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier.padding(bottom = 16.dp).padding(horizontal = 10.dp)
    )
}




@Composable
fun LoginButton(
    text: String = stringResource(id = R.string.btn_sign_in),
    shape: Shape = Shapes.medium,
    borderColor: Color = Color.LightGray,
    onClick: () -> Unit
) {

//    var clicked by remember  { mutableStateOf(false) }

    Surface(
        onClick = {
//            clicked = !clicked
            onClick()
        },
        shape = shape,
        border = BorderStroke(width = 1.dp, color = borderColor),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {


            Spacer(modifier = Modifier.width(8.dp))

            Text(text =  text)

        }
    }
}


@Composable
fun GoogleButton(
    text: String = stringResource(id = R.string.btn_sign_in_google),
    icon: Painter = painterResource(id = R.drawable.ic_google_logo),
    isLoading: Boolean = false,
    shape: Shape = Shapes.medium,
    borderColor: Color = Color.LightGray,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {

//    var clicked by remember  { mutableStateOf(false) }

    Surface(
        onClick = {
//            clicked = !clicked
            onClick()
        },
        shape = shape,
        border = BorderStroke(width = 1.dp, color = borderColor),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                painter = icon,
                contentDescription = "Google Button",
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text =  text)

            if (isLoading) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


@Preview
@Composable
private fun LoginScreenPrev() {
    AppTheme {
        LoginScreenMain(modifier = Modifier.fillMaxSize())
    }
}