package com.tta.qrscanner2023application.view.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import kotlin.apply
import kotlin.let

abstract class BaseDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.apply {
                requestFeature(Window.FEATURE_NO_TITLE)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let { window ->
            val metrics = resources.displayMetrics
            val marginDp = 24
            val marginPx = (marginDp * metrics.density).toInt()
            val screenWidth = metrics.widthPixels
            val desiredWidth = screenWidth - (marginPx * 2)

            window.setLayout(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }
}