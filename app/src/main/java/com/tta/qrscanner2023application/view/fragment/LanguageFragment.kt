package com.tta.qrscanner2023application.view.fragment

import androidx.navigation.fragment.findNavController
import com.tta.qrscanner2023application.view.base.BaseFragment
import com.tta.qrscanner2023application.databinding.FragmentLanguageBinding
import com.tta.qrscanner2023application.view.fragment.setting.SettingViewModel
import com.tta.qrscanner2023application.view.fragment.setting.language.LanguageHelper
import com.tta.qrscanner2023application.view.main.MainActivity
import java.util.Locale

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
//            (requireActivity() as MainActivity).changeLanguage(Locale.ENGLISH.language)
//            viewModel.changeLanguage(Locale.ENGLISH.language)
            LanguageHelper.setNewLocale(requireActivity(),"en")
            findNavController().popBackStack()
        }
        clVietNamese.setOnClickListener {
//            (requireActivity() as MainActivity).changeLanguage("vi")
//            viewModel.changeLanguage("vi")
            LanguageHelper.setNewLocale(requireActivity(),"vi")

            findNavController().popBackStack()
        }
        imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}