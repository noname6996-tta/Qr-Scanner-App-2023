package com.tta.qrscanner2023application.view.fragment.qrscan

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap

import com.google.zxing.NotFoundException
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.ResultPoint
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeReader
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.tta.qrscanner2023application.R
import com.tta.qrscanner2023application.data.model.TypeCode
import com.tta.qrscanner2023application.data.util.playSound
import com.tta.qrscanner2023application.data.util.vibratePhone
import com.tta.qrscanner2023application.databinding.FragmentQrScanBinding
import com.tta.qrscanner2023application.view.base.BaseCameraFragment
import com.tta.qrscanner2023application.view.fragment.setting.SettingViewModel
import com.tta.qrscanner2023application.view.main.MainActivity

class QrScanFragment : BaseCameraFragment<FragmentQrScanBinding>() {
    override var isTerminalBackKeyActive: Boolean = false
    private var lastText: String = ""
    private var canOpenExternalImage = false
    private var isFlashOn = false
    private lateinit var settingViewModel : SettingViewModel
    private var isVibrationOn = false
    private var isSoundOn = false
    private val callback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult?) {
            if (isVibrationOn){
                requireActivity().vibratePhone()
            }
            if (isSoundOn){
                playSound(requireActivity())
            }
            if (result?.text == null || result.text == lastText) {
                return
            }
            lastText = result.text
            findNavController().navigate(
                QrScanFragmentDirections.actionQrScanFragmentToShowQrFragment(
                    lastText,TypeCode.SCAN.toString()
                )
            )
            lastText = ""
        }

        override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
        }
    }

    override fun getDataBinding(): FragmentQrScanBinding {
        return FragmentQrScanBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        settingViewModel = SettingViewModel(requireContext())
        settingViewModel.getData()
    }

    override fun addObservers() {
        super.addObservers()
        settingViewModel.soundData.observe(viewLifecycleOwner) {
            isSoundOn = it
        }
        settingViewModel.vibrationData.observe(viewLifecycleOwner) {
            isVibrationOn = it
        }
    }

    override fun initView() {
        super.initView()
        checkPermissions(requireContext())
        (requireActivity() as MainActivity).setVisibleBottomBar(true)
    }

    override fun addEvent() = with(binding) {
        super.addEvent()
        imgChoosePic.setOnClickListener {
            if (canOpenExternalImage || android.os.Build.VERSION.SDK_INT >= 13) {
                openGallery()
            } else {
                checkPermission()
            }
        }
        imgFlash.setOnClickListener {
            isFlashOn = !isFlashOn
            checkFlashOn()
        }
        imgSetting.setOnClickListener {
            (requireActivity() as MainActivity).setVisibleBottomBar(false)
            findNavController().navigate(R.id.action_qrScanFragment_to_settingFragment)
        }
    }

    override var onSuccess: () -> Unit =
        {
            val formats = listOf(BarcodeFormat.QR_CODE, BarcodeFormat.DATA_MATRIX, BarcodeFormat.PDF_417, BarcodeFormat.MAXICODE, BarcodeFormat.ITF)
            binding.barcodeView.barcodeView.decoderFactory = DefaultDecoderFactory(formats)
            binding.barcodeView.decodeContinuous(callback)
            canOpenExternalImage = true
        }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openGallery()
            } else {
                Toast.makeText(requireContext(),
                    getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
            }
        }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val imageUri: Uri? = data?.data
                val bitmap = BitmapFactory.decodeStream(
                    requireActivity().contentResolver.openInputStream(imageUri!!)
                )

                // Convert bitmap to binary bitmap
                val width = bitmap.width
                val height = bitmap.height
                val pixels = IntArray(width * height)
                bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
                val source = RGBLuminanceSource(width, height, pixels)
                val binaryBitmap = BinaryBitmap(HybridBinarizer(source))

                // Use ZXing to decode QR code
                val reader = QRCodeReader()
                try {
                    val code = reader.decode(binaryBitmap)
                    // Process the decoded result as needed
                    lastText = code.text
                    findNavController().navigate(QrScanFragmentDirections.actionQrScanFragmentToResultFragment(
                            lastText
                        ))
                    lastText = ""
                } catch (e: NotFoundException) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(),
                        getString(R.string.qr_code_not_found), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(),
                    getString(R.string.no_image_selected), Toast.LENGTH_SHORT).show()
            }
        }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(galleryIntent)
    }

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                openGallery()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.permission_is_required_to_access_gallery),
                    Toast.LENGTH_SHORT
                ).show()
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun checkFlashOn() = with(binding){
        if (isFlashOn){
            barcodeView.setTorchOn()
            binding.imgFlash.setImageResource(R.drawable.ic_flash_on)
        } else {
            barcodeView.setTorchOff()
            binding.imgFlash.setImageResource(R.drawable.ic_flash)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        isFlashOn = false
        binding.imgFlash.setImageResource(R.drawable.ic_flash)
        binding.barcodeView.setTorchOff()
        binding.barcodeView.pause()
    }
}