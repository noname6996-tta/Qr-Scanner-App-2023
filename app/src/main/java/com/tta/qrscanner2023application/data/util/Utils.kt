package com.tta.qrscanner2023application.data.util
import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build

fun hasFlashFeature(context: Context): Boolean {
    val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    val cameraId = cameraManager.cameraIdList[0]
    val characteristics = cameraManager.getCameraCharacteristics(cameraId)
    return characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
}

// Turn on the flash
fun turnOnFlash(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]
        cameraManager.setTorchMode(cameraId, true)
    }
}

// Turn off the flash
fun turnOffFlash(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]
        cameraManager.setTorchMode(cameraId, false)
    }
}