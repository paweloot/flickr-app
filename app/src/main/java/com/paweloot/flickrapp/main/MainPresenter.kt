package com.paweloot.flickrapp.main

import android.content.SharedPreferences
import org.json.JSONArray

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {

    override fun onAddImageButtonClick() {
        view.addImage()
    }

    override fun fetchImageUrls(sharedPref: SharedPreferences): JSONArray {
        val rawImageData = sharedPref.getString(MainActivity.PREF_IMAGE_DATA, null)

        return if (rawImageData == null) JSONArray()
        else JSONArray(rawImageData)
    }
}