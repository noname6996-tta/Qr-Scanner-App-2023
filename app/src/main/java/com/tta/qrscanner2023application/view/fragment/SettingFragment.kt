package com.tta.qrscanner2023application.view.fragment

import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.databinding.FragmentSettingBinding
import com.tta.qrscanner2023application.view.main.MainActivity

class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    override var isTerminalBackKeyActive: Boolean = true
    private lateinit var viewModel: SettingViewModel
    override fun getDataBinding(): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel = SettingViewModel(requireContext())
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
    }
}