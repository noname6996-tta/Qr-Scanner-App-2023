package com.tta.qrscanner2023application.view.fragment.rate

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.tta.qrscanner2023application.R
import com.tta.qrscanner2023application.view.base.BaseDialogFragment
import androidx.core.net.toUri
import androidx.core.content.edit

class RateAppDialogFragment : BaseDialogFragment() {

    companion object {
        private const val PREFS_NAME = "rate_app_prefs"
        private const val KEY_NEVER_SHOW = "never_show_rate_dialog"
        private const val KEY_APP_OPEN_COUNT = "app_open_count"
        private const val KEY_LAST_SHOWN_COUNT = "last_shown_count"

        // Các lần hiển thị dialog
        private val SHOW_AT_COUNTS = listOf(2, 5, 10)

        fun newInstance(): RateAppDialogFragment {
            return RateAppDialogFragment()
        }

        /**
         * Kiểm tra xem có nên hiển thị dialog không
         * Gọi method này khi user mở app
         */
        fun shouldShowDialog(context: Context): Boolean {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

            // Nếu user đã chọn "Never", không hiển thị nữa
            if (prefs.getBoolean(KEY_NEVER_SHOW, false)) {
                return false
            }

            val currentCount = prefs.getInt(KEY_APP_OPEN_COUNT, 0) + 1
            val lastShownCount = prefs.getInt(KEY_LAST_SHOWN_COUNT, 0)

            // Cập nhật số lần mở app
            prefs.edit { putInt(KEY_APP_OPEN_COUNT, currentCount) }

            // Kiểm tra xem có nên hiển thị dialog không
            return SHOW_AT_COUNTS.contains(currentCount) && currentCount != lastShownCount
        }

        /**
         * Đánh dấu dialog đã được hiển thị
         */
        fun markDialogShown(context: Context) {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val currentCount = prefs.getInt(KEY_APP_OPEN_COUNT, 0)
            prefs.edit { putInt(KEY_LAST_SHOWN_COUNT, currentCount) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_rate_app, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Đánh dấu dialog đã được hiển thị
        markDialogShown(requireContext())

        setupViews(view)
    }

    private fun setupViews(view: View) {
        val titleText = view.findViewById<TextView>(R.id.tv_title)
        val messageText = view.findViewById<TextView>(R.id.tv_message)
        val btnNever = view.findViewById<Button>(R.id.btn_never)
        val btnLater = view.findViewById<Button>(R.id.btn_later)
        val btnRate = view.findViewById<Button>(R.id.btn_rate)

        titleText.text = getString(R.string.title_rate_app)
        messageText.text = getString(R.string.desc_rate_app)

        btnNever.setOnClickListener {
            handleNeverClicked()
        }

        btnLater.setOnClickListener {
            handleLaterClicked()
        }

        btnRate.setOnClickListener {
            handleRateClicked()
        }
    }

    private fun handleNeverClicked() {
        // Lưu flag không hiển thị nữa
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit { putBoolean(KEY_NEVER_SHOW, true) }

        dismiss()
    }

    private fun handleLaterClicked() {
        // Chỉ đóng dialog, sẽ hiển thị lại ở lần tiếp theo
        dismiss()
    }

    private fun handleRateClicked() {
        // Mở Google Play Store
        openPlayStore()

        // Đánh dấu không hiển thị nữa vì user đã rate
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit { putBoolean(KEY_NEVER_SHOW, true) }

        dismiss()
    }

    private fun openPlayStore() {
        try {
            val packageName = requireContext().packageName
            val playStoreIntent =
                Intent(Intent.ACTION_VIEW, "market://details?id=$packageName".toUri())
            startActivity(playStoreIntent)
        } catch (e: Exception) {
            // Nếu không có Google Play Store, mở bằng browser
            val packageName = requireContext().packageName
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                "https://play.google.com/store/apps/details?id=$packageName".toUri()
            )
            startActivity(browserIntent)
        }
    }
}