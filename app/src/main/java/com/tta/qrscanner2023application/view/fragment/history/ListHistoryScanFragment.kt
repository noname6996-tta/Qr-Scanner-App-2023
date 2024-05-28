package com.tta.qrscanner2023application.view.fragment.history

import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.databinding.FragmentListHistoryBinding
import com.tta.qrscanner2023application.view.fragment.qrscan.QrScanViewModel
import com.tta.qrscanner2023application.view.main.MainActivity


class ListHistoryScanFragment (type : Int) : BaseFragment<FragmentListHistoryBinding>() {
    private val type = type
    private var listScan = ArrayList<String>()
    override var isTerminalBackKeyActive: Boolean = false
    private lateinit var viewModel: QrScanViewModel
    override fun getDataBinding(): FragmentListHistoryBinding {
        return FragmentListHistoryBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()

    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel = (requireActivity() as MainActivity).qrViewModel
//        binding.recHistory.run {
//            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
//            adapter = PointHistoryAdapter().also {
//                pointAdapter = it
//            }
//        }
//        adapter.submitList(userViewModel.getAllUsers())
    }
}