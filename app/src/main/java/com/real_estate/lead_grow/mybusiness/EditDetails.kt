package com.real_estate.lead_grow.mybusiness

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.real_estate.lead_grow.R
import com.real_estate.lead_grow.databinding.ActivityEditDetailsBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EditDetails : AppCompatActivity() {
    lateinit var binding: ActivityEditDetailsBinding
    var business: String = ""
    var desc: String = ""
    var website: String = ""
    var address: String = ""
    var operating: String = ""
    var phone: String = ""
    var whatsapp: String = ""
    var email: String = ""
    var moredetails: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_details)
        binding.toolbarEditdetails.setNavigationOnClickListener {
            onBackPressed()
        }
        val accnt= GoogleSignIn.getLastSignedInAccount(applicationContext)
        val mref=FirebaseDatabase.getInstance().getReference("User").child(accnt?.id!!).child("UserData")
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.add_details_dia)
        val title = dialog.findViewById<TextView>(R.id.title_dia)
        val edit = dialog.findViewById<EditText>(R.id.details_et)
        var getdata: UserData = UserData()
        mref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    getdata = p0.getValue(UserData::class.java)!!
                    binding.bussinessData.setText(getdata.businessname)
                    binding.descData.setText(getdata.description)
                    binding.addressData.setText(getdata.address)
                    binding.emailData.setText(getdata.email)
                    binding.moredetailsData.setText(getdata.moredetails)
                    binding.phoneData.setText(getdata.phone)
                    binding.websiteData.setText(getdata.website)
                    binding.operatingData.setText(getdata.opening)
                    binding.whatsappData.setText(getdata.whatsapp)
                }
            }

        })
        binding.bussinessName.setOnClickListener {
            edit.setText(getdata.businessname)
            title.setText("Business")
            dialog.show()
        }
        binding.description.setOnClickListener {
            edit.setText(getdata.description)
            title.setText("Description")
            dialog.show()
        }
        binding.website.setOnClickListener {
            edit.setText(getdata.website)
            title.setText("Website")
            dialog.show()
        }
        binding.address.setOnClickListener {
            edit.setText(getdata.address)
            title.setText("Address")
            dialog.show()
        }
        binding.operatingHours.setOnClickListener {
            edit.setText(getdata.opening)
            title.setText("Operating Hours")
            dialog.show()
        }
        binding.phoneNumber.setOnClickListener {
            edit.setText(getdata.phone)
            title.setText("Phone Number")
            dialog.show()
        }
        binding.whatsappNumber.setOnClickListener {
            edit.setText(getdata.whatsapp)
            title.setText("WhatsApp Number")
            dialog.show()
        }
        binding.email.setOnClickListener {
            edit.setText(getdata.email)
            title.setText("Email")
            dialog.show()
        }
        binding.moreDetails.setOnClickListener {
            edit.setText(getdata.moredetails)
            title.setText("More Details")
            dialog.show()
        }
        dialog.findViewById<Button>(R.id.cancel).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.ok).setOnClickListener {
            val text = edit.text.toString()
            when (title.text) {
                "Business" -> {
                    business = text
                    mref.child("businessname").setValue(business)

                }
                "Description" -> {
                    desc = text
                    mref.child("description").setValue(desc)
                }
                "Website" -> {
                    website = text
                    mref.child("website").setValue(website)

                }
                "Address" -> {
                    address = text
                    mref.child("address").setValue(address)

                }
                "Operating Hours" -> {
                    operating = text
                    mref.child("opening").setValue(operating)

                }
                "Phone Number" -> {
                    phone = text
                    mref.child("phone").setValue(phone)
                }
                "WhatsApp Number" -> {
                    whatsapp = text
                    mref.child("whatsapp").setValue(whatsapp)
                }
                "Email" -> {
                    email = text
                    mref.child("email").setValue(email)

                }
                "More Details" -> {
                    moredetails = text
                    mref.child("moredetails").setValue(moredetails)
                }
            }
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

    }
}
