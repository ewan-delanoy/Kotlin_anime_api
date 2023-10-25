package com.example.animeapplication

import android.content.Context
import android.util.Log
import com.google.gson.Gson

class SharedPreferencesManager {
    companion object {
        const val mangaListKey = "mangaListKey"
        const val searchKey = "searchKey"
        const val preferencesFile = "preferencesFile"
    }

    fun saveSearchCriteria(search:String, context: Context) {
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        sharedPreferences
            .edit()
            .putString(searchKey, search)
            .apply()
    }

    fun getSearchCriteria(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        return sharedPreferences.getString(searchKey,"")
    }

    fun getLocalMangaStorage(context: Context): LocalMangaStorage {
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        val gson = Gson()
        val json: String? = sharedPreferences.getString(mangaListKey, "")
        if (json.isNullOrEmpty()) {
            return LocalMangaStorage(mutableListOf())
        }
        return gson.fromJson(json, LocalMangaStorage::class.java)
    }

    fun saveManga(manga: Manga, context: Context): Boolean {
        var localMangaStorage = getLocalMangaStorage(context)
        if (localMangaStorage.localMangas.contains(manga)) {
            return false
        }
        localMangaStorage.localMangas.add(manga)
        Log.d("savePhoto", " size "+localMangaStorage.localMangas.size)
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(localMangaStorage)
        sharedPreferences.edit().putString(mangaListKey, json).apply()
        return true
    }

    fun deleteManga(manga: Manga, context: Context) {
        var localPhotoStorage = getLocalMangaStorage(context)
        localPhotoStorage.localMangas.remove(manga)
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(localPhotoStorage)
        sharedPreferences.edit().putString(mangaListKey, json).apply()
    }
}