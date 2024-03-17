package com.tta.qrscanner2023application.view.fragment

import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.databinding.FragmentResultBinding

class ResultFragment : BaseFragment<FragmentResultBinding>() {
    override fun getDataBinding(): FragmentResultBinding {
        return FragmentResultBinding.inflate(layoutInflater)
    }
}