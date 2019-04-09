package com.paweloot.flickrapp.main

import android.content.SharedPreferences
import org.json.JSONArray

class MainPresenter(val view: MainContract.View) : MainContract.Presenter {

    override fun onAddImageButtonClick() {
        view.addImage()
    }

    override fun saveImageUrl(sharedPref: SharedPreferences, imageUrl: String) {
        val currImageUrlsRaw = sharedPref.getString("image_urls_data", null)
        val currImageUrlsData: JSONArray = if (currImageUrlsRaw == null) JSONArray() else JSONArray(currImageUrlsRaw)

        currImageUrlsData.put(imageUrl)

        with(sharedPref.edit()) {
            putString("image_urls_data", currImageUrlsData.toString())
            apply()
        }
    }

    override fun fetchImageUrls(sharedPref: SharedPreferences): JSONArray {
        val imageUrlsRaw = sharedPref.getString("image_urls_data", null)

        return if (imageUrlsRaw == null) JSONArray()
        else JSONArray(imageUrlsRaw)
    }
}