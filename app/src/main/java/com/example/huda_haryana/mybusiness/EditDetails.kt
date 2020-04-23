package com.example.huda_haryana.mybusiness

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.ActivityEditDetailsBinding

class EditDetails : AppCompatActivity() {
    lateinit var binding: ActivityEditDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_details)
        binding.toolbarEditdetails.setNavigationOnClickListener {
            onBackPressed()
        }

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.add_details_dia)
        val title=dialog.findViewById<TextView>(R.id.title_dia)
        val edit= dialog.findViewById<EditText>(R.id.details_et)
        binding.bussinessName.setOnClickListener {
            edit.setText("")
            title.setText("Business")
            dialog.show()
        }
        binding.description.setOnClickListener {
            edit.setText("")
            title.setText("Description")
            dialog.show()
        }
        binding.website.setOnClickListener {
            edit.setText("")
            title.setText("Website")
            dialog.show()
        }
        binding.address.setOnClickListener {
            edit.setText("")
            title.setText("Address")
            dialog.show()
        }
        binding.operatingHours.setOnClickListener {
            edit.setText("Operating Hours")
            title.setText("Website")
            dialog.show()
        }
        binding.phoneNumber.setOnClickListener {
            edit.setText("")
            title.setText("Phone Number")
            dialog.show()
        }
        binding.whatsappNumber.setOnClickListener {
            edit.setText("")
            title.setText("WhatsApp Number")
            dialog.show()
        }
        binding.email.setOnClickListener {
            edit.setText("")
            title.setText("Email")
            dialog.show()
        }
        binding.moreDetails.setOnClickListener {
            edit.setText("")
            title.setText("More Details")
            dialog.show()
        }
        dialog.findViewById<Button>(R.id.cancel).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.ok).setOnClickListener {
            val text=edit.text.toString()
            Toast.makeText(this,text,Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
    }
}
