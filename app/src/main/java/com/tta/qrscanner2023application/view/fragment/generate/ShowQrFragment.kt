package com.tta.qrscanner2023application.view.fragment.generate

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tta.qrscanner2023application.R
import com.tta.qrscanner2023application.data.model.QrCodeEntity
import com.tta.qrscanner2023application.data.model.TypeCode
import com.tta.qrscanner2023application.data.util.copyToClipboard
import com.tta.qrscanner2023application.data.util.generateQrCode
import com.tta.qrscanner2023application.data.util.isWebLinkOrAppLink
import com.tta.qrscanner2023application.data.util.saveImage
import com.tta.qrscanner2023application.data.util.shareImage
import com.tta.qrscanner2023application.databinding.FragmentShowQrBinding
import com.tta.qrscanner2023application.view.base.BaseFragment
import com.tta.qrscanner2023application.view.fragment.qrscan.QrScanViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ShowQrFragment : BaseFragment<FragmentShowQrBinding>() {
    private val qrViewModel: QrScanViewModel by viewModels()
    private lateinit var viewModel: QrScanViewModel
    override var isTerminalBackKeyActive: Boolean = true
    private val args: ShowQrFragmentArgs by navArgs()
    private var imageBitmapResoure: Bitmap? = null
    private val WRITE_EXTERNAL_STORAGE_REQUEST = 123
    override fun getDataBinding(): FragmentShowQrBinding {
        return FragmentShowQrBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel = qrViewModel
    }

    override fun initView() = with(binding) {
        super.initView()
        result.text = args.result
        insertQrCodeScan(args.result)
        imgQr.setImageBitmap(generateQrCode(args.result))
        imageBitmapResoure = generateQrCode(args.result)
        llAction.actionShare.root.visibility = View.GONE
        llAction.actionSave.root.visibility = View.GONE
        binding.tvTime.text = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        setViewActionQr(args.result)
    }

    override fun addEvent() = with(binding) {
        super.addEvent()
        imgBack.setOnClickListener { findNavController().popBackStack() }
        viewShowQr.setOnClickListener {
            checkImgVis()
        }
    }

    private fun checkImgVis() = with(binding) {
        if (imgQr.visibility == View.VISIBLE) {
            imgQr.visibility = View.GONE
            tvShowQr.text = "Show Qr code"
            llAction.actionShare.root.visibility = View.GONE
            llAction.actionSave.root.visibility = View.GONE
        } else {
            imgQr.visibility = View.VISIBLE
            tvShowQr.text = "Hide Qr code"
            llAction.actionShare.root.visibility = View.VISIBLE
            llAction.actionSave.root.visibility = View.VISIBLE
        }
    }

    private fun setViewActionQr(result: String) = with(binding) {
        with(llAction) {

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
            actionSave.tvTittle.text = "Save QrCode"
            actionSave.imgIcon.setImageResource(R.drawable.ic_save)
            actionSave.llItem.setOnClickListener {
                if (Build.VERSION.SDK_INT >= 13) {
                    imageBitmapResoure?.let { saveImage(requireContext(), binding.root, it) }
                    Toast.makeText(requireContext(), "Save success", Toast.LENGTH_LONG).show()
                } else {
                    // Check for permission
                    if (ContextCompat.checkSelfPermission(
                            requireActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // Permission is not granted
                        // Request the permission
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            WRITE_EXTERNAL_STORAGE_REQUEST
                        )
                    } else {
                        // Permission has already been granted
                        // Proceed with the image saving process
                        Toast.makeText(requireContext(), "Save success", Toast.LENGTH_LONG).show()
                        imageBitmapResoure?.let { saveImage(requireContext(), binding.root, it) }
                    }
                }
            }
        }
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
                    imageBitmapResoure?.let { saveImage(requireActivity(), binding.root, it) }
                } else {
                    // Permission is denied, handle this according to your app's logic
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage("Please grant permission to save image for the application, because this is a QR code scanning application, you cannot use it without turning on the camera") // Replace with your message
                    builder.setPositiveButton("OK") { _, _ ->
                        // Execute Intent to Launch Application Details Settings
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:${requireContext().packageName}")
                        )
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                    val dialog = builder.create()
                    dialog.show()
                }
                return
            }
        }
    }

    private fun insertQrCodeScan(code: String) {
        val scan = QrCodeEntity(0, code, LocalDate.now(), TypeCode.CREATED)
        viewModel.insertQrCode(scan)
    }
}