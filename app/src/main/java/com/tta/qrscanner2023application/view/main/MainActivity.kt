package com.tta.qrscanner2023application.view.main

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.tta.qrscanner2023application.view.base.BaseActivity
import com.tta.qrscanner2023application.R
import com.tta.qrscanner2023application.databinding.ActivityMainBinding
import com.tta.qrscanner2023application.view.fragment.setting.language.LanguageHelper
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    override fun getDataBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        when (navController.currentDestination?.id) {
            R.id.qrScanFragment, R.id.generateFragment, R.id.historyFragment, R.id.resultFragment -> {
                binding.imgToHome.visibility = View.VISIBLE
                binding.cardView.visibility = View.VISIBLE
            }

            else -> {
                binding.imgToHome.visibility = View.GONE
                binding.cardView.visibility = View.GONE
            }
        }
    }

    override fun addEvent() = with(binding) {
        super.addEvent()
        llGenerateQr.setOnClickListener {
            goToGenerateQr()
        }
        llHistory.setOnClickListener {
            toHistory()
        }
        imgToHome.setOnClickListener {
            toHome()
        }
    }

    private fun goToGenerateQr() {
        when (navController.currentDestination?.id) {
            R.id.qrScanFragment -> {
                navController.navigate(R.id.action_qrScanFragment_to_generateFragment)
            }

            R.id.historyFragment -> {
                navController.navigate(R.id.action_historyFragment_to_generateFragment)
            }

            R.id.resultFragment -> {
                navController.navigate(R.id.action_resultFragment_to_generateFragment)
            }

            else -> {
            }
        }
    }

    private fun toHome() {
        when (navController.currentDestination?.id) {
            R.id.generateFragment -> {
                navController.popBackStack(R.id.qrScanFragment, false)
            }

            R.id.resultFragment -> {
                navController.navigate(R.id.action_resultFragment_to_qrScanFragment)
            }

            R.id.historyFragment -> {
                navController.navigate(R.id.action_historyFragment_to_qrScanFragment)
            }

            else -> {
            }
        }
    }

    private fun toHistory() {
        when (navController.currentDestination?.id) {
            R.id.qrScanFragment -> {
                navController.navigate(R.id.action_qrScanFragment_to_historyFragment)
            }

            R.id.generateFragment -> {
                navController.navigate(R.id.action_generateFragment_to_historyFragment)
            }

            R.id.resultFragment -> {
                navController.navigate(R.id.action_resultFragment_to_historyFragment)
            }

            else -> {
            }
        }
    }

    fun setVisibleBottomBar(visible: Boolean) {
        binding.imgToHome.isVisible = visible
        binding.cardView.isVisible = visible
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun attachBaseContext(base: Context?) {
        base?.let {
            val currentLanguage = LanguageHelper.getLanguagePref(it)
            if (it.resources.configuration.locales[0].language != currentLanguage) {
                super.attachBaseContext(
                    LanguageHelper.setNewLocale(it, currentLanguage)
                )
            } else super.attachBaseContext(base)
        }
        if (base == null) super.attachBaseContext(base)
    }
}