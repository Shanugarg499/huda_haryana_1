package com.example.huda_haryana.mybusiness

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.FragmentNotesBinding
import com.facebook.FacebookSdk
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NotesActivity : AppCompatActivity() {
    lateinit var binding: FragmentNotesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_notes)
        binding.toolbarNotes.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.floatingActionButtonNotes.setOnClickListener {
            startActivity(Intent(this, NewNote::class.java))
        }
        var list = mutableListOf<NoteData>()
        binding.noteRecyler.layoutManager = LinearLayoutManager(this)
        val acct = GoogleSignIn.getLastSignedInAccount(FacebookSdk.getApplicationContext())
        val ref = FirebaseDatabase.getInstance().getReference("User").child(acct?.id!!).child("Notes")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    list = mutableListOf()
                    for (i in p0.children) {
                        val data = i.getValue(NoteData::class.java)
                        list.add(data!!)
                    }

                }
                if (list.size == 0) {
                    binding.noteRecyler.visibility=View.GONE
                    binding.noNoteImg.visibility = View.VISIBLE
                    binding.noNoteTxt.visibility = View.VISIBLE
                } else {
                    binding.noNoteTxt.visibility=View.GONE
                    binding.noNoteImg.visibility=View.GONE
                    binding.noteRecyler.visibility=View.VISIBLE
                    binding.noteRecyler.adapter = NoteAdapter(list)
                }
            }

        })
    }
}
