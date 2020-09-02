package com.michaeludjiawan.testproject.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.michaeludjiawan.testproject.R
import com.michaeludjiawan.testproject.ui.home.HomeFragment
import com.michaeludjiawan.testproject.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private val homeFragment by lazy { HomeFragment() }
    private val profileFragment by lazy { ProfileFragment() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()
        initBottomNavListeners()
    }

    private fun initViewPager() {
        val adapter = MainViewPagerAdapter(childFragmentManager, lifecycle)
        adapter.addFragment(homeFragment)
        adapter.addFragment(profileFragment)
        vp_main.adapter = adapter

        vp_main.isUserInputEnabled = false
    }

    private fun initBottomNavListeners() {
        bottom_nav_main.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    vp_main.currentItem = 0
                }
                R.id.action_profile -> {
                    vp_main.currentItem = 1
                }
            }

            true
        }
    }

    class MainViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

        private val fragments = ArrayList<Fragment>()

        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position]

        fun addFragment(fragment: Fragment) {
            fragments.add(fragment)
        }
    }
}