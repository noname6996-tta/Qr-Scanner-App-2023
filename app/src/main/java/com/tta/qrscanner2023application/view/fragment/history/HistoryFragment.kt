package com.tta.qrscanner2023application.view.fragment.history

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.qrscanner2023application.R
import com.tta.qrscanner2023application.databinding.FragmentHistoryBinding

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {
    override var isTerminalBackKeyActive: Boolean = false
    private lateinit var adapterPager: FragmentPageAdapter
    override fun getDataBinding(): FragmentHistoryBinding {
        return FragmentHistoryBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        initUiPager()
    }

    private fun initUiPager()  = with(binding){
        adapterPager = FragmentPageAdapter(requireActivity().supportFragmentManager, lifecycle)

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.scan)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.created)))

        viewPager2.adapter = adapterPager

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }
}