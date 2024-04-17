package com.tta.qrscanner2023application.view.fragment.history

import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.databinding.FragmentListHistoryBinding

class ListHistoryFragment (type : Int) : BaseFragment<FragmentListHistoryBinding>() {
    override var isTerminalBackKeyActive: Boolean = false
    override fun getDataBinding(): FragmentListHistoryBinding {
        return FragmentListHistoryBinding.inflate(layoutInflater)
    }
}