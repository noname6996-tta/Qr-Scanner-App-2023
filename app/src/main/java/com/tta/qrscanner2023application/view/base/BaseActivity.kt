package com.tta.qrscanner2023application.view.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.tta.qrscanner2023application.view.fragment.setting.language.LanguageHelper

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    private var _binding: T? = null
    protected val binding: T
        get() = checkNotNull(_binding) {
            "Activity $this binding cannot be accessed before onCreateView() or after onDestroyView()"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getDataBinding()
        setContentView(binding.root)
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
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun attachBaseContext(base: Context?) {
        base?.let {
            val currentLanguage = LanguageHelper.getLanguagePref(it)

            // Kiểm tra phiên bản Android
            val currentLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // For Android Nougat and above, use getLocales()
                it.resources.configuration.locales[0].language
            } else {
                // For older versions, use locale
                it.resources.configuration.locale.language
            }

            if (currentLocale != currentLanguage) {
                super.attachBaseContext(
                    LanguageHelper.setNewLocale(it, currentLanguage)
                )
            } else {
                super.attachBaseContext(base)
            }
        } ?: super.attachBaseContext(base) // Handle null base case
    }
}