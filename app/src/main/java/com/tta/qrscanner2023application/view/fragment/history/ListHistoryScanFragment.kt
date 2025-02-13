package com.tta.qrscanner2023application.view.fragment.history

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tta.qrscanner2023application.R
import com.tta.qrscanner2023application.data.model.QrCodeEntity
import com.tta.qrscanner2023application.data.model.TypeCode
import com.tta.qrscanner2023application.databinding.FragmentListHistoryBinding
import com.tta.qrscanner2023application.view.base.BaseFragment
import com.tta.qrscanner2023application.view.fragment.CoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListHistoryScanFragment : BaseFragment<FragmentListHistoryBinding>() {
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
        viewModel.listQrCodeScan.observe(viewLifecycleOwner) {
            list.clear()
            list.addAll(it)
            historyAdapter.setListHistory(list, requireContext())
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
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
        viewModel.getListQrByType(TypeCode.SCAN)
    }

    override fun addEvent() {
        super.addEvent()
        historyAdapter.deleteItem {
            viewModel.deleteQrCode(TypeCode.SCAN, list[it].id)
        }
        historyAdapter.showInfo {
            lifecycleScope.launch {
                val qrInfo = viewModel.getInfoById(list[it].id)
                findNavController().navigate(
                    HistoryFragmentDirections.actionHistoryFragmentToResultFragment(
                        qrInfo?.code ?: getString(R.string.not_found_qr), qrInfo?.createdTime?.time ?: 0L
                    )
                )
            }
        }
    }
}