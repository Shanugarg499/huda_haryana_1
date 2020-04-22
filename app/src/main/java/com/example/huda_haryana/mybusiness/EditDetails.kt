package com.example.huda_haryana.mybusiness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.ActivityEditDetailsBinding

class EditDetails : AppCompatActivity() {
    lateinit var binding:ActivityEditDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_edit_details)
        binding.toolbarEditdetails.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
