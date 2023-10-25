package com.example.animeapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.animeapplication.databinding.ActivityFavoritesBinding


class FavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setViewItems()
    }

    private fun setViewItems() {
        setTitle(R.string.favorites)
        displayPhotoList(SharedPreferencesManager().getLocalMangaStorage(this).localMangas)
    }


    fun displayPhotoList(mangas: MutableList<Manga>) {
        val adapter = MangaListViewAdapter(mangas, object : MangaItemCallback {
            override fun onCellClick(manga: Manga) {
            }
            override fun onSaveManga(manga: Manga) {
                // here in favorites list we should rather delete
                deleteManga(manga)
            }
        })
        binding.photoRv.adapter = adapter
        binding.photoRv.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun deleteManga(manga: Manga) {
        SharedPreferencesManager().deleteManga(manga, this)
        displayPhotoList(SharedPreferencesManager().getLocalMangaStorage(this).localMangas)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}