package com.example.animeapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter

class MangaListViewAdapter(var mangas: MutableList<Manga>, var photoItemCallback: MangaItemCallback) : Adapter<MangaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.list_item, parent, false)
        return MangaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  mangas.size
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        holder.bind(mangas[position], photoItemCallback)
    }

}