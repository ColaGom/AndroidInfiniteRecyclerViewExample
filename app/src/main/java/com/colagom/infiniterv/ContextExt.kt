package com.colagom.infiniterv

import android.content.Context
import android.util.DisplayMetrics

fun Context.dpToPx(dp: Float): Float {
    return dp * getPixelScaleFactor(this)
}

private fun getPixelScaleFactor(context: Context): Float {
    val displayMetrics = context.resources.displayMetrics
    return displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT
}