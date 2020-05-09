package com.example.huda_haryana.Lead

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.huda_haryana.R

class LeadNoteAdapter(val list: MutableList<LeadNoteData>) : RecyclerView.Adapter<LeadNoteAdapter.MyViewHolder>() {
    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val date = itemview.findViewById<TextView>(R.id.item_date)
        val note = itemview.findViewById<TextView>(R.id.note_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.leadnote_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.note.text = list[position].note
        holder.date.text = list[position].date
    }
}