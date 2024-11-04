package com.tta.qrscanner2023application.view.fragment.setting.language

import android.content.Context
import java.util.Locale

object LanguageHelper {
    const val SETTINGS = "settings"
    const val LANGUAGE_SELECT = "language"
    const val MODE_AUTO ="auto"

    fun getLanguagePref(context: Context):String{
        val pref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
        return pref.getString(LANGUAGE_SELECT,MODE_AUTO)?:MODE_AUTO
    }
    fun setLanguagePref(context: Context,languageCode:String){
        val pref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
        pref.edit().putString(LANGUAGE_SELECT,languageCode).apply()
    }

    fun setNewLocale(context: Context,languageCode: String): Context{
        setLanguagePref(context,languageCode)
        return updateResource(context,languageCode)
    }

    fun updateResource(context:Context,languageCode: String): Context{
        val resources = context.resources
        val config = resources.configuration

        val locale = if (languageCode==MODE_AUTO){
            config.locale
        }else{
            Locale(languageCode)
        }

        config.setLocale(locale)
        config.setLayoutDirection(locale)
        resources.updateConfiguration(config,resources.displayMetrics)

        return context
    }

}