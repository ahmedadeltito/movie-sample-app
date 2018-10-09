package com.ahmedadelsaid.moviesampleapp.utils

import android.content.res.Resources

object DensityUtils {

    /**
     * Converts the given amount of pixels to a dp value.
     * @param pixels The pixel-based measurement
     * @return The measurement's value in dp, based on the device's screen density
     */
    fun pxToDp(pixels: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        return pixels / (metrics.densityDpi / 160f)
    }

    /**
     * Converts the given dp measurement to pixels.
     * @param dp The measurement, in dp
     * @return The corresponding amount of pixels based on the device's screen density
     */
    fun dpToPx(dp: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        return dp * (metrics.densityDpi / 160f)
    }
}