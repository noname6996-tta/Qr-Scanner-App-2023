package com.tta.qrscanner2023application.view.fragment

import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.databinding.FragmentHistoryBinding

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {
    override fun getDataBinding(): FragmentHistoryBinding {
        return FragmentHistoryBinding.inflate(layoutInflater)
    }
}