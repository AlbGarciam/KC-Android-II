package com.soundapp.mobile.utils.extensions

import android.view.View
import android.view.animation.AccelerateInterpolator

fun View.setVisible(isVisible: Boolean) {
    val initialAlpha = if (visibility == View.VISIBLE) 1f else 0f
    val finalAlpha = if (isVisible) 1f else 0f
    alpha = initialAlpha

    visibility = if (isVisible) View.VISIBLE else View.GONE
    this.animate().alpha(finalAlpha).setDuration(200)
        .setInterpolator(AccelerateInterpolator()).start()
}