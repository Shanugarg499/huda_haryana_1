package com.example.huda_haryana.mybusiness

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
        val accnt = GoogleSignIn.getLastSignedInAccount(context)
        val mref = FirebaseDatabase.getInstance().getReference("User").child(accnt?.id!!).child("UserData")
        mref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val data = p0.getValue(UserData::class.java)!!
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

        val first_dialog = Dialog(requireContext())
        val second_dialog = Dialog(requireContext())
        second_dialog.setContentView(R.layout.business_card_dialog)
        first_dialog.setContentView(R.layout.business_first_dialog)
        binding.sendBusiness.setOnClickListener {
            first_dialog.show()
        }
        first_dialog.findViewById<TextView>(R.id.email_dialog).setOnClickListener {
            first_dialog.dismiss()
            second_dialog.show()
        }
        second_dialog.findViewById<Button>(R.id.second_dialog_ok).setOnClickListener {
            val email = second_dialog.findViewById<EditText>(R.id.entered_txt).text.toString()
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email))
            intent.putExtra(Intent.EXTRA_SUBJECT, binding.businessName.text)
            intent.putExtra(Intent.EXTRA_TEXT, binding.businessName.text.toString() + "\n" + binding.descBusiness.text.toString() + "\n" + binding.websiteBusiness.text.toString() + "\n" + binding.addressBusiness.text.toString() + "\n" + binding.openingBusiness.text.toString() + "\n" + binding.phoneBusiness.text.toString() + "\n" + binding.whatsappBusiness.text.toString() + "\n" + binding.emailBusiness.text.toString() + "\n" + binding.moreBusiness.text.toString())
            startActivity(Intent.createChooser(intent, "Choose:"))
        }
        return binding.root
    }

}
