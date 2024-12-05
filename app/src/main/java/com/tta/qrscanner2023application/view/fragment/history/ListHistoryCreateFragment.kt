package com.tta.qrscanner2023application.view.fragment.history

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tta.qrscanner2023application.data.model.QrCodeEntity
import com.tta.qrscanner2023application.data.model.TypeCode
import com.tta.qrscanner2023application.databinding.FragmentListHistoryBinding
import com.tta.qrscanner2023application.view.base.BaseFragment
import com.tta.qrscanner2023application.view.fragment.qrscan.CoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListHistoryCreateFragment : BaseFragment<FragmentListHistoryBinding>() {
    private val qrViewModel: CoreViewModel by viewModels()
    private var historyAdapter = HistoryAdapter()
    private var list = ArrayList<QrCodeEntity>()
    override var isTerminalBackKeyActive: Boolean = false
    private lateinit var viewModel: CoreViewModel
    override fun getDataBinding(): FragmentListHistoryBinding {
        return FragmentListHistoryBinding.inflate(layoutInflater)
    }

    override fun addObservers() {
        super.addObservers()
        viewModel.listQrCodeCreate.observe(viewLifecycleOwner) {
            list.clear()
            list.addAll(it)
            historyAdapter.setListHistory(list, requireContext())
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.qrCodeEntity.observe(viewLifecycleOwner) {
            findNavController().navigate(
                HistoryFragmentDirections.actionHistoryFragmentToResultFragment(
                    it.code,it.createdTime.time
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
        viewModel = qrViewModel
        viewModel.getListQrByType(TypeCode.CREATED)
    }

    override fun addEvent() {
        super.addEvent()
        historyAdapter.deleteItem {
            viewModel.deleteQrCode(TypeCode.CREATED, list[it].id)
        }
        historyAdapter.showInfo {
            viewModel.getInfoById(list[it].id)
        }

    }
}