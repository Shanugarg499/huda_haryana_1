package com.example.huda_haryana.LabelsFrag

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.huda_haryana.Lead.LabelLeadData
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.FragmentLabelsBinding
import com.example.huda_haryana.order_to_database
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
lateinit var list_sent:MutableList<String>

class LabelFrag : Fragment() {
    lateinit var binding: FragmentLabelsBinding
    lateinit var list:MutableList<LabelLeadData>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_labels, container, false)
        val ref = FirebaseDatabase.getInstance().getReference("LabelLead")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                     list = mutableListOf<LabelLeadData>()
                    for (i in p0.children) {
                        val data =i.getValue(LabelLeadData::class.java)
                        list.add(data!!)
                    }
                }
            }

        })

        binding.online.setOnClickListener {
            list_sent= mutableListOf<String>()
           for(i in list){
               if(i.online){
                   list_sent.add(i.key)
               }
           }
            val intent=Intent(context,LabelSelectedLead::class.java)
            startActivity(intent)
        }
        binding.radioTel.setOnClickListener {
            list_sent= mutableListOf<String>()
            for(i in list){
                if(i.radio){
                    list_sent.add(i.key)
                }
            }
            val intent=Intent(context,LabelSelectedLead::class.java)
            startActivity(intent)
        }
        binding.newspaperCheck.setOnClickListener {
            list_sent= mutableListOf<String>()
            for(i in list){
                if(i.newspaper){
                    list_sent.add(i.key)
                }
            }
            val intent=Intent(context,LabelSelectedLead::class.java)
            startActivity(intent)
        }
        binding.directLabel.setOnClickListener {

            Toast.makeText(context ,"ok", Toast.LENGTH_SHORT).show()
            list_sent= mutableListOf<String>()
            for(i in list){
                if(i.direct){
                    list_sent.add(i.key)
                }
            }


            val intent=Intent(context,LabelSelectedLead::class.java)
            startActivity(intent)
        }
        binding.referrals.setOnClickListener {
            list_sent= mutableListOf<String>()
            for(i in list){
                if(i.referral){
                    list_sent.add(i.key)
                }
            }
            val intent=Intent(context,LabelSelectedLead::class.java)
            startActivity(intent)
        }

        binding.telecalling.setOnClickListener {
            list_sent= mutableListOf<String>()
            for(i in list){
                if(i.telecalling){
                    list_sent.add(i.key)
                }
            }
            val intent=Intent(context,LabelSelectedLead::class.java)
            startActivity(intent)
        }
        binding.smsCampaign.setOnClickListener {
            list_sent= mutableListOf<String>()
            for(i in list){
                if(i.sms){
                    list_sent.add(i.key)
                }
            }
            val intent=Intent(context,LabelSelectedLead::class.java)
            startActivity(intent)
        }
        binding.pamphlets.setOnClickListener {
            list_sent= mutableListOf<String>()
            for(i in list){
                if(i.pamphlet){
                    list_sent.add(i.key)
                }
            }
            val intent=Intent(context,LabelSelectedLead::class.java)
            startActivity(intent)
        }
        return binding.root
    }

}
