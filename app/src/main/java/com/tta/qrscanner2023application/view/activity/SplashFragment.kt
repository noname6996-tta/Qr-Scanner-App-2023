package com.tta.qrscanner2023application.view.activity

import android.os.Handler
import androidx.navigation.fragment.findNavController
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.R
import com.tta.qrscanner2023application.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    override fun getDataBinding(): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(layoutInflater)
    }

    override fun addEvent() {
        super.addEvent()
        val handler = Handler()
        handler.postDelayed({
            // Code to be executed after 2 seconds
            // Write your code here
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }, 2000) // 2000 milliseconds = 2 seconds
    }
}