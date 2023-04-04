package com.example.handtalks.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.handtalks.R
import com.example.handtalks.databinding.ActivityMainBinding
import com.example.handtalks.other.Constants.ARSL
import com.example.handtalks.other.Constants.ASL
import com.example.handtalks.other.languageModels
import com.example.handtalks.other.modelPath
import com.example.handtalks.ui.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val settingViewModel  by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState )
        installSplashScreen()


        binding = ActivityMainBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        //customTheStatusBar()

        navController = findNavController(R.id.navHostFragment)
        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnItemReselectedListener {

        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.appIntro, R.id.chooseLangFragment ->
                    binding.bottomNavigationView.isVisible = false
                else -> binding.bottomNavigationView.isVisible = true
            }
        }

        checkLanguage()

    }

    private fun checkLanguage()  {
        settingViewModel.getLanguage.observe(this) { lang ->
            when (lang) {
                ASL -> {
                    modelPath = languageModels[ASL].toString()
                }
                ARSL -> {
                    modelPath = languageModels[ARSL].toString()
                }
            }
        }
    }

//    private fun customTheStatusBar() {
//        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//    }



}