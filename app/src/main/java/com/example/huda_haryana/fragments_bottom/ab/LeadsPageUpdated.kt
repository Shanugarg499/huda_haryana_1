package com.example.huda_haryana.fragments_bottom.ab

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.huda_haryana.R
import com.example.huda_haryana.ask_details
import com.example.huda_haryana.databinding.LeadsPageUpdatedFragmentBinding
import com.example.huda_haryana.order_to_database
import com.facebook.FacebookSdk.getApplicationContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LeadsPageUpdated : Fragment() {

    private lateinit var viewModel: LeadsPageUpdatedViewModel
    private lateinit var binding: LeadsPageUpdatedFragmentBinding
    private lateinit var data: ArrayList<order_to_database>
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
//        if (acct != null) {
//            personName = acct.displayName.toString()
//            personGivenName = acct.givenName.toString()
//            personFamilyName = acct.familyName.toString()
//        }

        mDb.child(acct?.id!!).child("Leads").addValueEventListener(

                object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            data = ArrayList()
                            for (eachLead in dataSnapshot.children) {
                                val oneLead = eachLead.getValue(order_to_database::class.java)
                                data.add(oneLead!!)
                            }
                            binding.recyclerViewLeadsUpdated.adapter = LeadsAdpater(context!!, data)
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
