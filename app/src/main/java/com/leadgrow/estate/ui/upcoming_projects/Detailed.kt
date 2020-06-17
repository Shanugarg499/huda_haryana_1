package com.leadgrow.estate.ui.upcoming_projects

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.leadgrow.estate.R
import com.leadgrow.estate.databinding.ActivityDetailedBinding
import com.squareup.picasso.Picasso

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