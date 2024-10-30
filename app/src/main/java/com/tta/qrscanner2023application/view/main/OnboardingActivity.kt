package com.tta.qrscanner2023application.view.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.limurse.onboard.OnboardAdvanced
import com.limurse.onboard.OnboardFragment
import com.tta.qrscanner2023application.R

class OnboardingActivity : OnboardAdvanced() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("OnboardingPrefs", Context.MODE_PRIVATE)
        val onboardingCompleted = sharedPreferences.getBoolean("onboarding_completed", false)

        if (!onboardingCompleted) {
            addSlide(
                OnboardFragment.newInstance(
                    title = "QrScanner2023",
                    description = "Go and enjoy our features for free and make your life easy with us.",
                    resourceId = R.drawable.icon_onboarding,
                    backgroundDrawable = R.drawable.bg_onboarding2,
                    titleColor = Color.WHITE,
                    descriptionColor = Color.WHITE,
                )
            )
            addSlide(
                OnboardFragment.newInstance(
                    title = "QrScanner2023",
                    description = "Go and enjoy our features for free and make your life easy with us.",
                    resourceId = R.drawable.ic_onboarding2,
                    backgroundDrawable = R.drawable.bg_onboarding,
                    titleColor = Color.WHITE,
                    descriptionColor = Color.WHITE,
                )
            )
            addSlide(
                OnboardFragment.newInstance(
                    title = "QrScanner2023",
                    description = "Go and enjoy our features for free and make your life easy with us.",
                    resourceId = R.drawable.icon_dark_theme,
                    backgroundDrawable = R.drawable.bg_onboarding,
                    titleColor = Color.WHITE,
                    descriptionColor = Color.WHITE,
                )
            )
            val editor = sharedPreferences.edit()
            editor.putBoolean("onboarding_completed", true)
            editor.apply()
        } else {
            startActivity(Intent(this@OnboardingActivity,MainActivity::class.java))
            finish()
        }
    }
    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}