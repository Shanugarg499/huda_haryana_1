package com.example.huda_haryana.Lead

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.ActivityLeadOptionsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LeadOptions : AppCompatActivity() {
    lateinit var binding: ActivityLeadOptionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lead_options)
        binding.toolbarLeadoptions.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.toolbarLeadoptions.inflateMenu(R.menu.bottom_navigation_menu)
        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")
        val email = intent.getStringExtra("email")
        binding.leadoptionAddnote.setOnClickListener {
            val intent = Intent(this, LeadNote::class.java).putExtra("id", id).putExtra("name", name)
            startActivity(intent)

        }
        binding.horRecyclerLeadOption.setOnClickListener {
            val intent = Intent(this, AddLabels::class.java).putExtra("number", id)
            startActivity(intent)
        }
        binding.leadptionsAddlabel.setOnClickListener {
            val intent = Intent(this, AddLabels::class.java).putExtra("number", id)
            startActivity(intent)
        }
        binding.leadoptionNameTxt.text = name
        binding.callOption.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
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
            val intent = Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phone, null))
            startActivity(intent)
        }
        binding.leadoptionsAddtask.setOnClickListener {
            val intent = Intent(this, AddTask::class.java).putExtra("id",id).putExtra("name",name)
            startActivity(intent)
        }
        binding.horRecyclerLeadOption.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val mref=FirebaseDatabase.getInstance().getReference("leads").child(id!!).child("Labels").child("list")
        mref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val list= mutableListOf<LabelData>()
                if(p0.exists()) {
                    for (i in p0.children) {
                        val data = i.getValue(LabelData::class.java)
                        list.add(data!!)
                    }
                    binding.horRecyclerLeadOption.visibility= View.VISIBLE
                    binding.addLabelTxt.visibility=View.GONE
                    binding.horRecyclerLeadOption.adapter=AddLabelAdapter(list)
                }
            }

        })

    }
}
