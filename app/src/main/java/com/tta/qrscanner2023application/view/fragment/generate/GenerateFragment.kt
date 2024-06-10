package com.tta.qrscanner2023application.view.fragment.generate

import androidx.navigation.fragment.findNavController
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.R
import com.tta.qrscanner2023application.data.util.QRCode
import com.tta.qrscanner2023application.databinding.FragmentGenerateBinding

class GenerateFragment : BaseFragment<FragmentGenerateBinding>() {
    override var isTerminalBackKeyActive: Boolean = false

    override fun getDataBinding(): FragmentGenerateBinding {
        return FragmentGenerateBinding.inflate(layoutInflater)
    }

    override fun initView() = with(binding) {
        super.initView()
        itemText.textView.text = getString(R.string.text)
        itemPhone.textView.text = getString(R.string.phone)
        itemWebsite.textView.text = getString(R.string.website)
        itemFacebook.textView.text = getString(R.string.facebook)
        itemInstagram.textView.text = getString(R.string.instagram)
        itemX.textView.text = getString(R.string.x)
        itemWifiPass.textView.text = getString(R.string.wifi)

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
            findNavController().navigate(
                GenerateFragmentDirections.actionGenerateFragmentToGenerateCodeFragment(
                    QRCode.TEXT.type
                )
            )
        }
        itemPhone.root.setOnClickListener {
            findNavController().navigate(
                GenerateFragmentDirections.actionGenerateFragmentToGenerateCodeFragment(
                    QRCode.PHONE.type
                )
            )
        }
        itemWebsite.root.setOnClickListener {
            findNavController().navigate(
                GenerateFragmentDirections.actionGenerateFragmentToGenerateCodeFragment(
                    QRCode.WEB.type
                )
            )
        }
        itemFacebook.root.setOnClickListener {
            findNavController().navigate(
                GenerateFragmentDirections.actionGenerateFragmentToGenerateCodeFragment(
                    QRCode.FACEBOOK.type
                )
            )
        }
        itemInstagram.root.setOnClickListener {
            findNavController().navigate(
                GenerateFragmentDirections.actionGenerateFragmentToGenerateCodeFragment(
                    QRCode.INSTAGRAM.type
                )
            )
        }
        itemX.root.setOnClickListener {
            findNavController().navigate(
                GenerateFragmentDirections.actionGenerateFragmentToGenerateCodeFragment(
                    QRCode.TWITTER.type
                )
            )
        }
        itemWifiPass.root.setOnClickListener {
            findNavController().navigate(
                GenerateFragmentDirections.actionGenerateFragmentToGenerateCodeFragment(
                    QRCode.WIFI.type
                )
            )
        }
    }
}