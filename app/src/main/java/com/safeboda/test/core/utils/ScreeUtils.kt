package com.safeboda.test.core.utils

import android.app.Activity
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowMetrics

object ScreeUtils {

    fun calculateScreen(activity: Activity?): Int =
        when {
            activity == null -> -1

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                val windowMetrics: WindowMetrics = activity.windowManager.currentWindowMetrics
                val insets: Insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(
                    WindowInsets.Type.systemBars()
                )
                windowMetrics.bounds.width() - insets.left - insets.right
            }
            else -> {
                val displayMetrics = DisplayMetrics()
                activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
                displayMetrics.widthPixels
            }
        }

}