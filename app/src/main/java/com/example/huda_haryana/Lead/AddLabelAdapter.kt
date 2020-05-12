package com.example.huda_haryana.Lead

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.huda_haryana.R

class AddLabelAdapter(val list: MutableList<LabelData>) : RecyclerView.Adapter<AddLabelAdapter.MyViewHolder>() {
    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val labelname = itemview.findViewById<TextView>(R.id.label_name_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.leadoption_addlabel_recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.labelname.text = list[position].labelname
        holder.labelname.setBackgroundColor(Color.parseColor("#"+list[position].labelcolor))
    }
}