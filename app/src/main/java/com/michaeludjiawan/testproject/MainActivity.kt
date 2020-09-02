package com.michaeludjiawan.testproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.michaeludjiawan.testproject.ui.MainFragment
import com.michaeludjiawan.testproject.ui.NavigationController
import com.michaeludjiawan.testproject.ui.NavigationType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main), NavigationController {

    private fun getParentLayoutId(): Int = R.id.fl_main_host
    private fun getCurrentFragment() = supportFragmentManager.findFragmentById(getParentLayoutId())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar_main)

        navigateToPage(MainFragment(), false)
    }

    override fun navigateToPage(
        fragment: Fragment,
        addToBackStack: Boolean,
        navigationType: NavigationType,
        backStackName: String?
    ) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        val currentFragment = getCurrentFragment()

        if (navigationType == NavigationType.ADD && currentFragment != null) {
            fragmentTransaction.hide(currentFragment)
            fragmentTransaction.add(getParentLayoutId(), fragment)
        } else {
            fragmentTransaction.replace(getParentLayoutId(), fragment)
        }

        if (addToBackStack) fragmentTransaction.addToBackStack(backStackName)

        fragmentTransaction.commit()
    }

}