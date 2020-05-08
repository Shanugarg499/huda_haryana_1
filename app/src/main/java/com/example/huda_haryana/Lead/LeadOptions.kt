package com.example.huda_haryana.Lead

import android.content.Intent
import android.net.Uri
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
        val phone=intent.getStringExtra("phone")
        val email=intent.getStringExtra("email")
        binding.leadoptionAddnote.setOnClickListener {
            val intent=Intent(this,LeadNote::class.java).putExtra("id",id).putExtra("name",name)
            startActivity(intent)

        }
        binding.leadptionsAddlabel.setOnClickListener {
            val intent=Intent(this,AddLabels::class.java).putExtra("number",id)
            startActivity(intent)
        }
        binding.leadoptionNameTxt.text=name
        binding.callOption.setOnClickListener {
            val intent=Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
            startActivity(intent)
        }
        binding.mailOption.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            val recipients = arrayOf(email)
            intent.putExtra(Intent.EXTRA_EMAIL, recipients)
            intent.type = "text/html"
            intent.setPackage("com.google.android.gm")
            startActivity(Intent.createChooser(intent, "Send mail"))
        }
        binding.messageOption.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW,Uri.fromParts("sms",phone,null))
            startActivity(intent)
        }
        binding.leadoptionsAddtask.setOnClickListener {
            val intent=Intent(this,AddTask::class.java)
            startActivity(intent)
        }

    }
}
