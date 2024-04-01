package com.tta.qrscanner2023application.view.fragment

import android.content.pm.PackageManager
import android.graphics.Bitmap
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.data.util.saveImage
import com.tta.qrscanner2023application.databinding.FragmentGenerateBinding

class GenerateFragment : BaseFragment<FragmentGenerateBinding>() {
    override var isTerminalBackKeyActive: Boolean = false
    private val WRITE_EXTERNAL_STORAGE_REQUEST = 123
    private var imageBitmapResource: Bitmap? = null

    override fun getDataBinding(): FragmentGenerateBinding {
        return FragmentGenerateBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()

    }

    override fun addEvent() = with(binding) {
        super.addEvent()
//        llSaveImage.setOnClickListener {
//            // Check for permission
//            if (ContextCompat.checkSelfPermission(
//                    requireActivity(),
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                // Permission is not granted
//                // Request the permission
//                ActivityCompat.requestPermissions(
//                    requireActivity(),
//                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                    WRITE_EXTERNAL_STORAGE_REQUEST
//                )
//            } else {
//                // Permission has already been granted
//                // Proceed with the image saving process
//                imageBitmapResource?.let { saveImage(it) }
//            }
//        }
    }


    // Override the onRequestPermissionsResult method to handle the result of the permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            WRITE_EXTERNAL_STORAGE_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted, proceed with the image saving process
                    imageBitmapResource?.let { saveImage(requireActivity(), binding.root, it) }
                } else {
                    // Permission is denied, handle this according to your app's logic
                }
                return
            }
        }
    }
}