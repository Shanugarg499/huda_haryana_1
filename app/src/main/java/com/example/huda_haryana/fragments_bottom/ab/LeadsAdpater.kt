package com.example.huda_haryana.fragments_bottom.ab

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.huda_haryana.R
import com.example.huda_haryana.order_to_database
import com.google.firebase.database.FirebaseDatabase

class LeadsAdpater(val context: Context, val data: ArrayList<order_to_database>) : RecyclerView.Adapter<LeadsAdpater.MyViewModel>() {

    private val mDb = FirebaseDatabase.getInstance().reference

    class MyViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val menuButton: ImageView = itemView.findViewById(R.id.leadIdRecyclerView)
        val optionLayout: LinearLayout = itemView.findViewById(R.id.option_layout)
        val nameTextView: TextView = itemView.findViewById(R.id.nameLeadRecyclerview)
        val timeTextView: TextView = itemView.findViewById(R.id.timeRecyclerViewItem)
        val deleteButton: LinearLayout = itemView.findViewById(R.id.delete_Leads)
        val background = itemView.findViewById(R.id.entireCard) as ConstraintLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewModel {
        return MyViewModel(
                LayoutInflater.from(context).inflate(
                        R.layout.ab_recyclerview_item_lead_updated,
                        parent,
                        false
                )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewModel, pos: Int) {
        holder.menuButton.setOnClickListener {

            if (holder.optionLayout.isVisible) {
                val animationSlideLeftOur = AnimationUtils.loadAnimation(context, R.anim.fadeout)
                holder.optionLayout.startAnimation(animationSlideLeftOur)
                holder.optionLayout.visibility = View.GONE
            } else {
                val animationSlideLeftOur = AnimationUtils.loadAnimation(context, R.anim.fadein)
                holder.optionLayout.startAnimation(animationSlideLeftOur)
                holder.optionLayout.visibility = View.VISIBLE
            }


        }

        holder.background.setOnClickListener{
            //anubhav do your work from here :)
        }




        holder.nameTextView.text = data[pos].name
        holder.timeTextView.text = "+"+data[pos].date+" "+data[pos].time

        val ref = mDb.child("leads").child(data[pos].key)

        holder.deleteButton.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("Delete Customer?")
                    .setMessage("Deletes customer will be removed and will NOT reappear when they call you ")
                    .setPositiveButton("Cancel") { dialogInterface, i ->
                        //do nothing
                    }.setNegativeButton("Delete") { dialogInterface, i ->
//                        data.removeAt(pos)
//                        notifyDataSetChanged()
//                        mDb.child("leads").setValue(data)
                        ref.removeValue()
                    }.setIcon(R.drawable.delete_lead)
                    .show()
        }

    }

}