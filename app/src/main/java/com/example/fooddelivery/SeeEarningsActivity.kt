package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_see_earnings.*

class SeeEarningsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_earnings)

        viewPager.adapter = ShipperIncomeAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

        btnBack.setOnClickListener {
            finish()
        }
    }
}