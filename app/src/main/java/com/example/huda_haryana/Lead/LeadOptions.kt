package com.example.huda_haryana.Lead

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
        binding.toolbarLeadoptions.inflateMenu(R.menu.bottom_navigation_menu)
        val id=intent.getStringExtra("id")
        val name=intent.getStringExtra("name")
        binding.leadoptionAddnote.setOnClickListener {
            val intent=Intent(this,LeadNote::class.java).putExtra("id",id).putExtra("name",name)
            startActivity(intent)

        }
        binding.leadoptionNameTxt.text=name
    }
}
