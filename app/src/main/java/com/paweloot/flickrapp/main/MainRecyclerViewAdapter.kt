package com.paweloot.flickrapp.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paweloot.flickrapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_photo_item.view.*
import org.json.JSONArray
import java.util.*

class MainRecyclerViewAdapter(private val data: JSONArray) :
    RecyclerView.Adapter<MainRecyclerViewAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewAdapter.PhotoViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_photo_item, parent, false) as View

        return PhotoViewHolder(inflatedView, parent.context)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.setImageURL(data.getString(position))
    }

    override fun getItemCount() = data.length()

    fun addAt(position: Int, element: String?) {
        data.put(position, element)
        notifyItemInserted(position)
    }

    fun removeAt(position: Int) {
        data.remove(position)
        notifyItemRemoved(position)
    }

    class PhotoViewHolder(val view: View, private val context: Context) : RecyclerView.ViewHolder(view) {

        fun setImageURL(url: String) {
            Picasso.get().load(url).into(view.photoIV)
        }

        fun setName(name: String) {

        }

        fun setDate(date: Date) {

        }

        fun setTags(tags: ArrayList<String>) {

        }
    }
}