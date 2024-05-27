package com.tta.qrscanner2023application.view.fragment

import android.text.InputType
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.data.util.QRCode
import com.tta.qrscanner2023application.data.util.QRForm
import com.tta.qrscanner2023application.databinding.FragmentGenerateCodeBinding
import com.tta.qrscanner2023application.view.main.MainActivity

class GenerateCodeFragment : BaseFragment<FragmentGenerateCodeBinding>() {
    override var isTerminalBackKeyActive: Boolean = true
    private val args: GenerateCodeFragmentArgs by navArgs()
    private var result = ""
    override fun getDataBinding(): FragmentGenerateCodeBinding {
        return FragmentGenerateCodeBinding.inflate(layoutInflater)
    }

    override fun initView() = with(binding) {
        super.initView()
        (requireActivity() as MainActivity).setVisibleBottomBar(false)
        val type = args.type
        textView3.text = QRCode.getByType(type).text
        imageView3.setImageResource(QRCode.getByType(type).icon)
        if (type == QRCode.MAIL.type) {
            edtText2.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }
        if (type == QRCode.WIFI.type) {
            textView2.isVisible = true
            edtText1.isVisible = true
            textView2.text = "Network"
            textView3.text = "Password"
        }
    }

    override fun addEvent() = with(binding) {
        super.addEvent()
        imgBack.setOnClickListener {
            (requireActivity() as MainActivity).setVisibleBottomBar(true)
            findNavController().popBackStack()
        }
        btnGenerate.setOnClickListener {
            when (args.type) {
                QRCode.TEXT.type -> {
                    result = edtText2.text.toString()
                }

                QRCode.MAIL.type -> {
                    result =
                        QRForm.EMAIL_TEMPLATE.replace("[EMAIL_ADDRESS]", edtText2.text.toString())
                }

                QRCode.PHONE.type -> {
                    result =
                        QRForm.PHONE_TEMPLATE.replace("[PHONE_NUMBER]", edtText2.text.toString())
                }

                QRCode.MAIL.type -> {
                    result = edtText2.text.toString()
                }

                QRCode.WIFI.type -> {

                }

                QRCode.FACEBOOK.type -> {

                }

                QRCode.INSTAGRAM.type -> {

                }

                QRCode.TWITTER.type -> {

                }
            }
        }
    }
}