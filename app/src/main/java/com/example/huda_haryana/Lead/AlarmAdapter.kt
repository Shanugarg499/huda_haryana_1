package com.example.huda_haryana.Lead

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.huda_haryana.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.sql.Date
import java.text.SimpleDateFormat

class AlarmAdapter(val list: MutableList<AlarmData>, val curr: Long) : RecyclerView.Adapter<AlarmAdapter.MyViewHolder>() {
    lateinit var dialog: AlertDialog.Builder
    lateinit var mref: DatabaseReference
    lateinit var mref2: DatabaseReference
    var pos:Int = 0

    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val desc = itemview.findViewById<TextView>(R.id.desc_addtask_item)
        val date = itemview.findViewById<TextView>(R.id.date_addtask_item)
        val name = itemview.findViewById<TextView>(R.id.addtask_item_name)
        val del = itemview.findViewById<ImageView>(R.id.delete_item_task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.addtask_items, parent, false)
        dialog = AlertDialog.Builder(parent.context)
        dialog.setTitle("Delete")
        dialog.setMessage("Are you Sure ?")
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.desc.text = list[position].desc
        val dateformat = SimpleDateFormat("hh:mm a dd-MMM")
        val date = dateformat.format(Date(list[position].date.toLong()))
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, SetTask::class.java).putExtra("key", list[position].key).putExtra("id", list[position].id).putExtra("name", list[position].name).putExtra("desc", list[position].desc).putExtra("date", list[position].date)
            holder.itemView.context.startActivity(intent)
        }
        if (list[position].date.toLong() < curr) {
            holder.date.setTextColor(Color.RED)
        }
        holder.del.setOnClickListener {
            dialog.show()
            mref = FirebaseDatabase.getInstance().getReference("leads").child(list[position].id).child("Alarm").child(list[position].key)
            mref2 = FirebaseDatabase.getInstance().getReference("Tasks").child(list[position].key)
            pos=position
        }
        dialog.setNegativeButton("CANCEL", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })
        dialog.setPositiveButton("YES", DialogInterface.OnClickListener { dialog, which ->
            mref.removeValue()
            mref2.removeValue()
            list.removeAt(pos)
            notifyDataSetChanged()
            dialog.dismiss()
        })
        holder.date.text = date
        holder.name.text = list[position].name
    }
}
