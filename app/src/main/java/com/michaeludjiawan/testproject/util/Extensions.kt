package com.michaeludjiawan.testproject.util

import android.view.View

fun toVisibility(constraint: Boolean): Int = if (constraint) {
    View.VISIBLE
} else {
    View.GONE
}