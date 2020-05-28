package com.example.huda_haryana.Lead

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.ActivityLeadOptionsBinding
import com.example.huda_haryana.order_to_database
import com.facebook.FacebookSdk
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LeadOptions : AppCompatActivity() {
    lateinit var binding: ActivityLeadOptionsBinding
    var typeData: TypeData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lead_options)
        binding.toolbarLeadoptions.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.toolbarLeadoptions.inflateMenu(R.menu.bottom_navigation_menu)
        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")
        val email = intent.getStringExtra("email")
        val obj = intent.getSerializableExtra("object") as order_to_database
        binding.leadoptionAddnote.setOnClickListener {
            val intent = Intent(this, LeadNote::class.java).putExtra("id", id).putExtra("name", name)
            startActivity(intent)

        }
        binding.horRecyclerLeadOption.setOnClickListener {
            val intent = Intent(this, AddLabels::class.java).putExtra("object", obj)
            startActivity(intent)
        }
        binding.leadptionsAddlabel.setOnClickListener {
            val intent = Intent(this, AddLabels::class.java).putExtra("object", obj)
            startActivity(intent)
        }
        binding.leadoptionNameTxt.text = name
        binding.callOption.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
            startActivity(intent)
        }
        binding.mailOption.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            val recipients = arrayOf(email)
            intent.putExtra(Intent.EXTRA_EMAIL, recipients)
            intent.type = "text/html"
            intent.setPackage("com.google.android.gm")
            startActivity(Intent.createChooser(intent, "Send mail"))
        }
        binding.messageOption.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phone, null))
            startActivity(intent)
        }
        binding.leadoptionsAddtask.setOnClickListener {
            val intent = Intent(this, AddTask::class.java).putExtra("id", id).putExtra("name", name)
            startActivity(intent)
        }
        binding.horRecyclerLeadOption.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val acct = GoogleSignIn.getLastSignedInAccount(FacebookSdk.getApplicationContext())
        val mref = FirebaseDatabase.getInstance().getReference("User").child(acct?.id!!).child("Leads").child(id!!).child("Labels").child("list")
        mref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val list = mutableListOf<LabelData>()
                if (p0.exists()) {
                    for (i in p0.children) {
                        val data = i.getValue(LabelData::class.java)
                        list.add(data!!)
                    }
                    binding.horRecyclerLeadOption.adapter = AddLabelAdapter(list)
                }
            }

        })
        val dia = Dialog(this as LeadOptions)
        dia.setContentView(R.layout.add_details_dia)
        binding.leadoptionAddbudget.setOnClickListener {
            dia.findViewById<TextView>(R.id.title_dia).setText("Add Budget")
            dia.findViewById<EditText>(R.id.details_et)
            dia.show()
        }
        val budgetref = FirebaseDatabase.getInstance().getReference("User").child(acct.id!!).child("Leads").child(id).child("Budget").child("Budgetvalue")

        dia.findViewById<Button>(R.id.ok).setOnClickListener {

            val txt = dia.findViewById<EditText>(R.id.details_et).text.toString()
            if (txt == "") {
                Toast.makeText(applicationContext, "Enter Something", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            budgetref.setValue(txt)
            dia.dismiss()
        }
        dia.findViewById<Button>(R.id.cancel).setOnClickListener {
            dia.dismiss()
        }
        budgetref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val data = p0.getValue().toString()
                if (data != "null") {
                    binding.budgetView.setText(data)
                    dia.findViewById<EditText>(R.id.details_et).setText(data)
                }
            }

        })
        val typeref = FirebaseDatabase.getInstance().getReference("User").child(acct.id!!).child("Leads").child(id).child("Type")
        val items = arrayListOf<String>("ADD PROPERTY TYPE", "BUY", "SELL", "RENT")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.leadoptionSpinner.adapter = adapter
        typeref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    typeData = p0.getValue(TypeData::class.java)!!
                    binding.leadoptionSpinner.setSelection(typeData!!.pos.toInt())
                }
            }

        })

        binding.leadoptionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    1 -> {
                        typeref.setValue(TypeData("BUY", "1"))
                    }
                    2 -> {
                        typeref.setValue(TypeData("SELL", "2"))
                    }
                    3 -> {
                        typeref.setValue(TypeData("RENT", "3"))
                    }
                }
            }

        }
    }
}
