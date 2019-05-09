package com.paweloot.flickrapp.main

import android.content.SharedPreferences
import org.json.JSONArray

interface MainContract {
    interface Presenter {
        fun onAddImageButtonClick()

        fun fetchImageUrls(sharedPref: SharedPreferences): JSONArray
        fun pickSimilarImages(n: Int, data: JSONArray)
    }

    interface View {
        fun addImage()
        fun fetchSharedPref(): SharedPreferences
    }
}