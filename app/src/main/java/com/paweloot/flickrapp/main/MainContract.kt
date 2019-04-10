package com.paweloot.flickrapp.main

import android.content.SharedPreferences
import org.json.JSONArray

interface MainContract {
    interface Presenter {
        fun onAddImageButtonClick()

        fun fetchImageUrls(sharedPref: SharedPreferences): JSONArray
    }

    interface View {
        fun addImage()
        fun fetchSharedPref(): SharedPreferences
    }
}