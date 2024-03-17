package com.tta.qrscanner2023application

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.tta.fitnessapplication.view.base.BaseActivity
import com.tta.qrscanner2023application.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val navController = findNavController(R.id.nav_host_fragment)
    override fun getDataBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        when (navController.currentDestination?.id) {
            R.id.qrScanFragment, R.id.generateFragment, R.id.historyFragment, R.id.resultFragment, R.id.showQrFragment -> {
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
        llHistory.setOnClickListener{

        }
        imgToHome.setOnClickListener {
            toHome()
        }
    }

    private fun goToGenerateQr(){
        when (navController.currentDestination?.id) {
            R.id.qrScanFragment -> {
                navController.navigate(R.id.action_qrScanFragment_to_generateFragment)
            }
            R.id.historyFragment -> {
                navController.navigate(R.id.action_historyFragment_to_generateFragment)
            }
            R.id.historyFragment -> {
                navController.navigate(R.id.action_historyFragment_to_generateFragment)
            }
            else -> {
            }
        }

        if (navController.currentDestination?.id==R.id.generateFragment){
            binding.tvGenerateQr.setTextColor(Color.YELLOW)
        } else binding.tvGenerateQr.setTextColor(Color.WHITE)
    }

    private fun toHome(){
        val navController = findNavController(R.id.nav_host_fragment)
        when (navController.currentDestination?.id) {
            R.id.generateFragment -> {
                navController.popBackStack(R.id.qrScanFragment, false)
            }
            R.id.generateFragment -> {
                binding.tvGenerateQr.setTextColor(Color.YELLOW)
            }
            else -> {
            }
        }
    }
}