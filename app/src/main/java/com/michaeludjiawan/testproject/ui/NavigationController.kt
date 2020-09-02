package com.michaeludjiawan.testproject.ui

import androidx.fragment.app.Fragment

interface NavigationController {
    fun navigateToPage(
        fragment: Fragment,
        addToBackStack: Boolean = true,
        navigationType: NavigationType = NavigationType.REPLACE,
        backStackName: String? = null
    )
}

enum class NavigationType {
    ADD, REPLACE
}