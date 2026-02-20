package com.tta.qrscanner2023application.data.util

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.tta.qrscanner2023application.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Hashtable
import androidx.core.graphics.set
import androidx.core.graphics.createBitmap
import java.net.URLEncoder

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

fun shareImage(context: Context, imageBitmapResource: Bitmap) {
    try {
        val path = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            imageBitmapResource,
            "title",
            "qrCode"
        )
        val uri = Uri.parse(path)

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/*"
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(Intent.createChooser(shareIntent, "Share image via"))
    } catch (e: Exception) {

    }
}

fun shareText(
    context: Context,
    text: String,
    subject: String? = null
) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
        subject?.let {
            putExtra(Intent.EXTRA_SUBJECT, it)
        }
    }

    context.startActivity(
        Intent.createChooser(intent, "Chia sẻ qua")
    )
}

fun copyToClipboard(context: Context, view: View, text: String) {
    val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("text", text)
    clipboardManager.setPrimaryClip(clipData)

    Snackbar.make(view, "Copy Success", Snackbar.LENGTH_SHORT).show()
}

fun saveImage(context: Context, view: View, bitmap: Bitmap) {
    val timestamp = System.currentTimeMillis()

    //Tell the media scanner about the new file so that it is immediately available to the user.
    val values = ContentValues()
    values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
    values.put(MediaStore.Images.Media.DATE_ADDED, timestamp)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        values.put(MediaStore.Images.Media.DATE_TAKEN, timestamp)
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "")
        values.put(MediaStore.Images.Media.IS_PENDING, true)
        val uri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )
        if (uri != null) {
            try {
                val outputStream = context.contentResolver.openOutputStream(uri)
                if (outputStream != null) {
                    try {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                        outputStream.close()
                    } catch (e: Exception) {

                    }
                }
                values.put(MediaStore.Images.Media.IS_PENDING, false)
                context.contentResolver.update(uri, values, null, null)
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
            context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )

            Toast.makeText(context, "Saved...", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {

        }
    }
}

fun generateQrCode(qrCodeData: String): Bitmap? {
    val hints = Hashtable<EncodeHintType, Any>()
    hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
    val qrCodeWriter = QRCodeWriter()
    try {
        val bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, 512, 512, hints)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bmp = createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp[x, y] = if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt()
            }
        }
        // Use 'bmp' to display the QR code as needed
        return bmp
    } catch (e: WriterException) {
        e.printStackTrace()
        // Handle exception
        return null
    }
}

fun isWebLinkOrAppLink(input: String): Boolean {
    val webLinkPattern = "^(http|https)://.*".toRegex()
    val appLinkPattern = "^myapp://.*".toRegex()

    return input.matches(webLinkPattern) || input.matches(appLinkPattern)
}

fun Activity.vibratePhone() {
    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(
            VibrationEffect.createOneShot(
                100,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(100)
    }
}

fun playSound(context: Context) {
    val mediaPlayer = MediaPlayer.create(context, R.raw.done)
    mediaPlayer.start()
}

fun searchOnWeb(context: Context, query: String) {
    val encodedQuery = URLEncoder.encode(query, "UTF-8")
    val url = "https://www.google.com/search?q=$encodedQuery"

    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }

    context.startActivity(intent)
}