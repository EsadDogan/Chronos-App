import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
    alias(libs.plugins.google.firebase.crashlytics)
}

val localProperties = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
}

val apiKeyWeatherNew: String = localProperties.getProperty("API_KEY_WEATHER_NEW") ?: ""
val apiKeyNewsNew: String = localProperties.getProperty("API_KEY_NEWS_NEW") ?: ""
val apiKeyPexels: String = localProperties.getProperty("API_KEY_PEXELS") ?: ""


android {
    namespace = "com.doganesad.chronosapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.doganesad.chronosapp"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"


        // Inject the API keys into the BuildConfig class
        buildConfigField("String", "API_KEY_WEATHER_NEW", "\"$apiKeyWeatherNew\"")
        buildConfigField("String", "API_KEY_NEWS_NEW", "\"$apiKeyNewsNew\"")
        buildConfigField("String", "API_KEY_PEXELS", "\"$apiKeyPexels\"")



        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }



    buildTypes {
        release {
            isMinifyEnabled = false

            }
        debug {
            isMinifyEnabled = false

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.accompanist.systemuicontroller)

    //SplashScreen
    implementation(libs.androidx.core.splashscreen)

    //Retrofit
    implementation(libs.retrofit)

    // Gson Converter
    implementation(libs.converter.gson)

    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.navigation:navigation-compose:2.8.2")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("com.google.accompanist:accompanist-pager:0.30.0")
    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation ("com.google.android.gms:play-services-auth:21.2.0")

    implementation ("com.google.accompanist:accompanist-permissions:0.32.0")



}
