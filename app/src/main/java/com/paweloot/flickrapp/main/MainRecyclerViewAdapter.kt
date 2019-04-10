package com.paweloot.flickrapp.main

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel
import com.paweloot.flickrapp.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.recyclerview_image_item.view.*
import org.json.JSONArray
import java.lang.Exception
import java.util.*

class MainRecyclerViewAdapter(private val data: JSONArray) :
    RecyclerView.Adapter<MainRecyclerViewAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewAdapter.PhotoViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_image_item, parent, false) as View

        return PhotoViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.setImageURL(data.getString(position))


//        image?.apply {
//            contentIV.setImageBitmap(this)
//            val vision = FirebaseVisionImage.fromBitmap(this)
//            val labeler = FirebaseVision.getInstance().onDeviceImageLabeler
//
//            labeler.processImage(vision)
//                .addOnSuccessListener { labels ->
//                    // Task completed successfully
//                    // ...
//                    Log.d("LAB", labels.joinToString(" ") { it.text })
//                }
//                .addOnFailureListener { e ->
//                    // Task failed with an exception
//                    // ...
//                    Log.wtf("LAB", e.message)
//                }
//        }
    }

    override fun getItemCount() = data.length()

    fun addImage(imageUrl: String) {
        data.put(imageUrl)
        notifyDataSetChanged()
    }

    fun removeImageAt(position: Int) {
        data.remove(position)
        notifyItemRemoved(position)
    }

    fun getData(): JSONArray = data

    class PhotoViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun setImageURL(url: String) {
            Picasso.get().load(url).into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    if (bitmap != null) {
                        view.imageIV.setImageBitmap(bitmap)

                        val vision = FirebaseVisionImage.fromBitmap(bitmap)
                        val labeler = FirebaseVision.getInstance().onDeviceImageLabeler
                        labeler.processImage(vision)
                            .addOnSuccessListener { labels ->
                                setTags(
                                    "#${labels.take(3)
                                        .joinToString(" #") {
                                            it.text.toLowerCase()
                                        }}"
                                )
                            }

                            .addOnFailureListener { e ->
                                Log.wtf("ImageLabeler", e.message)
                            }
                    }
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
            })
        }

        fun setName(name: String) {

        }

        fun setDate(date: Date) {

        }

        fun setTags(tags: String) {
            view.tagsLabelTV.text = tags
        }
    }
}