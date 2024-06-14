package com.tta.qrscanner2023application.view.fragment.history

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.data.model.QrCodeEntity
import com.tta.qrscanner2023application.data.model.TypeCode
import com.tta.qrscanner2023application.databinding.FragmentListHistoryBinding
import com.tta.qrscanner2023application.view.fragment.qrscan.QrScanViewModel
import com.tta.qrscanner2023application.view.main.MainActivity


class ListHistoryScanFragment(type: Int) : BaseFragment<FragmentListHistoryBinding>() {
    private var historyAdapter = HistoryAdapter()
    private var list = ArrayList<QrCodeEntity>()
    override var isTerminalBackKeyActive: Boolean = false
    private lateinit var viewModel: QrScanViewModel
    override fun getDataBinding(): FragmentListHistoryBinding {
        return FragmentListHistoryBinding.inflate(layoutInflater)
    }

    override fun addObservers() {
        super.addObservers()
        viewModel.listQrCodeScan.observe(viewLifecycleOwner) {
            list.clear()
            list.addAll(it)
            historyAdapter.setListHistory(list, requireContext())
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.qrCodeEntity.observe(viewLifecycleOwner) {
            findNavController().navigate(
                HistoryFragmentDirections.actionHistoryFragmentToShowQrFragment(
                    it.code
                )
            )
        }
    }

    override fun initView() {
        super.initView()
        binding.recHistory.run {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = HistoryAdapter().also {
                historyAdapter = it
            }
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel = (requireActivity() as MainActivity).qrViewModel
        viewModel.getListQrByType(TypeCode.SCAN)
    }

    override fun addEvent() {
        super.addEvent()
        historyAdapter.deleteItem {
            viewModel.deleteQrCode(TypeCode.SCAN, list[it].id)
        }
        historyAdapter.showInfo {
            viewModel.getInfoById(list[it].id)
        }
    }
}