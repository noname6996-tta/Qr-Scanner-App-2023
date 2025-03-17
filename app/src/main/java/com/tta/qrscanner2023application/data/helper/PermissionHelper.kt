package com.tta.qrscanner2023application.data.helper

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class PermissionHelper(
    private val fragment: Fragment,
    private val onSuccess: () -> Unit
) {
    companion object {
        val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.VIBRATE
        )
    }

    private val permissionLauncher = fragment.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            onSuccess.invoke()
        } else {
            handleDeniedPermissions(permissions)
        }
    }

    fun checkAndRequestPermissions() {
        val context = fragment.requireContext()
        if (REQUIRED_PERMISSIONS.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }) {
            onSuccess.invoke()
        } else {
            permissionLauncher.launch(REQUIRED_PERMISSIONS)
        }
    }

    private fun handleDeniedPermissions(permissions: Map<String, Boolean>) {
        val context = fragment.requireContext()
        if (!permissions[Manifest.permission.CAMERA]!!) {
            AlertDialog.Builder(context)
                .setMessage("Ứng dụng cần quyền Camera để quét mã QR. Hãy bật quyền Camera trong cài đặt.")
                .setPositiveButton("OK") { _, _ ->
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:${context.packageName}")
                    ).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
                    context.startActivity(intent)
                }
                .show()
        }
    }
}
