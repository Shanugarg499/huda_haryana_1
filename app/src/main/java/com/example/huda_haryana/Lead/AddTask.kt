package com.example.huda_haryana.Lead

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.ActivityAddTaskBinding

class AddTask : AppCompatActivity() {
    lateinit var binding: ActivityAddTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_add_task)
        binding.leadnoteToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.leadnoteToolbar.inflateMenu(R.menu.task_menu)
        binding.leadnoteToolbar.setOnMenuItemClickListener{
            when(it.itemId){
                R.id.add_task->{
                    val intent= Intent(this,SetTask::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }
}
