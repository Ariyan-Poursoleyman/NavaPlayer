package com.navaplayer.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.navaplayer.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.navaplayer.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
        setSupportActionBar(dataBinding.mainToolbar)
        supportActionBar?.title = ""
        drawerToggle = ActionBarDrawerToggle(
            this,
            dataBinding.mainDrawerLayout,
            dataBinding.mainToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        dataBinding.mainDrawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        dataBinding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.setting -> {
                    Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show()
                    dataBinding.mainDrawerLayout.closeDrawers()

                }
                R.id.policies -> {
                    val intent =  Intent(Intent.ACTION_VIEW)
                    intent.data=Uri.parse("https://www.lipsum.com/feed/html")
                    startActivity(intent)
                    dataBinding.mainDrawerLayout.closeDrawers()

                }
                R.id.donate -> {
                    Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show()
                    dataBinding.mainDrawerLayout.closeDrawers()

                }
                R.id.score -> {
                    Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show()
                    dataBinding.mainDrawerLayout.closeDrawers()

                }
                R.id.contactUs -> {
                    val intent = Intent(Intent.ACTION_SENDTO)
                    intent.data = Uri.parse("mailto:Ariyan.poursoleyman@gmail.com")
                    startActivity(intent)
                    dataBinding.mainDrawerLayout.closeDrawers()

                }
                R.id.about -> {
                    val intent =  Intent(Intent.ACTION_VIEW)
                    intent.data=Uri.parse("https://www.lipsum.com/feed/html")
                    startActivity(intent)
                    dataBinding.mainDrawerLayout.closeDrawers()

                }
                R.id.exit -> {
                    exitDialog()
                    dataBinding.mainDrawerLayout.closeDrawers()

                }

            }
            true
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        dataBinding.bottomNav.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.favoriteFragment, R.id.searchFragment),
            dataBinding.mainDrawerLayout
        )


    }

    override fun onBackPressed() {
        val currentDestination = navController.currentDestination?.id
        if (dataBinding.mainDrawerLayout.isDrawerOpen(androidx.core.view.GravityCompat.START)) {
            dataBinding.mainDrawerLayout.closeDrawer(androidx.core.view.GravityCompat.START)
        } else {
            if(currentDestination == R.id.homeFragment){
                exitDialog()
            }else{
                super.onBackPressed()
            }

        }
    }

    private fun exitDialog() {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Exit")
            .setIcon(R.drawable.baseline_exit_to_app_24)
            .setMessage("Are you sure?")
            .setPositiveButton("Yes") { _, _ ->
                finishAffinity()
            }
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()
        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            ?.setTextColor(ContextCompat.getColor(this, R.color.white))

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            ?.setTextColor(ContextCompat.getColor(this, R.color.white))
    }

}

