package dependencies

/**
 * Created by Ahmed Adel on 06/10/2018.
 *
 * Version that will be used in Dependecies kotlin file.
 */

@Suppress("unused")
object Versions {

    // Version Name and Code
    const val versionName = "1.0"
    const val versionCode = 1

    object SupportAndroidLibs {
        const val servicesPlugin = "3.2.0"
        const val compileSdk = 28
        const val minSdk = 21
        const val targetSdk = 28
    }

    object AndroidX {
        const val main = "1.0.0"
        const val material = "1.0.0-rc01"
        const val multiDex = "2.0.0"
        const val constraintLayout = "1.1.2"
        const val androidArc = "2.0.0-rc01"
    }

    object Testing {
        const val mockito = "2.10.0"
        const val espresso = "3.1.0-alpha3"
        const val runner = "1.1.0-alpha3"
        const val junit = "4.12"
        const val junitPlatform = "1.0.0"
        const val spek = "1.1.5"
    }

    object Kotlin {
        const val std = "1.2.71"
    }

    object Google {
        const val playServices = "12.0.1"
        const val firebase = "12.0.1"
        const val dagger = "2.16"
    }

    object Libraries {
        // RxJava and RxAndroid
        const val rxAndroid = "2.0.1"
        const val rxJava = "2.1.9"
        const val rxRelay = "2.0.0"
        const val rxIdler = "0.9.0"

        // OkHttp and Retrofit
        const val retrofit = "2.3.0"
        const val okHttp = "3.11.0"

        // Paging Library of Android Arch Component to make it easier for you to load data
        // gradually and gracefully within your app's
        const val paging = "1.0.0"

        // Gson
        const val gson = "2.8.2"

        // Markomilos For Pagination
        const val markomilos = "0.5.1"

        // Timber
        const val timber = "1.5.1"

        // ButterKnife
        const val butterknife = "8.8.1"

        //LeakCanary
        const val leakCanary = "1.5.4"

        // Glide for Image Handling
        const val glide = "4.8.0"

        // Anko
        const val ankoVersion = "0.10.7"

        // fabric
        const val fabric = "1.25.4"
        const val crahslytics = "2.9.4@aar"

    }
}