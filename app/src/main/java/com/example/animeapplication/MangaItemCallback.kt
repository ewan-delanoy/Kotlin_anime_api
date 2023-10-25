package com.example.animeapplication

interface MangaItemCallback {
    fun onCellClick(manga: Manga)
    fun onSaveManga(manga: Manga)
}