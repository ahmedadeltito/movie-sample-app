package dependencies

/**
 * Created by Ahmed Adel on 06/10/2018.
 *
 * Dependencies that will be used in build.gradle file for each module.
 */

@Suppress("unused")
object Dependencies {

    val AndroidXLibs = arrayOf(
            // Android Annotation
            "androidx.annotation:annotation:${Versions.AndroidX.main}",

            // AppCompat
            "androidx.appcompat:appcompat:${Versions.AndroidX.main}",

            //CardView
            "androidx.cardview:cardview:${Versions.AndroidX.main}",

            // RecyclerView
            "androidx.recyclerview:recyclerview:${Versions.AndroidX.main}",

            // Google Chrome Custom Tab
            "androidx.browser:browser:${Versions.AndroidX.main}",

            // MultiDex
            "androidx.multidex:multidex:${Versions.AndroidX.multiDex}",

            // ConstraintLayout
            "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.constraintLayout}",

            // Android Material
            "com.google.android.material:material:${Versions.AndroidX.material}",

            // Android Arch Lifecycle
            "androidx.lifecycle:lifecycle-compiler:${Versions.AndroidX.androidArc}",
            "androidx.lifecycle:lifecycle-extensions:${Versions.AndroidX.androidArc}",
            "androidx.lifecycle:lifecycle-reactivestreams:${Versions.AndroidX.androidArc}",

            // Android Arch Room
            "androidx.room:room-runtime:${Versions.AndroidX.androidArc}",
            "androidx.room:room-rxjava2:${Versions.AndroidX.androidArc}"
    )

    val Testing = arrayOf(
            // Android Unit Testing
            "androidx.test:runner:${Versions.Testing.runner}",
            "androidx.test.espresso:espresso-core:${Versions.Testing.espresso}",

            // Mockito
            "org.mockito:mockito-core:${Versions.Testing.mockito}",

            // JUnit
            "junit:junit:${Versions.Testing.junit}",

            // Android Arch Room
            "androidx.room:room-testing:${Versions.AndroidX.androidArc}",

            // RxIdler
            "com.squareup.rx.idler:rx2-idler:${Versions.Libraries.rxIdler}",

            // Spek for Kotlin Unit Testing
            "org.jetbrains.spek:spek-api:${Versions.Testing.spek}"
    )

    val Kotlin = arrayOf(
            "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.Kotlin.std}"
    )

    val Google = arrayOf(
            "com.google.android.gms:play-services-analytics:${Versions.Google.playServices}",
            "com.google.firebase:firebase-core:${Versions.Google.firebase}",
            "com.google.dagger:dagger:${Versions.Google.dagger}",
            "com.google.dagger:dagger-android-support:${Versions.Google.dagger}"
    )

    val Libraries = arrayOf(
            // RxJava, RxAndroid and RxRelay
            "io.reactivex.rxjava2:rxandroid:${Versions.Libraries.rxAndroid}",
            "io.reactivex.rxjava2:rxjava:${Versions.Libraries.rxJava}",
            "com.jakewharton.rxrelay2:rxrelay:${Versions.Libraries.rxRelay}",

            // OkHttp and Retrofit
            "com.squareup.okhttp3:okhttp:${Versions.Libraries.okHttp}",
            "com.squareup.okhttp3:logging-interceptor:${Versions.Libraries.okHttp}",
            "com.squareup.okhttp3:okhttp-urlconnection:${Versions.Libraries.okHttp}",
            "com.squareup.retrofit2:retrofit:${Versions.Libraries.retrofit}",
            "com.squareup.retrofit2:converter-gson:${Versions.Libraries.retrofit}",
            "com.squareup.retrofit2:adapter-rxjava2:${Versions.Libraries.retrofit}",
            "com.squareup.retrofit2:retrofit-mock:${Versions.Libraries.retrofit}",

            // Gson
            "com.squareup.retrofit2:converter-gson:${Versions.Libraries.retrofit}",

            // Markomilos For Pagination
            "com.github.markomilos:paginate:${Versions.Libraries.markomilos}",

            // TimberKotlin
            "com.github.ajalt:timberkt:${Versions.Libraries.timber}",

            // LeakCanary
            "com.squareup.leakcanary:leakcanary-android:${Versions.Libraries.leakCanary}",

            // Glide
            "com.github.bumptech.glide:glide:${Versions.Libraries.glide}",

            // Anko
            "org.jetbrains.anko:anko:${Versions.Libraries.ankoVersion}"
    )

    val Annotations = arrayOf(
            // Android Architecture Components
            "androidx.lifecycle:lifecycle-compiler:${Versions.AndroidX.androidArc}",

            // ButterKnife
            "com.jakewharton:butterknife-compiler:${Versions.Libraries.butterknife}"
            )

    val Kapt = arrayOf(
            // Android Arch Room
            "androidx.room:room-compiler:${Versions.AndroidX.androidArc}",

            // Dagger
            "com.google.dagger:dagger-compiler:${Versions.Google.dagger}",

            // Glide
            "com.github.bumptech.glide:compiler:${Versions.Libraries.glide}"
    )

}