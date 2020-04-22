package com.example.huda_haryana.mybusiness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.FragmentNotesBinding

class NotesActivity : AppCompatActivity() {
    lateinit var binding:FragmentNotesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.fragment_notes)
        binding.toolbarNotes.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
