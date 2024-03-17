package com.tta.qrscanner2023application.view.fragment

import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.databinding.FragmentGenerateCodeBinding

class GenerateCodeFragment : BaseFragment<FragmentGenerateCodeBinding>() {
    override fun getDataBinding(): FragmentGenerateCodeBinding {
        return FragmentGenerateCodeBinding.inflate(layoutInflater)
    }
}