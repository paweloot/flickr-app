package com.paweloot.flickrapp.main

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.paweloot.flickrapp.R
import com.paweloot.flickrapp.common.IMAGE_DATA
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import org.json.JSONArray
import java.lang.Exception

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {

    companion object {
        const val TAG = "ImageLabeler"
    }

    override fun onAddImageButtonClick() {
        view.addImage()
    }

    override fun fetchImageUrls(sharedPref: SharedPreferences): JSONArray {
        val rawImageData = sharedPref.getString(IMAGE_DATA, null)

        return if (rawImageData == null) JSONArray()
        else JSONArray(rawImageData)
    }

    override fun generateTagsAndAddImage(url: String, title: String, date: String) {
        Picasso.get().load(url).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                if (bitmap != null) {
                    val vision = FirebaseVisionImage.fromBitmap(bitmap)
                    val labeler = FirebaseVision.getInstance().onDeviceImageLabeler

                    labeler.processImage(vision)
                        .addOnSuccessListener { labels ->
                            val joinedTags = "#${labels?.joinToString(" #") {
                                it.text.toLowerCase()
                            }}"

                            view.addImageToAdapter(url, title, date, joinedTags)
                        }
                        .addOnFailureListener { e -> Log.wtf(TAG, e.message) }
                }
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Toast.makeText(view as Context, R.string.error_loading_image, Toast.LENGTH_SHORT).show()
            }
        })

    }
}
