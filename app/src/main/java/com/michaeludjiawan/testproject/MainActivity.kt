package com.michaeludjiawan.testproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar_main)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.main_nav_host_fragment).navigateUp()
}