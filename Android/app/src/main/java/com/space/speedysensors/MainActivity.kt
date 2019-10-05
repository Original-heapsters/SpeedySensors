package com.space.speedysensors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        private val TAG: String = MainActivity::class.java.simpleName
    }

    private val navListener = object : NavController.OnDestinationChangedListener {
        override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
            when (destination.label) {
                "fragment_main" -> {

                }
                "fragment_login" -> {

                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navController = findNavController(R.id.navHostFragment)
        setupActionBarWithNavController(navController)

        navController.addOnDestinationChangedListener(navListener)
    }

}
