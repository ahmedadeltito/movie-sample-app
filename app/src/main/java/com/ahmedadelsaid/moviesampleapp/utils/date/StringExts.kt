@file:Suppress("unused")

package com.ahmedadelsaid.moviesampleapp.utils.date

import java.text.SimpleDateFormat
import java.util.*

/**
 * Date Useful Extensions.
 */

fun String.toDate(format: String): Date = SimpleDateFormat(format, Locale.US).parse(this)