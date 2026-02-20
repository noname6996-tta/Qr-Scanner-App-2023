package com.tta.qrscanner2023application.view.fragment.history

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tta.qrscanner2023application.R
import com.tta.qrscanner2023application.data.model.QrCodeEntity
import com.tta.qrscanner2023application.data.model.TypeCode
import com.tta.qrscanner2023application.data.util.DialogUtil
import com.tta.qrscanner2023application.databinding.FragmentListHistoryBinding
import com.tta.qrscanner2023application.view.base.BaseFragment
import com.tta.qrscanner2023application.view.fragment.CoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
            DialogUtil.showAlertDialog(
                context = requireContext(),
                title = getString(R.string.alert),
                message = getString(R.string.delete_all_warn_title),
                positiveText = getString(R.string.delete),
                negativeText = getString(R.string.cancel),
                onPositiveClick = {
                    viewModel.deleteQrCode(TypeCode.CREATED, list[it].id)
                    Snackbar.make(binding.root, getString(R.string.delete_success), Snackbar.LENGTH_SHORT)
                        .show()
                },
                onNegativeClick = {

                }
            )
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