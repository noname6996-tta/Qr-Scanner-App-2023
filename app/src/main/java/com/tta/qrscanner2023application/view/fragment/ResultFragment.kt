package com.tta.qrscanner2023application.view.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.R
import com.tta.qrscanner2023application.data.util.copyToClipboard
import com.tta.qrscanner2023application.data.util.generateQrCode
import com.tta.qrscanner2023application.data.util.isWebLinkOrAppLink
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
        setViewActionQr(args.text)
    }

    override fun addEvent() = with(binding) {
        super.addEvent()
        imgBack.setOnClickListener { findNavController().popBackStack() }
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

    private fun setViewActionQr(result : String) = with(binding){
        with(llAction){

            /* copy action */
            actionCopy.tvTittle.text = "Copy"
            actionCopy.imgIcon.setImageResource(R.drawable.ic_copy)
            actionCopy.llItem.setOnClickListener {
                copyToClipboard(
                    requireActivity(),
                    binding.root,
                    result
                )
            }

            /* share action */
            actionShare.tvTittle.text = "Share"
            actionShare.imgIcon.setImageResource(R.drawable.ic_share)
            actionShare.llItem.setOnClickListener {
                if (imageBitmapResoure != null) {
                    shareImage(requireActivity(), imageBitmapResoure!!)
                }
            }

            /* open action */
            if (isWebLinkOrAppLink(result)) {
                actionOpen.root.visibility = View.VISIBLE
            } else {
                actionOpen.root.visibility = View.GONE
            }

            actionOpen.tvTittle.text = "Open"
            actionOpen.imgIcon.setImageResource(R.drawable.qr_background)
            actionOpen.llItem.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(result))
                startActivity(intent)
            }

            /* save action */
            actionSave.tvTittle.text = "Save"
            actionSave.imgIcon.setImageResource(R.drawable.ic_save)
            actionSave.llItem.setOnClickListener {

            }
        }
    }
}