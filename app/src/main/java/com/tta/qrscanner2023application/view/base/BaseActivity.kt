package com.tta.fitnessapplication.view.base

import android.content.Context
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
            if (it.resources.configuration.locales[0].language!= currentLanguage) {
                super.attachBaseContext(
                    LanguageHelper.setNewLocale(it, currentLanguage)
                )
            }else super.attachBaseContext(base)
        }
        if (base==null) super.attachBaseContext(base)
    }
}