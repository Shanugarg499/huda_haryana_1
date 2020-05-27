package com.example.huda_haryana.Lead

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.LeadAddlabelBinding
import com.example.huda_haryana.order_to_database
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.FirebaseDatabase
import java.io.Serializable

class AddLabels: AppCompatActivity() {
    lateinit var binding: LeadAddlabelBinding
//    lateinit var online_list:MutableList<order_to_database>
//    lateinit var referral_list:MutableList<order_to_database>
//    lateinit var direct:MutableList<order_to_database>
//    lateinit var sms_list:MutableList<order_to_database>
//    lateinit var newspaper_list:MutableList<order_to_database>
//    lateinit var pamphlets_list:MutableList<order_to_database>
//    lateinit var radio_list:MutableList<order_to_database>
//    lateinit var telecalling_list:MutableList<order_to_database>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.lead_addlabel)
//        val id = intent.getStringExtra("number")
        val obj=intent.getSerializableExtra("object") as order_to_database
        val accnt= GoogleSignIn.getLastSignedInAccount(applicationContext)
        val mref = FirebaseDatabase.getInstance().getReference("User").child(accnt?.id!!).child("Leads").child(obj.key).child("Labels")
        binding.toolbarAddlabels.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.addTolabels.setOnClickListener {
            val labellist = mutableListOf<LabelData>()
            var online_list= false
            var referral_list=false
            var direct= false
            var sms_list= false
            var newspaper_list= false
            var pamphlets_list= false
            var radio_list= false
            var telecalling_list= false
            val ref=FirebaseDatabase.getInstance().getReference("User").child(accnt.id!!).child("LabelLead")
            if (binding.directCheck.isChecked) {
                labellist.add(LabelData("DIRECT", "55C177"))
                direct=true
            }
            if (binding.newspaperCheck.isChecked) {
                labellist.add(LabelData("NEWSPAPER ADS", "FF6E40"))
                newspaper_list=true
            }
            if (binding.onlineCheck.isChecked) {
                labellist.add(LabelData("ONLINE INQUIRY", "151212"))
                online_list=true
            }
            if (binding.pamphletCheck.isChecked) {
                labellist.add(LabelData("PAMPHLETS", "5BA10C"))
                pamphlets_list=true
            }
            if (binding.referralCheck.isChecked) {
                labellist.add(LabelData("REFERRALS", "C63232"))
                referral_list=true
            }
            if (binding.smsCheck.isChecked) {
                labellist.add(LabelData("SMS CAMPAIGN", "3F51B5"))
                sms_list=true
            }
            if (binding.telecallingCheck.isChecked) {
                labellist.add(LabelData("TELECALLING", "646715"))
                telecalling_list=true
            }
            if (binding.radioCheck.isChecked) {
                labellist.add(LabelData("RADIO/TELEVISION ADS", "40C4FF"))
                radio_list=true
            }
            val data = ListData(labellist)
            mref.setValue(data)
            val labelLeadData=LabelLeadData(online_list,referral_list,direct,sms_list,newspaper_list,pamphlets_list,radio_list,telecalling_list,obj.key)
            ref.child(obj.key).setValue(labelLeadData)
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
        }

    }
}
