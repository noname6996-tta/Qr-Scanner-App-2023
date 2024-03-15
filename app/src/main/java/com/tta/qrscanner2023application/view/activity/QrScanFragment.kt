package com.tta.qrscanner2023application.view.activity

import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.tta.qrscanner2023application.databinding.FragmentQrScanBinding
import com.tta.qrscanner2023application.view.base.BaseCameraPermissionFragment

class QrScanFragment : BaseCameraPermissionFragment<FragmentQrScanBinding>() {
    private var lastText: String = ""
    private val callback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult?) {
            if (result?.text == null || result.text == lastText) {
                return
            }
            lastText = result.text
            Log.e("tta", lastText)
        }

        override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
        }
    }

    override fun getDataBinding(): FragmentQrScanBinding {
        return FragmentQrScanBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        checkPermissions(requireContext())
        binding.btClose.setOnClickListener {

        }
    }

    override var onPermissionSuccess: () -> Unit =
        {
            val formats = listOf(BarcodeFormat.QR_CODE)
            binding.barcodeView.barcodeView.decoderFactory = DefaultDecoderFactory(formats)
            binding.barcodeView.decodeContinuous(callback)
        }

    override fun onResume() {
        super.onResume()
        binding.barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.barcodeView.pause()
    }
}