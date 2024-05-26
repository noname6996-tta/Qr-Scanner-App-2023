package com.tta.qrscanner2023application.data.util

object QRForm {
    const val WIFI_TEMPLATE = "WIFI:S:[SSID];T:WPA;P:[PASSWORD];H:false;;"
    const val FACEBOOK_TEMPLATE = "https://www.facebook.com/[USERNAME]"
    const val INSTAGRAM_TEMPLATE = "https://www.instagram.com/[USERNAME]/"
    const val PHONE_TEMPLATE = "tel:[PHONE_NUMBER]"
    const val EMAIL_TEMPLATE = "mailto:[EMAIL_ADDRESS]"
    const val TWITTER_TEMPLATE = "https://twitter.com/[USERNAME]"

    //val phoneLink = PhoneForm.PHONE_TEMPLATE.replace("[PHONE_NUMBER]", phoneNumber)
    //val emailLink = EmailForm.EMAIL_TEMPLATE.replace("[EMAIL_ADDRESS]", emailAddress)

    fun generateTwitterLink(username: String): String {
        return TWITTER_TEMPLATE.replace("[USERNAME]", username)
    }

    fun generateInstagramLink(username: String): String {
        return INSTAGRAM_TEMPLATE.replace("[USERNAME]", username)
    }

    fun generateFacebookLink(username: String): String {
        return FACEBOOK_TEMPLATE.replace("[USERNAME]", username)
    }

    fun generateWifiString(ssid: String, password: String): String {
        return WIFI_TEMPLATE.replace("[SSID]", ssid).replace("[PASSWORD]", password)
    }
}