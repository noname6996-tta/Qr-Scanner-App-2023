package com.tta.qrscanner2023application.view.fragment

import androidx.navigation.fragment.findNavController
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.databinding.FragmentGenerateBinding

class GenerateFragment : BaseFragment<FragmentGenerateBinding>() {
    override var isTerminalBackKeyActive: Boolean = false

    override fun getDataBinding(): FragmentGenerateBinding {
        return FragmentGenerateBinding.inflate(layoutInflater)
    }

    override fun initView() = with(binding) {
        super.initView()
        itemText.textView.text = "Text"
        itemPhone.textView.text = "Phone"
        itemWebsite.textView.text = "Website"
        itemFacebook.textView.text = "Facebook"
        itemInstagram.textView.text = "Instagram"
        itemX.textView.text = "X"
        itemWifiPass.textView.text = "Wifi"

        // mai cho anhr vào sau
    }

    override fun addEvent() = with(binding) {
        super.addEvent()
        imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        itemText.root.setOnClickListener {

        }
        itemPhone.root.setOnClickListener {

        }
        itemWebsite.root.setOnClickListener {

        }
        itemFacebook.root.setOnClickListener {

        }
        itemInstagram.root.setOnClickListener {

        }
        itemX.root.setOnClickListener {

        }
        itemWifiPass.root.setOnClickListener {

        }
    }
}