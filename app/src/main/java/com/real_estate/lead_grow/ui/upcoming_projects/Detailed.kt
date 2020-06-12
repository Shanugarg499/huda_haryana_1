package com.real_estate.lead_grow.ui.upcoming_projects

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.real_estate.lead_grow.R
import com.real_estate.lead_grow.databinding.ActivityDetailedBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detailed.*

class Detailed : AppCompatActivity() {

    lateinit var binding: ActivityDetailedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , R.layout.activity_detailed)

        val animationZoom = AnimationUtils.loadAnimation(this, R.anim.zoomin)

        val heading  =intent.getStringExtra("heading")
        val description  =intent.getStringExtra("description")
        val imageUrl  =intent.getStringExtra("image")

        binding.heading.text = heading
        binding.detailedText.text = description
        Picasso.get().load(imageUrl).into(binding.image)

        binding.heading.startAnimation(animationZoom)
        binding.detailedText.startAnimation(animationZoom)
        binding.image.startAnimation(animationZoom)
    }
}