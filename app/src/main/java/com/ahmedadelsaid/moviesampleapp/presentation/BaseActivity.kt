package com.ahmedadelsaid.moviesampleapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Main Base Activity, didn't has much logic but maybe we can make some abstraction code here for future.
 */

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectActivity()
    }

    abstract fun injectActivity()

}