package com.example.huda_haryana.Lead

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.ActivityAddTaskBinding
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
        binding.leadnoteToolbar.inflateMenu(R.menu.task_menu)
        binding.tasksRecycler.layoutManager = LinearLayoutManager(this)
        binding.leadnoteToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_task -> {
                    val intent = Intent(this, SetTask::class.java)
                    startActivity(intent)
                }
            }
            true
        }
        val mref = FirebaseDatabase.getInstance().getReference("Alarm")
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
//                    for(i in list){
//                        if(i.date.toLong()>Date().time){
//                            setalarm(i.date.toLong())
//                            break
//                        }
//                    }
                    binding.tasksRecycler.adapter = AlarmAdapter(list)
                }
            }

        })

    }


}

