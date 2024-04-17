package com.tta.qrscanner2023application.view.fragment.history

import android.widget.Toast
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.databinding.FragmentListHistoryBinding

class ListHistoryFragment (type : Int) : BaseFragment<FragmentListHistoryBinding>() {
    private val type = type
    override var isTerminalBackKeyActive: Boolean = false
    override fun getDataBinding(): FragmentListHistoryBinding {
        return FragmentListHistoryBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()

    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(requireContext(), type.toString(), Toast.LENGTH_LONG).show()
    }
}