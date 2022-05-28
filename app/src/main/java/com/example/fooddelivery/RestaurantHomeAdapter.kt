package com.example.fooddelivery

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class RestaurantHomeAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> {return "PROCESSING"}
            1 -> {return "DONE"}
        }
        return super.getPageTitle(position)
    }

    override fun getItem(position: Int): Fragment {
        when (position){
            0 -> {return RestaurantHomeProcessingFragment()}
            1 -> {return RestaurantHomeDoneFragment()}
            else -> {return RestaurantHomeProcessingFragment()}
        }
    }
}