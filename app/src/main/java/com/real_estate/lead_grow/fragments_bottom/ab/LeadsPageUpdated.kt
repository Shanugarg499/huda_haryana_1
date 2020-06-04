package com.real_estate.lead_grow.fragments_bottom.ab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.FacebookSdk.getApplicationContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.real_estate.lead_grow.R
import com.real_estate.lead_grow.ask_details
import com.real_estate.lead_grow.databinding.LeadsPageUpdatedFragmentBinding
import com.real_estate.lead_grow.order_to_database
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList


lateinit var data_lead: ArrayList<order_to_database>

class LeadsPageUpdated : Fragment() {

    private lateinit var viewModel: LeadsPageUpdatedViewModel
    private lateinit var binding: LeadsPageUpdatedFragmentBinding

    private lateinit var adapter: LeadsAdpater
    var personFamilyName = ""
    var personName = ""
    var personGivenName = ""

    private var mDb = FirebaseDatabase.getInstance().getReference("User")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.leads_page_updated_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(LeadsPageUpdatedViewModel::class.java)

        binding.recyclerViewLeadsUpdated.layoutManager = LinearLayoutManager(requireContext())
        val acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext())
        mDb.child(acct?.id!!).child("Leads").addValueEventListener(

                object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            data_lead = ArrayList()
                            for (eachLead in dataSnapshot.children) {
                                val oneLead = eachLead.getValue(order_to_database::class.java)
                                data_lead.add(oneLead!!)
                            }

                            Collections.sort( data_lead , StringDateComparator() )

                            binding.recyclerViewLeadsUpdated.adapter = context?.let { LeadsAdpater(it, data_lead) }
                        }
                    }

                }
        )

        binding.mFab.setOnClickListener {
            val intent = Intent(activity, ask_details::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}

class StringDateComparator : Comparator<order_to_database?> {

    val date = SimpleDateFormat("dd-MMM-yyy")

    override fun compare(l: order_to_database?, r: order_to_database?): Int {

        Log.i("date",date.parse(l!!.date!!).toString())

        if(l!!.date!! == r!!.date!!){
            return (l?.time.compareTo(r?.time))*-1
        }
        return (date.parse(l!!.date!!).compareTo(date.parse(r?.date!!) ))*-1
    }
}