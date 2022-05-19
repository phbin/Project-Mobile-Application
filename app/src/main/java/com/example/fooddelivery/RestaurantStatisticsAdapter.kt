package com.example.fooddelivery

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class RestaurantStatisticsAdapter (fm : FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> {return "DAY"}
            1 -> {return "MONTH"}
        }
        return super.getPageTitle(position)
    }

    override fun getItem(position: Int): Fragment {
        when (position){
            0 -> {return RestaurantStatisticsDayFragment() }
            1 -> {return RestaurantStatisticMonthFragment()}
            else -> {return RestaurantStatisticsDayFragment()}
        }
    }
}