package com.example.huda_haryana.mybusiness

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.ActivityNewNoteBinding
import com.google.firebase.database.FirebaseDatabase

class NewNote : AppCompatActivity() {
    lateinit var binding: ActivityNewNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_note)
        val ref = FirebaseDatabase.getInstance().getReference("Notes")
        binding.okNewnote.setOnClickListener {
            val key = ref.push().key
            val newnotedata=NoteData(binding.newnoteEt.text.toString())
            ref.child(key!!).setValue(newnotedata)
            onBackPressed()
        }
    }
}
