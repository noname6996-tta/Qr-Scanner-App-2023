package com.tta.qrscanner2023application.data.util

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog

object DialogUtil {

    private var dialog: Dialog? = null

    /**
     * Hiển thị AlertDialog
     */
    fun showAlertDialog(
        context: Context,
        title: String? = null,
        message: String,
        positiveText: String = "OK",
        negativeText: String? = null,
        cancelable: Boolean = true,
        onPositiveClick: (() -> Unit)? = null,
        onNegativeClick: (() -> Unit)? = null
    ) {
        val builder = AlertDialog.Builder(context)

        title?.let { builder.setTitle(it) }
        builder.setMessage(message)

        builder.setPositiveButton(positiveText) { dialog, _ ->
            dialog.dismiss()
            onPositiveClick?.invoke()
        }

        negativeText?.let {
            builder.setNegativeButton(it) { dialog, _ ->
                dialog.dismiss()
                onNegativeClick?.invoke()
            }
        }

        builder.setCancelable(cancelable)
        builder.show()
    }

    /**
     * Hiển thị Dialog custom
     */
    fun showCustomDialog(
        context: Context,
        @LayoutRes layoutResId: Int,
        cancelable: Boolean = true,
        isFullWidth: Boolean = true,
        onViewCreated: ((View, Dialog) -> Unit)? = null
    ) {
        dialog = Dialog(context)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val view = LayoutInflater.from(context).inflate(layoutResId, null)
        dialog?.setContentView(view)
        dialog?.setCancelable(cancelable)

        if (isFullWidth) {
            dialog?.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }

        onViewCreated?.invoke(view, dialog!!)

        dialog?.show()
    }

    /**
     * Ẩn dialog nếu đang hiển thị
     */
    fun dismissDialog() {
        dialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
        dialog = null
    }
}