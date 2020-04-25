package com.example.huda_haryana.mybusiness

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.huda_haryana.R

class NoteAdapter(val list:MutableList<NoteData>) : RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {
    class MyViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
        val text=itemview.findViewById<TextView>(R.id.note_txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.text.text=list[position].text
    }
}