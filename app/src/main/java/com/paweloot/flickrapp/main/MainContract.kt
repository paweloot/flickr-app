package com.paweloot.flickrapp.main

import android.content.SharedPreferences
import org.json.JSONArray

interface MainContract {
    interface Presenter {
        fun onAddImageButtonClick()
        fun fetchImageUrls(sharedPref: SharedPreferences): JSONArray
        fun generateTagsAndAddImage(url: String, title: String, date: String)
    }

    interface View {
        fun addImage()
        fun addImageToAdapter(url: String, title: String, date: String, tags: String)
        fun fetchSharedPref(): SharedPreferences
    }
}