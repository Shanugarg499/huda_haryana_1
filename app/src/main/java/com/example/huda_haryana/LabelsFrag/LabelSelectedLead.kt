package com.example.huda_haryana.LabelsFrag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.ActivityLabelSelectedLeadBinding
import com.example.huda_haryana.fragments_bottom.ab.LeadsAdpater
import com.example.huda_haryana.fragments_bottom.ab.data_lead
import com.example.huda_haryana.order_to_database
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LabelSelectedLead : AppCompatActivity() {
    lateinit var binding:ActivityLabelSelectedLeadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_label_selected_lead)
        binding.labelSelectedRecycler.layoutManager=LinearLayoutManager(this)
        val accnt = GoogleSignIn.getLastSignedInAccount(applicationContext)
//        val mref=FirebaseDatabase.getInstance().getReference("User").child(accnt?.id!!).child("Leads")
        for(i in list_sent){
            Log.i("1",i)
        }
        for(i in data_lead){
            Log.i("12",i.key)
        }
        val recycler_list= arrayListOf<order_to_database>()
        if(list_sent.isNotEmpty()) {
            for (i in list_sent) {
                for (j in data_lead) {
                    if (i == j.key) {
                        recycler_list.add(j)
                        break
                    }
                }
            }
            binding.labelSelectedRecycler.adapter=LeadsAdpater(this,recycler_list)
        }

    }
}
