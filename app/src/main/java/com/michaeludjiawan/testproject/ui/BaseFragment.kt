package com.michaeludjiawan.testproject.ui

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseFragment(layoutResId: Int) : Fragment(layoutResId) {

    fun findNavController(): NavigationController = activity as NavigationController

    protected fun initDefaultToolbar(
        toolbarTitle: String = "",
        showBackButton: Boolean = true,
        homeAsUpIndicator: Int = 0
    ) {
        setHasOptionsMenu(true)

        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            if (toolbarTitle.isBlank()) {
                setDisplayShowTitleEnabled(false)
            } else {
                setDisplayShowTitleEnabled(true)
                title = toolbarTitle
            }

            setHomeAsUpIndicator(homeAsUpIndicator)
            setDisplayHomeAsUpEnabled(showBackButton)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}