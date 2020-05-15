package com.example.huda_haryana.Lead

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.huda_haryana.R
import java.sql.Date
import java.text.SimpleDateFormat

class AlarmAdapter(val list: MutableList<AlarmData>) : RecyclerView.Adapter<AlarmAdapter.MyViewHolder>() {
    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val desc = itemview.findViewById<TextView>(R.id.desc_addtask_item)
        val date = itemview.findViewById<TextView>(R.id.date_addtask_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.addtask_items, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.desc.text = list[position].desc
        val dateformat = SimpleDateFormat("hh:mm a dd-MMM")
        val date = dateformat.format(Date(list[position].date.toLong()))
        holder.date.text=list[position].name +"   "+ date
    }
}
