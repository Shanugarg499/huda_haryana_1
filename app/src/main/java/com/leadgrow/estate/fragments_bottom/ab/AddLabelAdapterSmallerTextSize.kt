package com.leadgrow.estate.fragments_bottom.ab

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.leadgrow.estate.Lead.LabelData
import com.leadgrow.estate.R

class AddLabelAdapterSmallerTextSize(val list: MutableList<LabelData>) : RecyclerView.Adapter<AddLabelAdapterSmallerTextSize.MyViewHolder>() {
    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val labelname = itemview.findViewById<TextView>(R.id.label_name_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lable_item_small, parent, false)
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