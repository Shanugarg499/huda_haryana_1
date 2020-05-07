package com.example.huda_haryana.Lead

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.LeadAddlabelBinding
import com.google.firebase.database.FirebaseDatabase

class AddLabels : AppCompatActivity() {
    lateinit var binding: LeadAddlabelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.lead_addlabel)
        val id = intent.getStringExtra("number")
        val mref = FirebaseDatabase.getInstance().getReference("leads").child(id!!).child("Labels")
        binding.toolbarAddlabels.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.addTolabels.setOnClickListener {
            val labellist = mutableListOf<LabelData>()
            if (binding.directCheck.isChecked) {
                labellist.add(LabelData("DIRECT", "55C177"))
            }
            if (binding.newspaperCheck.isChecked) {
                labellist.add(LabelData("NEWSPAPER ADS", "FF6E40"))
            }
            if (binding.onlineCheck.isChecked) {
                labellist.add(LabelData("ONLINE INQUIRY", "151212"))
            }
            if (binding.pamphletCheck.isChecked) {
                labellist.add(LabelData("PAMPHLETS", "5BA10C"))
            }
            if (binding.referralCheck.isChecked) {
                labellist.add(LabelData("REFERRALS", "C63232"))
            }
            if (binding.smsCheck.isChecked) {
                labellist.add(LabelData("SMS CAMPAIGN", "3F51B5"))
            }
            if (binding.telecallingCheck.isChecked) {
                labellist.add(LabelData("TELECALLING", "646715"))
            }
            if (binding.radioCheck.isChecked) {
                labellist.add(LabelData("RADIO/TELEVISION ADS", "40C4FF"))
            }
            val data = ListData(labellist)
            mref.setValue(data)
            Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show()
        }

    }
}
