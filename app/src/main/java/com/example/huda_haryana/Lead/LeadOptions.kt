package com.example.huda_haryana.Lead

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.ActivityLeadOptionsBinding

class LeadOptions : AppCompatActivity() {
    lateinit var binding: ActivityLeadOptionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_lead_options)
        binding.toolbarLeadoptions.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
