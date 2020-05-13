package com.example.huda_haryana.Lead

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.ActivityAddTaskBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
        val mref = FirebaseDatabase.getInstance().getReference("leads").child(id!!).child("Alarm")
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
                    for (i in 0 until list.size) {
                        for (j in i until list.size) {
                            if (list[i].date > list[j].date) {
                                val t = list[i]
                                list[i] = list[j]
                                list[j] = t
                            }
                        }
                    }
                    binding.tasksRecycler.adapter = AlarmAdapter(list)
                }
            }

        })

    }


}

