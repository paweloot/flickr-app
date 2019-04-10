package com.paweloot.flickrapp.main

import android.content.SharedPreferences
import org.json.JSONArray

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {

    override fun onAddImageButtonClick() {
        view.addImage()
    }

    override fun fetchImageUrls(sharedPref: SharedPreferences): JSONArray {
        val imageUrlsRaw = sharedPref.getString("image_urls_data", null)

        return if (imageUrlsRaw == null) JSONArray()
        else JSONArray(imageUrlsRaw)
    }
}