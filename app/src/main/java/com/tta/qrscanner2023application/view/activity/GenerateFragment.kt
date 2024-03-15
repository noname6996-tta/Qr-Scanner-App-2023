package com.tta.qrscanner2023application.view.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.databinding.FragmentGenerateBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.Hashtable

class GenerateFragment : BaseFragment<FragmentGenerateBinding>() {

    private val WRITE_EXTERNAL_STORAGE_REQUEST = 123
    private var imageBitmapResource: Bitmap? = null

    override fun getDataBinding(): FragmentGenerateBinding {
        return FragmentGenerateBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
//        tvTitle.text = getString(ReferralCodeViewModel.Text.REFERRAL_TITTLE.id)
//        tvSaveImage.text = getString(ReferralCodeViewModel.Text.SAVE.id)
//        tvShareImage.text = getString(ReferralCodeViewModel.Text.SHARE.id)
//        tvCodeTittle.text = getString(ReferralCodeViewModel.Text.CODE_TITTLE.id)
//        tvCopyTittle.text = getString(ReferralCodeViewModel.Text.COPY.id)
//        btShowInfo.text = getString(ReferralCodeViewModel.Text.BUTTON_INFO.id)
//        tvDescReferralCode.text = getString(ReferralCodeViewModel.Text.REFERRAL_CODE_INFO.id)
//
//        tvQrCode.text = args.referralCode
//        if (args.referralCode.isEmpty().not()) {
//            generateQrCode(args.referralCode)
//        } else {
//            generateQrCode(" ")
//        }
    }

    override fun addEvent() = with(binding){
        super.addEvent()
//        llCopyCode.setOnClickListener {
//            if (tvQrCode.text.toString().trim().isNotEmpty()) {
//                copyToClipboard()
//            }
//        }
//
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
//
//        llShareImage.setOnClickListener {
//            shareImage()
//        }
//
//        btShowInfo.setOnClickListener {
//            if (tvDescReferralCode.visibility == View.VISIBLE) {
//                tvDescReferralCode.visibility = View.GONE
//                btShowInfo.icon =
//                    AppCompatResources.getDrawable(requireContext(), R.drawable.select_nega)
//            } else {
//                tvDescReferralCode.visibility = View.VISIBLE
//                btShowInfo.icon =
//                    AppCompatResources.getDrawable(requireContext(), R.drawable.select_nega_off)
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
                    imageBitmapResource?.let { saveImage(it) }
                } else {
                    // Permission is denied, handle this according to your app's logic
                }
                return
            }
        }
    }

    private fun generateQrCode(qrCodeData: String) {
        val hints = Hashtable<EncodeHintType, Any>()
        hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
        val qrCodeWriter = QRCodeWriter()
        try {
            val bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, 512, 512, hints)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(
                        x,
                        y,
                        if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt()
                    )
                }
            }
            // Use 'bmp' to display the QR code as needed
            imageBitmapResource = bmp
//            binding.imgQrRefer.setImageBitmap(bmp)
        } catch (e: WriterException) {
            e.printStackTrace()
            // Handle exception
        }
    }

    private fun copyToClipboard() {
//        val textToCopy = binding.tvQrCode.text.toString().trim()
        val textToCopy = ""

        val clipboardManager =
            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)

        Snackbar.make(binding.root, "コピーしました。", Snackbar.LENGTH_SHORT).show()
    }

    private fun saveImage(bitmap: Bitmap) {
        val timestamp = System.currentTimeMillis()

        //Tell the media scanner about the new file so that it is immediately available to the user.
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.DATE_ADDED, timestamp)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.DATE_TAKEN, timestamp)
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "")
            values.put(MediaStore.Images.Media.IS_PENDING, true)
            val uri = requireActivity().contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )
            if (uri != null) {
                try {
                    val outputStream = requireActivity().contentResolver.openOutputStream(uri)
                    if (outputStream != null) {
                        try {
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                            outputStream.close()
                        } catch (e: Exception) {

                        }
                    }
                    values.put(MediaStore.Images.Media.IS_PENDING, false)
                    requireActivity().contentResolver.update(uri, values, null, null)

                    Snackbar.make(binding.root, "保存しました。", Snackbar.LENGTH_SHORT).show()
                } catch (e: Exception) {

                }
            }
        } else {
            val imageFileFolder = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString()
            )
            if (!imageFileFolder.exists()) {
                imageFileFolder.mkdirs()
            }
            val mImageName = "$timestamp.png"
            val imageFile = File(imageFileFolder, mImageName)
            try {
                val outputStream: OutputStream = FileOutputStream(imageFile)
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    outputStream.close()
                } catch (e: Exception) {

                }
                values.put(MediaStore.Images.Media.DATA, imageFile.absolutePath)
                requireActivity().contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    values
                )

                Toast.makeText(requireContext(), "Saved...", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {

            }
        }
    }

    private fun shareImage() {
        try {
            val path = MediaStore.Images.Media.insertImage(
                requireActivity().contentResolver,
                imageBitmapResource,
                "title",
                "qrCode"
            )
            val uri = Uri.parse(path)

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/*"
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(Intent.createChooser(shareIntent, "Share image via"))
        } catch (e: Exception) {

        }
    }
}