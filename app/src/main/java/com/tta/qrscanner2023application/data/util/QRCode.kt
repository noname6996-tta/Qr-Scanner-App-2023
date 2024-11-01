package com.tta.qrscanner2023application.data.util

import com.tta.qrscanner2023application.R

enum class QRCode(val text: String, val type: String, val icon: Int) {
    TEXT("Text", "1", R.drawable.ic_text),
    PHONE("Phone", "2", R.drawable.ic_phone),
    WEB("Website", "3", R.drawable.ic_web),
    FACEBOOK("Facebook", "4", R.drawable.ic_facebook),
    INSTAGRAM("Instagram", "5", R.drawable.ic_instagram),
    TWITTER("Twitter", "6", R.drawable.ic_x),
    WIFI("Wifi", "7", R.drawable.ic_wifi),
    MAIL("Mail", "8", R.drawable.ic_mail),
    ;

    companion object {
        fun getByType(status: String?): QRCode = entries.find { it.type == status } ?: TEXT
    }
}