package com.tta.qrscanner2023application.view.fragment

import androidx.navigation.fragment.findNavController
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.R
import com.tta.qrscanner2023application.data.util.QRCode
import com.tta.qrscanner2023application.databinding.FragmentGenerateBinding
import com.tta.qrscanner2023application.view.main.MainActivity

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

        itemText.image.setImageResource(R.drawable.ic_text)
        itemPhone.image.setImageResource(R.drawable.ic_phone)
        itemWebsite.image.setImageResource(R.drawable.ic_web)
        itemFacebook.image.setImageResource(R.drawable.ic_facebook)
        itemInstagram.image.setImageResource(R.drawable.ic_instagram)
        itemX.image.setImageResource(R.drawable.ic_x)
        itemWifiPass.image.setImageResource(R.drawable.ic_wifi)
    }

    override fun addEvent() = with(binding) {
        super.addEvent()
        itemText.root.setOnClickListener {
            findNavController().navigate(GenerateFragmentDirections.actionGenerateFragmentToGenerateCodeFragment(QRCode.TEXT.type))
        }
        itemPhone.root.setOnClickListener {
            findNavController().navigate(GenerateFragmentDirections.actionGenerateFragmentToGenerateCodeFragment(QRCode.PHONE.type))
        }
        itemWebsite.root.setOnClickListener {
            findNavController().navigate(GenerateFragmentDirections.actionGenerateFragmentToGenerateCodeFragment(QRCode.WEB.type))
        }
        itemFacebook.root.setOnClickListener {
            findNavController().navigate(GenerateFragmentDirections.actionGenerateFragmentToGenerateCodeFragment(QRCode.FACEBOOK.type))
        }
        itemInstagram.root.setOnClickListener {
            findNavController().navigate(GenerateFragmentDirections.actionGenerateFragmentToGenerateCodeFragment(QRCode.INSTAGRAM.type))
        }
        itemX.root.setOnClickListener {
            findNavController().navigate(GenerateFragmentDirections.actionGenerateFragmentToGenerateCodeFragment(QRCode.TWITTER.type))
        }
        itemWifiPass.root.setOnClickListener {
            findNavController().navigate(GenerateFragmentDirections.actionGenerateFragmentToGenerateCodeFragment(QRCode.WIFI.type))
        }
    }
}