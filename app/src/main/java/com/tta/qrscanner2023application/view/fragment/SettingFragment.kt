package com.tta.qrscanner2023application.view.fragment

import androidx.navigation.fragment.findNavController
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.databinding.FragmentSettingBinding
import com.tta.qrscanner2023application.view.main.MainActivity

class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    override var isTerminalBackKeyActive: Boolean = true
    override fun getDataBinding(): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(layoutInflater)
    }

    override fun addEvent() = with(binding){
        super.addEvent()
        imgBack.setOnClickListener {
            (requireActivity() as MainActivity).setVisibleBottomBar(false)
            findNavController().popBackStack()
        }
    }
}