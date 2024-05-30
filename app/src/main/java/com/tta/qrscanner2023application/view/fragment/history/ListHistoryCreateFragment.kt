package com.tta.qrscanner2023application.view.fragment.history

import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.data.model.TypeCode
import com.tta.qrscanner2023application.databinding.FragmentListHistoryBinding
import com.tta.qrscanner2023application.view.fragment.qrscan.QrScanViewModel
import com.tta.qrscanner2023application.view.main.MainActivity

class ListHistoryCreateFragment(type: Int) : BaseFragment<FragmentListHistoryBinding>() {
    private var historyAdapter = HistoryAdapter()
    override var isTerminalBackKeyActive: Boolean = false
    private lateinit var viewModel: QrScanViewModel
    override fun getDataBinding(): FragmentListHistoryBinding {
        return FragmentListHistoryBinding.inflate(layoutInflater)
    }

    override fun addObservers() {
        super.addObservers()
        viewModel.listQrCodeCreate.observe(viewLifecycleOwner) {
            historyAdapter.setListHistory(it, requireContext())
        }
        viewModel.deleteCreateQrCode.observe(viewLifecycleOwner) {
            if (it){
                viewModel.getListQrByType(TypeCode.CREATED)
                Toast.makeText(requireContext(), "Xóa thành công", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show()
            }
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
        viewModel.getListQrByType(TypeCode.CREATED)
    }

    override fun addEvent() {
        super.addEvent()
        historyAdapter.deleteItem {
            viewModel.deleteQrCode(TypeCode.CREATED,it)
        }
    }
}