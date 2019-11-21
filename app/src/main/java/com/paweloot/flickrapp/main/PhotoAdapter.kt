package com.paweloot.flickrapp.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paweloot.flickrapp.R
import com.paweloot.flickrapp.api.GalleryItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_gallery.view.*
import java.text.SimpleDateFormat
import java.util.*

class PhotoAdapter(private val galleryItems: List<GalleryItem>) :
    RecyclerView.Adapter<PhotoAdapter.PhotoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.list_item_gallery,
                parent, false
            ) as View

        return PhotoHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val galleryItem = galleryItems[position]

        val date = SimpleDateFormat("MM/dd/yyyy").format(Date(galleryItem.date.toLong() * 1000))
        holder.apply {
            bindUrl(galleryItem.url)
            setTitle(galleryItem.title)
            setDate(date)
        }

    }

    override fun getItemCount() = galleryItems.size

//    private fun firstNTags(n: Int, rawTags: String): String {
//        val nTags = splitTagsToList(rawTags).take(n)
//
//        return joinTags(nTags)
//    }
//
//    private fun splitTagsToList(tags: String): List<String> {
//        return tags.substring(1).split(" #")
//    }
//
//    private fun joinTags(tags: List<String>): String {
//        return "#${tags.joinToString(" #")}"
//    }

    class PhotoHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindUrl(url: String) {
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
    }
}