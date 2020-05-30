package com.real_estate.lead_grow.ui.analysis

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager

import com.real_estate.lead_grow.R
import com.real_estate.lead_grow.databinding.AnalysisFragment2Binding
import com.real_estate.lead_grow.ui.upcoming_projects.AdapterNews
import com.real_estate.lead_grow.ui.upcoming_projects.NewsData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AnalysisFragment : Fragment() {


    private lateinit var viewModel: AnalysisViewModel
    private lateinit var binding: AnalysisFragment2Binding
    private lateinit var data: ArrayList<NewsData>
    private val mDb by lazy { FirebaseDatabase.getInstance().reference }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding  = DataBindingUtil.inflate( inflater , R.layout.analysis_fragment2, container, false)
        viewModel = ViewModelProviders.of(this).get(AnalysisViewModel::class.java)

        data = ArrayList()
        binding.recyclerViewNews.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewNews.adapter = AdapterNews(requireContext() , data)

        mDb.child("news").addValueEventListener(
                object : ValueEventListener {
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
