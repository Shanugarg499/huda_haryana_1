package com.leadgrow.estate.fragments_bottom.ab

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leadgrow.estate.Lead.LabelData
import com.leadgrow.estate.Lead.LeadOptions
import com.leadgrow.estate.R
import com.leadgrow.estate.order_to_database
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.Serializable

class LeadsAdpater(val context: Context, val data: ArrayList<order_to_database>) : RecyclerView.Adapter<LeadsAdpater.MyViewModel>() {

    private val mDb = FirebaseDatabase.getInstance().reference

    class MyViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val pin: ImageView = itemView.findViewById(R.id.imageViewPin)
        val menuButton: ImageView = itemView.findViewById(R.id.leadIdRecyclerView)
        val optionLayout: LinearLayout = itemView.findViewById(R.id.option_layout)
        val nameTextView: TextView = itemView.findViewById(R.id.nameLeadRecyclerview)
        val timeTextView: TextView = itemView.findViewById(R.id.timeRecyclerViewItem)
        val deleteButton: LinearLayout = itemView.findViewById(R.id.delete_Leads)
        val recyclerViewInner: RecyclerView = itemView.findViewById(R.id.recyclerViewInner)
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

        holder.background.setOnClickListener {

            val intent = Intent(context, LeadOptions::class.java) .putExtra("id", data[pos].key).putExtra("name", data[pos].name).putExtra("phone", data[pos].number).putExtra("email", data[pos].email).putExtra("object",data[pos] as Serializable)
            holder.itemView.context.startActivity(intent)
        }

        val accnt = GoogleSignIn.getLastSignedInAccount(context)
        val mref = FirebaseDatabase.getInstance().getReference("User").child(accnt?.id!!).child("Leads").child(data[pos].key)
        mref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val list = mutableListOf<LabelData>()
                if (p0.child("Labels").child("list").exists()) {
                    for (i in p0.child("Labels").child("list").children) {
                        val data = i.getValue(LabelData::class.java)
                        list.add(data!!)
                    }
                }
                if(p0.child("Type").child("type").exists()){
                    val type = p0.child("Type").child("type").value.toString()
                    list.add(LabelData(labelname =  type , labelcolor = "006064"))
                }

                if(p0.child("Allproperties").child("Value").exists()){
                    val propType = p0.child("Allproperties").child("Value").value.toString()
                    list.add(LabelData(labelname =  propType , labelcolor = "AD1457"))
                }
                list.reverse()
                holder.recyclerViewInner.visibility = View.VISIBLE
                holder.recyclerViewInner.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                holder.recyclerViewInner.adapter = AddLabelAdapterSmallerTextSize(list)
            }

        })



        holder.nameTextView.text = data[pos].name
        holder.timeTextView.text = "+" + data[pos].date + " " + data[pos].time

        val ref = mDb.child("User").child(accnt.id!!).child("Leads").child(data[pos].key)

        holder.deleteButton.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("Delete Customer?")
                    .setMessage("Deletes customer will be removed and will NOT reappear when they call you ")
                    .setPositiveButton("Cancel") { dialogInterface, i ->
                        //do nothing
                    }.setNegativeButton("Delete") { dialogInterface, i ->
                        ref.removeValue()
                        data.removeAt(pos)
                        notifyDataSetChanged()
                    }.setIcon(R.drawable.delete_lead)
                    .show()
        }

        holder.pin.setOnClickListener {

//                Toast.makeText(context,"UnPinned",Toast.LENGTH_SHORT).show()
                data[pos].isPin = !data[pos].isPin
                mref.child("pin").setValue(data[pos].isPin)
//                LeadsPageUpdated().sortAndNotify(data)
            val message = if(data[pos].isPin)"Pinned " else "Unpinned "
                Toast.makeText(
                        context,
                        message+data[pos].name,
                        Toast.LENGTH_LONG
                ) .show()
        }

    }

}