package com.paweloot.flickrapp.main

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.paweloot.flickrapp.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.recyclerview_image_item.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainRecyclerViewAdapter(private val data: JSONArray, val onImageClickListener: OnImageClickListener) :
    RecyclerView.Adapter<MainRecyclerViewAdapter.PhotoViewHolder>() {

    companion object {
        const val JSON_KEY_IMAGE_URL = "URL"
        const val JSON_KEY_IMAGE_TITLE = "TITLE"
        const val JSON_KEY_IMAGE_DATE = "DATE"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewAdapter.PhotoViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_image_item, parent, false) as View

        return PhotoViewHolder(inflatedView, onImageClickListener)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val image = data.getJSONObject(position)

        holder.apply {
            setURL(image.getString(JSON_KEY_IMAGE_URL))
            setTitle(image.getString(JSON_KEY_IMAGE_TITLE))
            setDate(image.getString(JSON_KEY_IMAGE_DATE))
        }
    }

    override fun getItemCount() = data.length()

    fun addImage(url: String, title: String, date: String) {
        data.put(createImageJSONObject(url, title, date))
        notifyDataSetChanged()
    }

    private fun createImageJSONObject(url: String, title: String, date: String): JSONObject {
        return JSONObject().apply {
            put(JSON_KEY_IMAGE_URL, url)
            put(JSON_KEY_IMAGE_TITLE, title)
            put(JSON_KEY_IMAGE_DATE, date)
        }
    }

    fun removeImageAt(position: Int) {
        data.remove(position)
        notifyItemRemoved(position)
    }

    fun getImageUrlAt(position: Int): String {
        return data.getJSONObject(position).getString(JSON_KEY_IMAGE_URL)
    }

    fun getData(): JSONArray = data

    class PhotoViewHolder(val view: View, private val onImageClickListener: OnImageClickListener) :
        RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        fun setURL(url: String) {
            Picasso.get().load(url).into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    if (bitmap != null) {
                        view.image_main.setImageBitmap(bitmap)

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
                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    Toast.makeText(view.context, R.string.error_loading_image, Toast.LENGTH_SHORT).show()
                }
            })
        }

        fun setTitle(title: String) {
            view.text_image_title.text = title
        }

        fun setDate(date: String) {
            view.text_image_date.text = date
        }

        fun setTags(tags: String) {
            view.text_image_tags.text = tags
        }

        override fun onClick(view: View?) {
            onImageClickListener.onImageClick(adapterPosition)
        }
    }

    interface OnImageClickListener {
        fun onImageClick(position: Int)
    }
}