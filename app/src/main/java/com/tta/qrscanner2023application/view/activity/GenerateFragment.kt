package com.tta.qrscanner2023application.view.activity

import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.databinding.FragmentGenerateBinding

class GenerateFragment : BaseFragment<FragmentGenerateBinding>() {
    override fun getDataBinding(): FragmentGenerateBinding {
        return FragmentGenerateBinding.inflate(layoutInflater)
    }
}