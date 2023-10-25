package com.example.animeapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animeapplication.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        val mangaTitleKey = "mangaTitleKey"
        val synopsisKey = "synopsisKey"
        val imageKey = "imageKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewItems()
    }

    private fun setViewItems() {

        val storedSearch = SharedPreferencesManager().getSearchCriteria(this)
        if (storedSearch != null) {
            binding.searchEt.setText(storedSearch)
        }

        binding.searchBt.setOnClickListener {
            checkUserInput()
            callService()
            binding.searchBt.visibility = View.INVISIBLE
            binding.progress.visibility = View.VISIBLE
        }
        binding.favoritesBt.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }
    }

    private fun checkUserInput() {
        if (binding.searchEt.text.toString().isEmpty()) {
            Toast.makeText(this, "Veuillez effectuer une saisie", Toast.LENGTH_LONG).show()
            return
        }
    }

    private fun callService() {
        val service: MangaApi.MangaService =
            MangaApi().getClient().create(MangaApi.MangaService::class.java)
        val call: Call<MangaApiResponse> =
            service.getMangas(
                /*"IlQOgCrui5R9g9aHW8r3Frhk78vqlbwWeaYmaMG25eJtzJtCOZyUdNEw", */
                binding.searchEt.text.toString()
            )
        call.enqueue(object : Callback<MangaApiResponse> {
            override fun onResponse(
                call: Call<MangaApiResponse>,
                response: Response<MangaApiResponse>
            ) {
                processResponse(response)
                searchEnded()
            }

            override fun onFailure(call: Call<MangaApiResponse>, t: Throwable) {
                processFailure(t)
                searchEnded()
            }
        })
    }

    private fun searchEnded() {
        binding.searchBt.visibility = View.VISIBLE
        binding.progress.visibility = View.INVISIBLE
        SharedPreferencesManager().saveSearchCriteria(binding.searchEt.text.toString(), this)
    }

    private fun processFailure(t: Throwable) {
        Toast.makeText(this, "Erreur", Toast.LENGTH_LONG).show()
    }

    private fun processResponse(response: Response<MangaApiResponse>) {
        if (response.body() != null) {
            val body = response.body()
            if (body?.data?.isNotEmpty() == true) {
                val adapter = MangaListViewAdapter(body.data, object : MangaItemCallback {
                    override fun onCellClick(manga: Manga) {
                        gotoNextActivity(manga)
                    }

                    override fun onSaveManga(manga: Manga) {
                        saveManga(manga)
                    }

                })
                val recyclerView = findViewById<RecyclerView>(R.id.photo_rv)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)
            }
        }
    }

    private fun saveManga(manga: Manga) {
        if (SharedPreferencesManager().saveManga(manga, this)){
            Toast.makeText(this,"Enregistrement bien effectué", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this,"Ce manga est déjà dans vos favoris", Toast.LENGTH_LONG).show()
        }
    }

    private fun gotoNextActivity(manga: Manga) {
        val intent = Intent(this, MangaDetailActivity::class.java)
        intent.putExtra(mangaTitleKey, manga.title)
        intent.putExtra(synopsisKey, manga.synopsis)
        intent.putExtra(imageKey, manga.images?.jpg?.imageUrl)
        startActivity(intent)
    }
}