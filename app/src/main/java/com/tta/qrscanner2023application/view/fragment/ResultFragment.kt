package com.tta.qrscanner2023application.view.fragment

import android.graphics.Bitmap
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.data.util.copyToClipboard
import com.tta.qrscanner2023application.data.util.generateQrCode
import com.tta.qrscanner2023application.data.util.shareImage
import com.tta.qrscanner2023application.databinding.FragmentResultBinding

class ResultFragment : BaseFragment<FragmentResultBinding>() {
    private val args: ResultFragmentArgs by navArgs()
    private var imageBitmapResoure: Bitmap? = null
    override fun getDataBinding(): FragmentResultBinding {
        return FragmentResultBinding.inflate(layoutInflater)
    }

    override fun initView() = with(binding) {
        super.initView()
        result.text = args.text
        imgQr.setImageBitmap(generateQrCode(args.text))
        imageBitmapResoure = generateQrCode(args.text)
    }

    override fun addEvent() = with(binding) {
        super.addEvent()
        imgBack.setOnClickListener { findNavController().popBackStack() }
        llCopy.setOnClickListener {
            copyToClipboard(
                requireActivity(),
                binding.root,
                binding.result.text.toString()
            )
        }
        llShare.setOnClickListener {
            if (imageBitmapResoure != null) {
                shareImage(requireActivity(), imageBitmapResoure!!)
            }
        }
        viewShowQr.setOnClickListener {
            checkImgVis()
        }
    }

    private fun checkImgVis() = with(binding){
        if (imgQr.visibility == View.VISIBLE) {
            imgQr.visibility = View.GONE
            tvShowQr.text = "Show Qr code"
        } else {
            imgQr.visibility = View.VISIBLE
            tvShowQr.text = "Hode Qr code"
        }
    }
}