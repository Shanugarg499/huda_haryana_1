package com.leadgrow.estate.Lead

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.leadgrow.estate.R
import com.leadgrow.estate.databinding.ActivityAddTaskBinding
import com.facebook.FacebookSdk
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

lateinit var list: MutableList<AlarmData>

class AddTask : AppCompatActivity() {
    lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_task)
        binding.leadnoteToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        val id=intent.getStringExtra("id")
        val name=intent.getStringExtra("name")
        binding.leadnoteToolbar.inflateMenu(R.menu.task_menu)
        binding.tasksRecycler.layoutManager = LinearLayoutManager(this)
        binding.leadnoteToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_task -> {
                    val intent = Intent(this, SetTask::class.java).putExtra("id",id).putExtra("name",name)
                    startActivity(intent)
                }
            }
            true
        }
        val acct = GoogleSignIn.getLastSignedInAccount(FacebookSdk.getApplicationContext())
        val mref = FirebaseDatabase.getInstance().getReference("User").child(acct?.id!!).child("Leads").child(id!!).child("Alarm")
        mref.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                list = mutableListOf()
                if (p0.exists()) {
                    for (i in p0.children) {
                        val data = i.getValue(AlarmData::class.java)
                        list.add(data!!)
                    }
                    val curr= Date().time
                    list.reverse()
                    binding.tasksRecycler.adapter = AlarmAdapter(list,curr)
                }
            }

        })

    }


}

