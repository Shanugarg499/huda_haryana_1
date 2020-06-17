package com.leadgrow.estate.Lead

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.leadgrow.estate.R
import com.leadgrow.estate.databinding.ActivityLeadOptionsBinding
import com.leadgrow.estate.order_to_database
import com.facebook.FacebookSdk
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LeadOptions : AppCompatActivity() {
    lateinit var binding: ActivityLeadOptionsBinding
    lateinit var check: String
    var typeData: TypeData? = null
    var type: String = ""
    var subtype: String = ""
    var second_type: String = ""
    var second_sub: String = ""
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
        val r_Cref = FirebaseDatabase.getInstance().getReference("User").child(acct.id!!).child("Leads").child(id).child("r_C")
        r_Cref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    val data=p0.value
                    binding.displayType1.setText(data.toString())
                }
            }

        })
        val budgetref = FirebaseDatabase.getInstance().getReference("User").child(acct.id!!).child("Leads").child(id).child("figures")

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
        binding.displayType1.visibility=View.GONE
        binding.displayType2.visibility=View.GONE
        binding.displayType3.visibility=View.GONE
        val typeref = FirebaseDatabase.getInstance().getReference("User").child(acct.id!!).child("Leads").child(id).child("Type")
        typeref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    val data=p0.getValue(TypeData::class.java)!!
                    binding.displayType1.visibility=View.VISIBLE
                    binding.displayType2.visibility=View.VISIBLE
                    //binding.displayType1.setText(data.type)
                    binding.displayType2.setText(data.subtype)
//                    if(data.type==""){
//                        binding.displayType1.visibility= View.GONE
//                    }
//                    else{
//                        binding.displayType1.visibility=View.VISIBLE
//
//                    }
//                    if(data.subtype==""){
//                        binding.displayType2.visibility= View.GONE
//                    }
//                    else{
//                        binding.displayType2.visibility=View.VISIBLE
//
//                    }
                    if(data.second_sub!="") {
                        binding.displayType3.visibility = View.VISIBLE
                    }
                    binding.displayType3.setText(data.second_sub)

                }
            }

        })
        val propref = FirebaseDatabase.getInstance().getReference("User").child(acct.id!!).child("Leads").child(id).child("Allproperties").child("Value")
        val alertDialog = AlertDialog.Builder(this)
        val alert2 = AlertDialog.Builder(this)
        val alert3 = AlertDialog.Builder(this)
        val items = arrayListOf<String>("RESIDENTIAL", "COMMERCIAL")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items)
        val items2 = arrayListOf<String>("BUY", "SELL", "RENT")
        val adapter2 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items2)
        val item3 = arrayListOf<String>("SHOPS", "SCO", "SCF", "Other")
        val adapter3 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item3)
        val alert5 = AlertDialog.Builder(this)
        val items5 = arrayListOf<String>("36sq. Yards","60sq. Yards", "100sq. Yards", "160sq. Yards", "250sq. Yards","350sq. Yards","500sq. yards", "1000sq. yards","Others")
        val adapter5 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items5)
        alert5.setAdapter(adapter5, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    0->{
                        second_sub="36sq. Yards"
                    }
                    1 -> {
                        second_sub = "60sq. Yards"
                    }
                    2 -> {
                        second_sub = "100sq. Yards"
                    }
                    3 -> {
                        second_sub = "160sq. Yards"
                    }
                    4 -> {
                        second_sub = "250sq. Yards"
                    }
                    5->{
                        second_sub="350sq. Yards"
                    }
                    6 -> {
                        second_sub = "500sq. Yards"
                    }
                    7 -> {
                        second_sub = "1000sq. Yards"
                    }
                    8->{
                        second_sub="Others"
                    }

                }
                typeref.setValue(TypeData(type, subtype, second_sub))
                dialog?.dismiss()
            }

        })
        val alert6 = AlertDialog.Builder(this)
        val items6 = arrayListOf<String>("Studio Apartment", "1BHK", "2BHK", "2+1 BHK", "3BHK", "3+1 BHK", "4BHK", "PentHouse","Others")
        val adapter6 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items6)
        alert6.setAdapter(adapter6, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    0 -> {
                        second_sub = "Studio Apartment"
                    }
                    1 -> {
                        second_sub = "1BHK"
                    }
                    2 -> {
                        second_sub = "2BHK"

                    }
                    3 -> {
                        second_sub = "2+1 BHK"
                    }
                    4 -> {
                        second_sub = "3BHK"
                    }
                    5 -> {
                        second_sub = "3+1 BHK"
                    }
                    6 -> {
                        second_sub = "4BHK"
                    }
                    7 -> {
                        second_sub = "PentHouse"
                    }
                    8->{
                        second_sub="Others"
                    }
                }
                typeref.setValue(TypeData(type, subtype, second_sub))
                dialog?.dismiss()
            }

        })
        val alert4 = AlertDialog.Builder(this)
        val items4 = arrayListOf<String>("PLOT", "VILLA", "KOTHI", "FLAT", "Other")
        val adapter4 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items4)
        alert4.setAdapter(adapter4, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    0 -> {
                        subtype = "PLOT"
                        alert5.show()
                        dialog?.dismiss()
                        return
                    }
                    1 -> {
                        subtype = "VILLA"
                    }
                    2 -> {
                        subtype = "KOTHI"
                    }
                    3 -> {
                        subtype = "FLAT"
                        alert6.show()
                        dialog?.dismiss()
                        return
                    }
                    4 -> {
                        subtype = "Other"
                    }
                }
                typeref.setValue(TypeData(type,  subtype))
                dialog?.dismiss()
            }

        })
        binding.leadoptionAllprop.setOnClickListener {
            alert2.show()
        }
        binding.displayAllprop.visibility=View.GONE
        propref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    binding.displayAllprop.visibility=View.VISIBLE
                    binding.displayAllprop.setText(p0.getValue().toString())
                }
            }

        })
        alert2.setAdapter(adapter2, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    0 -> {
                        second_type = "BUY"
                    }
                    1 -> {
                        second_type = "SELL"
                    }
                    2 -> {
                        second_type = "RENT"
                    }
                }
                propref.setValue(second_type)
                dialog?.dismiss()
            }

        })
        alert3.setAdapter(adapter3, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    0 -> {

                        subtype = "SHOPS"
                    }
                    1 -> {
                        subtype = "SCO"
                    }
                    2 -> {
                        subtype = "SCF"
                    }
                    3 -> {
                        subtype = "Other"
                    }
                }
                typeref.setValue(TypeData(type, subtype))
                dialog?.dismiss()

            }

        })
        binding.leadoptionAddtype.setOnClickListener {
            alertDialog.show()
        }
        alertDialog.setAdapter(adapter, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    0 -> {
                        type = "RESIDENTIAL"
                        r_Cref.setValue(type)
                        dialog?.dismiss()
                        alert4.show()
                    }
                    1 -> {
                        type = "COMMERCIAL"
                        r_Cref.setValue(type)
                        dialog?.dismiss()
                        alert3.show()
                    }
                }
                dialog?.dismiss()
            }

        })
    }
}
