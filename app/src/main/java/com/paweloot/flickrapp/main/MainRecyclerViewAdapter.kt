package com.paweloot.flickrapp.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paweloot.flickrapp.R
import com.paweloot.flickrapp.common.IMAGE_DATE
import com.paweloot.flickrapp.common.IMAGE_TAGS
import com.paweloot.flickrapp.common.IMAGE_TITLE
import com.paweloot.flickrapp.common.IMAGE_URL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_image_item.view.*
import org.json.JSONArray
import org.json.JSONObject

class MainRecyclerViewAdapter(private val data: JSONArray, private val onImageClickListener: OnImageClickListener) :
    RecyclerView.Adapter<MainRecyclerViewAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_image_item, parent, false) as View

        return PhotoViewHolder(inflatedView, onImageClickListener)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val image = data.getJSONObject(position)

        holder.apply {
            setURL(image.getString(IMAGE_URL))
            setTitle(image.getString(IMAGE_TITLE))
            setDate(image.getString(IMAGE_DATE))
            setTags(firstNTags(3, image.getString(IMAGE_TAGS)))
        }
    }

    private fun firstNTags(n: Int, rawTags: String): String {
        val nTags = splitTagsToList(rawTags).take(n)

        return joinTags(nTags)
    }

    private fun splitTagsToList(tags: String): List<String> {
        return tags.substring(1).split(" #")
    }

    private fun joinTags(tags: List<String>): String {
        return "#${tags.joinToString(" #")}"
    }

    override fun getItemCount() = data.length()

    fun addImage(url: String, title: String, date: String, tags: String) {
        data.put(createImageJSONObject(url, title, date, tags))
        notifyDataSetChanged()
    }

    private fun createImageJSONObject(url: String, title: String, date: String, tags: String): JSONObject {
        return JSONObject().apply {
            put(IMAGE_URL, url)
            put(IMAGE_TITLE, title)
            put(IMAGE_DATE, date)
            put(IMAGE_TAGS, tags)
        }
    }

    fun removeImageAt(position: Int) {
        data.remove(position)
        notifyItemRemoved(position)
    }

    fun getData(): JSONArray = data

    class PhotoViewHolder(val view: View, private val onImageClickListener: OnImageClickListener) :
        RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        fun setURL(url: String) {
            Picasso.get().load(url).into(view.image_main)
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