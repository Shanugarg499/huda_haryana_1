package com.example.huda_haryana.ui.upcoming_projects

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.huda_haryana.R
import com.squareup.picasso.Picasso

class AdapterNews(val context: Context, val data: ArrayList<NewsData>) : RecyclerView.Adapter<AdapterNews.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val heading: TextView = itemView.findViewById(R.id.heading_news)
        val description: TextView = itemView.findViewById(R.id.details_news)
        val image: ImageView = itemView.findViewById(R.id.newsImage)
        val base: CardView = itemView.findViewById(R.id.base_news)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
                LayoutInflater.from(context).inflate(R.layout.recycler_view_news_api, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, pos: Int) {
        val animationZoom = AnimationUtils.loadAnimation(context, R.anim.zoomin)
        holder.base.startAnimation(animationZoom)
        holder.heading.text = data[pos].heading
        holder.description.text = data[pos].description
        Picasso.get().load(data[pos].imageUrl).into(holder.image)

    }

}