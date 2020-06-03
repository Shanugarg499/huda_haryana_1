

package com.real_estate.lead_grow.ui.upcoming_projects

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager

import com.real_estate.lead_grow.R
import com.real_estate.lead_grow.databinding.UpcomingProjectsFragmentBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Upcoming_ProjectsFragment : Fragment() {



    private lateinit var viewModel: UpcomingProjectsViewModel
    private lateinit var binding : UpcomingProjectsFragmentBinding
    private lateinit var data: ArrayList<NewsData>
    private val mDb by lazy { FirebaseDatabase.getInstance().reference }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding  = DataBindingUtil.inflate( inflater , R.layout.upcoming__projects_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(UpcomingProjectsViewModel::class.java)

        data = ArrayList()
        binding.recyclerViewNews.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewNews.adapter = AdapterNews(requireContext() , data)

        mDb.child("news").addValueEventListener(
                object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {}

                    override fun onDataChange(news: DataSnapshot) {
                        data.clear()
                        for(new in news.children){
                            data.add( new.getValue(NewsData::class.java)!!)
                        }

                        binding.recyclerViewNews.adapter?.notifyDataSetChanged()
                    }

                }
        )





        return binding.root
    }



}
