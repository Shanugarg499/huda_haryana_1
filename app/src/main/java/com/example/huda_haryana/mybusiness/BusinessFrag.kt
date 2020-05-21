package com.example.huda_haryana.mybusiness

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.FragmentMybusinessBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class BusinessFrag : Fragment() {
    lateinit var binding: FragmentMybusinessBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mybusiness, container, false)
        binding.addLogo.setOnClickListener {
            val intent = Intent(context, EditDetails::class.java)
            startActivity(intent)
        }
        binding.editDetails.setOnClickListener {
            val intent = Intent(context, EditDetails::class.java)
            startActivity(intent)
        }
        binding.notes.setOnClickListener {
            val intent = Intent(context, NotesActivity::class.java)
            startActivity(intent)
        }
        val accnt= GoogleSignIn.getLastSignedInAccount(context)
        val mref=FirebaseDatabase.getInstance().getReference(accnt?.familyName+accnt?.givenName+accnt?.displayName+"leads").child("UserData")
        mref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    val data=p0.getValue(UserData::class.java)!!
                    binding.businessName.setText(data.businessname)
                    binding.addressBusiness.setText(data.address)
                    binding.moreBusiness.setText(data.moredetails)
                    binding.emailBusiness.setText(data.email)
                    binding.openingBusiness.setText(data.opening)
                    binding.descBusiness.setText(data.description)
                    binding.phoneBusiness.setText(data.phone)
                    binding.websiteBusiness.setText(data.website)
                    binding.whatsappBusiness.setText(data.whatsapp)
                }
            }

        })
        return binding.root
    }

}
