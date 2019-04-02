package com.paweloot.flickrapp.photo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paweloot.flickrapp.R
import kotlinx.android.synthetic.main.recyclerview_photo_item.view.*

class PhotoRecyclerViewAdapter(private val data: Array<String>) :
    RecyclerView.Adapter<PhotoRecyclerViewAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoRecyclerViewAdapter.PhotoViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_photo_item, parent, false) as View

        return PhotoViewHolder(inflatedView, parent.context)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.setText(data[position])
    }

    override fun getItemCount() = data.size

    class PhotoViewHolder(val view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        fun setText(text: String) {
            view.sample_text.text = text
        }
    }
}