package com.example.huda_haryana.LabelsFrag

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.huda_haryana.Lead.LabelLeadData
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.FragmentLabelsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

lateinit var list_sent: MutableList<String>

class LabelFrag : Fragment() {
    lateinit var binding: FragmentLabelsBinding
    var list: MutableList<LabelLeadData>? = null
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
                        val data = i.getValue(LabelLeadData::class.java)
                        list!!.add(data!!)
                    }
                    var online = 0
                    var referral = 0
                    var direct = 0
                    var sms_campaign = 0
                    var newspaper = 0
                    var pamphlet = 0
                    var radio = 0
                    var telecalling = 0
                    for (i in list!!) {
                        if (i.online) {
                            online++;
                        }
                        if (i.telecalling) {
                            telecalling++
                        }
                        if (i.sms) {
                            sms_campaign++
                        }
                        if (i.pamphlet) {
                            pamphlet++
                        }
                        if (i.radio) {
                            radio++
                        }
                        if (i.newspaper) {
                            newspaper++
                        }
                        if (i.direct) {
                            direct++
                        }
                        if (i.referral) {
                            referral++
                        }
                        binding.onlineCus.setText(online.toString() + " Customers")
                        binding.referralCus.setText(referral.toString() + " Customers")
                        binding.directCus.setText(direct.toString() + " Customers")
                        binding.smsCus.setText(sms_campaign.toString() + " Customers")
                        binding.newsCus.setText(newspaper.toString() + " Customers")
                        binding.pamphletCus.setText(pamphlet.toString() + " Customers")
                        binding.radioCus.setText(radio.toString() + " Customers")
                        binding.telecallingCus.setText(telecalling.toString() + " Customers")
                    }
                }
            }

        })
        binding.online.setOnClickListener {
            list_sent = mutableListOf<String>()
            if(list==null){
                Toast.makeText(context,"Net is Slow",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            for (i in list!!) {
                if (i.online) {
                    list_sent.add(i.key)
                }
            }
            val intent = Intent(context, LabelSelectedLead::class.java)
            startActivity(intent)
        }
        binding.radioTel.setOnClickListener {
            list_sent = mutableListOf<String>()
            if(list==null){
                Toast.makeText(context,"Net is Slow",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            for (i in list!!) {
                if (i.radio) {
                    list_sent.add(i.key)
                }
            }
            val intent = Intent(context, LabelSelectedLead::class.java)
            startActivity(intent)
        }
        binding.newspaperCheck.setOnClickListener {
            list_sent = mutableListOf<String>()
            if(list==null){
                Toast.makeText(context,"Net is Slow",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            for (i in list!!) {
                if (i.newspaper) {
                    list_sent.add(i.key)
                }
            }
            val intent = Intent(context, LabelSelectedLead::class.java)
            startActivity(intent)
        }
        binding.directLabel.setOnClickListener {
            list_sent = mutableListOf<String>()
            if(list==null){
                Toast.makeText(context,"Net is Slow",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            for (i in list!!) {
                if (i.direct) {
                    list_sent.add(i.key)
                }
            }


            val intent = Intent(context, LabelSelectedLead::class.java)
            startActivity(intent)
        }
        binding.referrals.setOnClickListener {
            list_sent = mutableListOf<String>()
            if(list==null){
                Toast.makeText(context,"Net is Slow",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            for (i in list!!) {
                if (i.referral) {
                    list_sent.add(i.key)
                }
            }
            val intent = Intent(context, LabelSelectedLead::class.java)
            startActivity(intent)
        }

        binding.telecalling.setOnClickListener {
            list_sent = mutableListOf<String>()
            if(list==null){
                Toast.makeText(context,"Net is Slow",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            for (i in list!!) {
                if (i.telecalling) {
                    list_sent.add(i.key)
                }
            }
            val intent = Intent(context, LabelSelectedLead::class.java)
            startActivity(intent)
        }
        binding.smsCampaign.setOnClickListener {
            list_sent = mutableListOf<String>()
            if(list==null){
                Toast.makeText(context,"Net is Slow",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            for (i in list!!) {
                if (i.sms) {
                    list_sent.add(i.key)
                }
            }
            val intent = Intent(context, LabelSelectedLead::class.java)
            startActivity(intent)
        }
        binding.pamphlets.setOnClickListener {
            list_sent = mutableListOf<String>()
            if(list==null){
                Toast.makeText(context,"Net is Slow",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            for (i in list!!) {
                if (i.pamphlet) {
                    list_sent.add(i.key)
                }
            }
            val intent = Intent(context, LabelSelectedLead::class.java)
            startActivity(intent)
        }

        return binding.root
    }

}
