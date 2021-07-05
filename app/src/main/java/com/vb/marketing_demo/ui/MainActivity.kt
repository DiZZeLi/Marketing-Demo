package com.vb.marketing_demo.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.vb.marketing_demo.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController


        val appBarConfiguration = AppBarConfiguration
            .Builder(R.id.channelsFragment, R.id.homeFragment, R.id.summaryFragment)
            .build()

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)


    }

    override fun onBackPressed() {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        if (navController.previousBackStackEntry != null) {
            super.onBackPressed()
        } else {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_title_close))
                .setMessage(getString(R.string.dialog_message_close))
                .setPositiveButton(
                    "Yes"
                ) { _, _ -> finish() }
                .setNegativeButton("No", null)
                .show()

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}