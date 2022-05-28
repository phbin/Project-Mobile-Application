package com.example.fooddelivery

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PageAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> {return "NEW ORDER"}
            1 -> {return "HISTORY"}
            2 -> {return "INCOME"}
            3 -> {return "PROFILE"}
        }
        return super.getPageTitle(position)
    }

    override fun getItem(position: Int): Fragment {
        when (position){
            0 -> {return ShipperNewOrderFragment()}
            1 -> {return ShipperHistoryFragment()}
            2 -> {return ShipperIncomeFragment()}
            3 -> {return ShipperProfileFragment()}
            else -> {return ShipperNewOrderFragment()}
        }
    }
}