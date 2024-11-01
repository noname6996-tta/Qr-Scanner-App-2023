package com.tta.qrscanner2023application.view.fragment.setting

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tta.qrscanner2023application.R
import com.tta.qrscanner2023application.data.util.Constants
import com.tta.qrscanner2023application.databinding.FragmentSettingBinding
import com.tta.qrscanner2023application.view.base.BaseFragment
import com.tta.qrscanner2023application.view.fragment.qrscan.QrScanViewModel
import com.tta.qrscanner2023application.view.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    private val qrViewModel: QrScanViewModel by viewModels()
    override var isTerminalBackKeyActive: Boolean = true
    private lateinit var viewModel: SettingViewModel
    private lateinit var viewModelQr: QrScanViewModel
    override fun getDataBinding(): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel = SettingViewModel(requireContext())
        viewModelQr = qrViewModel
        viewModel.getData()
    }

    override fun addObservers() {
        super.addObservers()
        viewModel.soundData.observe(viewLifecycleOwner) {
            binding.soundSwitch.isChecked = it
        }
        viewModel.vibrationData.observe(viewLifecycleOwner) {
            binding.vibrationSwitch.isChecked = it
        }
        viewModel.message.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun addEvent() = with(binding) {
        super.addEvent()
        imgBack.setOnClickListener {
            (requireActivity() as MainActivity).setVisibleBottomBar(false)
            findNavController().popBackStack()
        }
        clSound.setOnClickListener {
            val isChecked = !binding.soundSwitch.isChecked
            viewModel.changeSound(isChecked)
        }
        clVibration.setOnClickListener {
            val isChecked = !binding.vibrationSwitch.isChecked
            viewModel.changeVibration(isChecked)
        }
        clPrivacy.setOnClickListener {
            val url = Constants.PRIVACY_WEB
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        clLanguage.setOnClickListener {
            findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToLanguageFragment())
        }
        clDeleteAll.setOnClickListener {
            viewModelQr.deleteAllQrCode()
            Snackbar.make(binding.root, getString(R.string.all_data_deleted), Snackbar.LENGTH_SHORT).show()
        }
    }
}