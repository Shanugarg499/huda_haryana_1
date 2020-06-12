package com.real_estate.lead_grow.LabelsFrag

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.real_estate.lead_grow.Lead.LabelLeadData
import com.real_estate.lead_grow.R
import com.real_estate.lead_grow.databinding.FragmentLabelsBinding

lateinit var list_sent: MutableList<String>

class LabelFrag : Fragment() {
    lateinit var binding: FragmentLabelsBinding
    var list: MutableList<LabelLeadData>? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_labels, container, false)
        val pieChart = binding.pieChart
        val pientries = ArrayList<Entry>()
        val xval = ArrayList<String>()
        pieChart.setDescription("No. of Customers")
        pieChart.setDescriptionTextSize(14f)
        pieChart.setHoleColor(Color.WHITE)
        pieChart.holeRadius = 34f
        pieChart.centerText="Month"
        pieChart.transparentCircleRadius = 10f
        binding.noLabelTxt.visibility=View.VISIBLE
        binding.mainLayout.visibility=View.GONE

        val accnt = GoogleSignIn.getLastSignedInAccount(context)
        val ref = FirebaseDatabase.getInstance().getReference("User").child(accnt?.id!!).child("LabelLead")
        var online_index=-1
        var tele_index=-1
        var referral_index=-1
        var newspaper_index=-1
        var radio_index=-1
        var pamphlet_index=-1
        var direct_index=-1
        var sms_index=-1
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {

                    list = mutableListOf<LabelLeadData>()
                    for (i in p0.children) {
                        val data = i.getValue(LabelLeadData::class.java)
                        list!!.add(data!!)
                    }
                    var online = 0
                    var referral = 0
                    var direct = 0
                    var sms_campaign = 0
                    var newspaper = 0
                    var pamphlet = 0
                    var radio = 0
                    var telecalling = 0
                    for (i in list!!) {
                        if(i.online||i.telecalling||i.sms||i.pamphlet||i.radio||i.referral||i.newspaper||i.direct){
                            binding.mainLayout.visibility=View.VISIBLE
                            binding.noLabelTxt.visibility=View.GONE
                        }
                        if (i.online) {
                            online++;
                        }
                        if (i.telecalling) {
                            telecalling++
                        }
                        if (i.sms) {
                            sms_campaign++
                        }
                        if (i.pamphlet) {
                            pamphlet++
                        }
                        if (i.radio) {
                            radio++
                        }
                        if (i.newspaper) {
                            newspaper++
                        }
                        if (i.direct) {
                            direct++
                        }
                        if (i.referral) {
                            referral++
                        }
                        binding.onlineCus.setText(online.toString() + " Customers")
                        binding.referralCus.setText(referral.toString() + " Customers")
                        binding.directCus.setText(direct.toString() + " Customers")
                        binding.smsCus.setText(sms_campaign.toString() + " Customers")
                        binding.newsCus.setText(newspaper.toString() + " Customers")
                        binding.pamphletCus.setText(pamphlet.toString() + " Customers")
                        binding.radioCus.setText(radio.toString() + " Customers")
                        binding.telecallingCus.setText(telecalling.toString() + " Customers")

                    }
                    var index=0

                    if (online > 0) {
                        online_index=index
                        pientries.add(Entry(online.toFloat(), index))
                        xval.add(index, "Online")
                        index += 1
                    }
                    if (telecalling > 0) {
                        tele_index=index
                        pientries.add(Entry(telecalling.toFloat(), index))
                        xval.add(index, "Telecalling")
                        index+=1
                    }
                    if (sms_campaign > 0) {
                        sms_index=index
                        pientries.add(Entry(sms_campaign.toFloat(), index))
                        xval.add(index, "SMS Campaign")
                        index+=1
                    }
                    if(pamphlet>0) {
                        pamphlet_index=index
                        pientries.add(Entry(pamphlet.toFloat(), index))
                        xval.add(index, "Pamphlet")
                        index+=1
                    }
                    if(radio>0) {
                        radio_index=index
                        pientries.add(Entry(radio.toFloat(), index))
                        xval.add(index, "Radio")
                        index+=1
                    }
                    if(newspaper>0) {
                        newspaper_index=index
                        pientries.add(Entry(newspaper.toFloat(), index))
                        xval.add(index, "Newspaper")
                        index+=1
                    }
                    if(direct>0) {
                        direct_index=index
                        pientries.add(Entry(direct.toFloat(), index))
                        xval.add(index, "Direct")
                        index+=1
                    }
                    if(referral>0) {
                        referral_index=index
                        pientries.add(Entry(referral.toFloat(), index))
                        xval.add(index, "Referral")
                        index+=1
                    }
                    val pieDataSet = PieDataSet(pientries, "")
                    pieDataSet.setColors(arrayListOf(Color.parseColor("#c65b39"),Color.parseColor("#738b28"),Color.parseColor("#a239c6"),Color.parseColor("#daa520"),Color.parseColor("#b22222"),Color.parseColor("#ff1493"),Color.parseColor("#228b22"),Color.parseColor("#483d8b")))
                    val piedata = PieData(xval, pieDataSet)
                    piedata.setValueTextColor(Color.WHITE)
                    pieDataSet.sliceSpace = 3f
                    piedata.setValueTextSize(13f)
                    pieChart.data = piedata
                    pieDataSet.sliceSpace = 2f
                    pieChart.invalidate()
                }
            }

        })
        pieChart.setOnChartValueSelectedListener(object :OnChartValueSelectedListener{
            override fun onNothingSelected() {

            }

            override fun onValueSelected(e: Entry?, dataSetIndex: Int, h: Highlight?) {
                if(e?.xIndex==online_index){

                    list_sent = mutableListOf<String>()
                    if (list == null) {
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show()
                        return
                    }
                    for (i in list!!) {
                        if (i.online) {
                            list_sent.add(i.key)
                        }
                    }
                    val intent = Intent(context, LabelSelectedLead::class.java)
                    startActivity(intent)
                }
                if(e?.xIndex==sms_index){

                    list_sent = mutableListOf<String>()
                    if (list == null) {
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show()
                        return
                    }
                    for (i in list!!) {
                        if (i.sms) {
                            list_sent.add(i.key)
                        }
                    }
                    val intent = Intent(context, LabelSelectedLead::class.java)
                    startActivity(intent)
                }
                if(e?.xIndex==direct_index){

                    list_sent = mutableListOf<String>()
                    if (list == null) {
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show()
                        return
                    }
                    for (i in list!!) {
                        if (i.direct) {
                            list_sent.add(i.key)
                        }
                    }


                    val intent = Intent(context, LabelSelectedLead::class.java)
                    startActivity(intent)
                }
                if(e?.xIndex==pamphlet_index){

                    list_sent = mutableListOf<String>()
                    if (list == null) {
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show()
                        return
                    }
                    for (i in list!!) {
                        if (i.pamphlet) {
                            list_sent.add(i.key)
                        }
                    }
                    val intent = Intent(context, LabelSelectedLead::class.java)
                    startActivity(intent)
                }
                if(e?.xIndex==newspaper_index){

                    list_sent = mutableListOf<String>()
                    if (list == null) {
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show()
                        return
                    }
                    for (i in list!!) {
                        if (i.newspaper) {
                            list_sent.add(i.key)
                        }
                    }
                    val intent = Intent(context, LabelSelectedLead::class.java)
                    startActivity(intent)
                }
                if(e?.xIndex==referral_index){

                    list_sent = mutableListOf<String>()
                    if (list == null) {
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show()
                        return
                    }
                    for (i in list!!) {
                        if (i.referral) {
                            list_sent.add(i.key)
                        }
                    }
                    val intent = Intent(context, LabelSelectedLead::class.java)
                    startActivity(intent)
                }
                if(e?.xIndex==radio_index){

                    list_sent = mutableListOf<String>()
                    if (list == null) {
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show()
                        return
                    }
                    for (i in list!!) {
                        if (i.radio) {
                            list_sent.add(i.key)
                        }
                    }
                    val intent = Intent(context, LabelSelectedLead::class.java)
                    startActivity(intent)
                }
                if(e?.xIndex==tele_index){

                    list_sent = mutableListOf<String>()
                    if (list == null) {
                        Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show()
                        return
                    }
                    for (i in list!!) {
                        if (i.telecalling) {
                            list_sent.add(i.key)
                        }
                    }
                    val intent = Intent(context, LabelSelectedLead::class.java)
                    startActivity(intent)
                }
            }

        })
        binding.online.setOnClickListener {
            list_sent = mutableListOf<String>()
            if(list==null){
                Toast.makeText(context,"No Data",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            for (i in list!!) {
                if (i.online) {
                    list_sent.add(i.key)
                }
            }
            val intent = Intent(context, LabelSelectedLead::class.java)
            startActivity(intent)
        }
        binding.radioTel.setOnClickListener {
            list_sent = mutableListOf<String>()
            if(list==null){
                Toast.makeText(context,"No Data",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            for (i in list!!) {
                if (i.radio) {
                    list_sent.add(i.key)
                }
            }
            val intent = Intent(context, LabelSelectedLead::class.java)
            startActivity(intent)
        }
        binding.newspaperCheck.setOnClickListener {
            list_sent = mutableListOf<String>()
            if(list==null){
                Toast.makeText(context,"No Data",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            for (i in list!!) {
                if (i.newspaper) {
                    list_sent.add(i.key)
                }
            }
            val intent = Intent(context, LabelSelectedLead::class.java)
            startActivity(intent)
        }
        binding.directLabel.setOnClickListener {
            list_sent = mutableListOf<String>()
            if(list==null){
                Toast.makeText(context,"No Data",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            for (i in list!!) {
                if (i.direct) {
                    list_sent.add(i.key)
                }
            }


            val intent = Intent(context, LabelSelectedLead::class.java)
            startActivity(intent)
        }
        binding.referrals.setOnClickListener {
            list_sent = mutableListOf<String>()
            if(list==null){
                Toast.makeText(context,"No Data",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            for (i in list!!) {
                if (i.referral) {
                    list_sent.add(i.key)
                }
            }
            val intent = Intent(context, LabelSelectedLead::class.java)
            startActivity(intent)
        }

        binding.telecalling.setOnClickListener {
            list_sent = mutableListOf<String>()
            if(list==null){
                Toast.makeText(context,"No Data",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            for (i in list!!) {
                if (i.telecalling) {
                    list_sent.add(i.key)
                }
            }
            val intent = Intent(context, LabelSelectedLead::class.java)
            startActivity(intent)
        }
        binding.smsCampaign.setOnClickListener {
            list_sent = mutableListOf<String>()
            if(list==null){
                Toast.makeText(context,"No Data",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            for (i in list!!) {
                if (i.sms) {
                    list_sent.add(i.key)
                }
            }
            val intent = Intent(context, LabelSelectedLead::class.java)
            startActivity(intent)
        }
        binding.pamphlets.setOnClickListener {
            list_sent = mutableListOf<String>()
            if(list==null){
                Toast.makeText(context,"No Data",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            for (i in list!!) {
                if (i.pamphlet) {
                    list_sent.add(i.key)
                }
            }
            val intent = Intent(context, LabelSelectedLead::class.java)
            startActivity(intent)
        }


        return binding.root
    }

}
