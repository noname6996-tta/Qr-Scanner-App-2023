package com.tta.qrscanner2023application.view.base

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseCameraPermissionFragment<T : ViewBinding> : Fragment()  {
    private var _binding: T? = null
    protected val binding: T
        get() = checkNotNull(_binding) {
            "Fragment $this binding cannot be accessed before onCreateView() or after onDestroyView()"
        }


    //Enabling or disabling the device back key
    abstract var isTerminalBackKeyActive: Boolean

    protected inline fun binding(block: T.() -> Unit): T {
        return binding.apply(block)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (isTerminalBackKeyActive.not()) {
            requireActivity().onBackPressedDispatcher
                .addCallback(this) { }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getDataBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
        addEvent()
        addObservers()
        initData()
    }

    abstract fun getDataBinding(): T

    open fun initViewModel() {}

    open fun initView() {}

    open fun addEvent() {}

    open fun addObservers() {}

    open fun initData() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        private const val REQUEST_CODE_CAMERA_PERMISSION = 200
        var PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA
        )
    }

    abstract var onPermissionSuccess: () -> Unit

    internal fun checkPermissions(context: Context) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permReqLauncher.launch(
                PERMISSIONS
            )
        } else {
            onPermissionSuccess.invoke()
        }
    }

    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value
            }
            if (granted) {
                onPermissionSuccess.invoke()
            } else if (permissions.entries.filter { it.key == Manifest.permission.CAMERA }
                    .isNotEmpty()) {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Please grant permission to use the camera for the application, because this is a QR code scanning application, you cannot use it without turning on the camera") // Replace with your message
                builder.setPositiveButton("OK") { dialog, which ->
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
        }
}