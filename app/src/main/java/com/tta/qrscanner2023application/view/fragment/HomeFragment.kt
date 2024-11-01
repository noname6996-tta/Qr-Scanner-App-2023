package com.tta.qrscanner2023application.view.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.permissionx.guolindev.PermissionX
import com.tta.qrscanner2023application.view.base.BaseFragment
import com.tta.qrscanner2023application.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private var barcodeDetector: BarcodeDetector? = null
    private var cameraSource: CameraSource? = null
    private val REQUEST_CAMERA_PERMISSION = 201
    private var intentData = ""
    override var isTerminalBackKeyActive: Boolean = false

    override fun getDataBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun initView() {
        super.initView()
        PermissionX.init(this)
            .permissions(
                Manifest.permission.CAMERA,
            )
            .explainReasonBeforeRequest()
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    "Core fundamental are based on these permissions",
                    "OK",
                    "Cancel"
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    "You need to allow necessary permissions in Settings manually",
                    "OK",
                    "Cancel"
                )
            }
            .request { allGranted, _, deniedList ->
                if (!allGranted) {
                    Toast.makeText(
                        requireContext(),
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        initialiseDetectorsAndSources()
    }

    private fun initialiseDetectorsAndSources() {
        barcodeDetector = BarcodeDetector.Builder(requireActivity())
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()

        cameraSource = CameraSource.Builder(requireActivity(), barcodeDetector!!)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true)
            .build()

        binding.surfaceView.holder.addCallback(surfaceHolderCallback)

        barcodeDetector?.setProcessor(barcodeProcessor)
    }

    private val surfaceHolderCallback = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                cameraSource?.start(holder)
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CAMERA_PERMISSION
                )
            }
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            // Surface changed implementation if needed
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            cameraSource?.stop()
        }
    }

    private val barcodeProcessor = object : Detector.Processor<Barcode> {
        override fun release() {
            Toast.makeText(requireContext(), "Barcode scanner stopped", Toast.LENGTH_SHORT).show()
        }

        override fun receiveDetections(detections: Detections<Barcode>) {
            val barcodes = detections.detectedItems
            if (barcodes.size() != 0) {
                binding.txtBarcodeValue.post {
                    intentData = barcodes.valueAt(0).displayValue.trim()
                    binding.txtBarcodeValue.text = intentData
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        cameraSource?.release()
    }

    override fun onResume() {
        super.onResume()
        initialiseDetectorsAndSources()
    }
}