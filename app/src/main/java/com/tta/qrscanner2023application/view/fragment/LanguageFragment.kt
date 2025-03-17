package com.tta.qrscanner2023application.view.fragment

import androidx.navigation.fragment.findNavController
import com.tta.qrscanner2023application.databinding.FragmentLanguageBinding
import com.tta.qrscanner2023application.view.base.BaseFragment
import com.tta.qrscanner2023application.view.fragment.setting.SettingViewModel
import com.tta.qrscanner2023application.view.fragment.setting.language.LanguageHelper

class LanguageFragment : BaseFragment<FragmentLanguageBinding>() {
    override var isTerminalBackKeyActive: Boolean = true
    private lateinit var viewModel: SettingViewModel

    override fun getDataBinding(): FragmentLanguageBinding {
        return FragmentLanguageBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel = SettingViewModel(requireContext())
    }

    override fun addEvent() = with(binding) {
        super.addEvent()
        clEnglish.setOnClickListener {
            LanguageHelper.setNewLocale(requireActivity(), "en")
            requireActivity().recreate()
            findNavController().popBackStack()
        }
        clVietNamese.setOnClickListener {
            LanguageHelper.setNewLocale(requireActivity(), "vi")
            requireActivity().recreate()
            findNavController().popBackStack()
        }
        imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}