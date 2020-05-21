package com.example.huda_haryana.Lead

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.ActivityLeadNoteBinding
import com.facebook.FacebookSdk
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class LeadNote : AppCompatActivity() {
    lateinit var binding: ActivityLeadNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lead_note)
        binding.leadnoteToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val acct = GoogleSignIn.getLastSignedInAccount(FacebookSdk.getApplicationContext())
        val mref = FirebaseDatabase.getInstance().getReference("User").child(acct?.id!!).child(id!!).child("Notes")
        binding.leadnoteToolbar.inflateMenu(R.menu.bottom_navigation_menu)
        binding.leadnoteRecyler.layoutManager = LinearLayoutManager(this)
        val getdate = Calendar.getInstance().time
        val dateformat = SimpleDateFormat("dd-MMM-yyyy")
        val date = dateformat.format(getdate)
        binding.leadnoteDate.text = date
        binding.leadnoteName.text = name
        binding.leadnoteButton.setOnClickListener {
            val notetext = binding.leadnoteAddnoteEt.text.toString()
            val data = LeadNoteData(date, notetext)
            val key = mref.push().key
            mref.child(key!!).setValue(data)
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
            binding.leadnoteAddnoteEt.setText("")
        }

        mref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val list = mutableListOf<LeadNoteData>()
                    for (i in p0.children) {
                        val data = i.getValue(LeadNoteData::class.java)
                        list.add(data!!)
                    }
                    binding.leadnoteRecyler.adapter = LeadNoteAdapter(list)
                }
            }

        })
    }
}
