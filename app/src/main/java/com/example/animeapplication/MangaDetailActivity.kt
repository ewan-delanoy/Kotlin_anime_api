package com.example.animeapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.animeapplication.databinding.ActivityPhotoDetailBinding

class MangaDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setViewItems()
    }

    private fun setViewItems() {
        val mangaTitle = intent.extras?.getString(MainActivity.mangaTitleKey)
        val image = intent.extras?.getString(MainActivity.imageKey)
        binding.mangaTitleTv.text = mangaTitle
        binding.synopsisTv.text = mangaTitle
        Glide.with(binding.photoIv.context).load(image)
            .into(binding.photoIv)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}