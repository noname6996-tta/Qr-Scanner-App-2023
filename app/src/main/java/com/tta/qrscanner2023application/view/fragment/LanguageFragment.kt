package com.tta.qrscanner2023application.view.fragment

import androidx.navigation.fragment.findNavController
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.databinding.FragmentLanguageBinding
import com.tta.qrscanner2023application.view.main.MainActivity
import java.util.Locale

class LanguageFragment : BaseFragment<FragmentLanguageBinding>() {
    override var isTerminalBackKeyActive: Boolean = true

    override fun getDataBinding(): FragmentLanguageBinding {
        return FragmentLanguageBinding.inflate(layoutInflater)
    }

    override fun addEvent() = with(binding) {
        super.addEvent()
        clEnglish.setOnClickListener {
            (requireActivity() as MainActivity).changeLanguage(Locale.ENGLISH.language)
        }
        clVietNamese.setOnClickListener {
            (requireActivity() as MainActivity).changeLanguage("vi")
            findNavController().popBackStack()
        }
        imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}