package com.tta.qrscanner2023application.view.fragment

import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.databinding.FragmentShowQrBinding

class ShowQrFragment : BaseFragment<FragmentShowQrBinding>() {
    override var isTerminalBackKeyActive: Boolean = true
    override fun getDataBinding(): FragmentShowQrBinding {
        return FragmentShowQrBinding.inflate(layoutInflater)
    }
}