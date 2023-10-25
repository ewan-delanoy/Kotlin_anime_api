package com.example.animeapplication

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MangaViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var mangaIV: ImageView
    private var authorTV: TextView
    private var containerCL: ConstraintLayout
    private var bookmark: ImageView

    init {
        mangaIV = itemView.findViewById(R.id.manga_iv)
        authorTV = itemView.findViewById(R.id.author_tv)
        containerCL = itemView.findViewById(R.id.container)
        bookmark = itemView.findViewById(R.id.bookmark)
    }

    fun bind(manga: Manga, photoItemCallback: MangaItemCallback
    ) {
        authorTV.text = manga.title
        Glide.with(mangaIV.context).load(manga.images?.jpg?.imageUrl)
            .into(mangaIV)
        containerCL.setOnClickListener {
            photoItemCallback.onCellClick(manga)
        }
        bookmark.setOnClickListener {
            photoItemCallback.onSaveManga(manga)
        }
    }
}


