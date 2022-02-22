package ru.castprograms.hackeducation.tools

import android.content.Context

object AndroidTools {
    fun dp(context: Context, pixel: Int) =
        context.resources.displayMetrics.density * pixel
}