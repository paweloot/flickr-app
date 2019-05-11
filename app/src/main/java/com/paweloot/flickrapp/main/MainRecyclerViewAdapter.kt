package com.paweloot.flickrapp.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paweloot.flickrapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_image_item.view.*
import org.json.JSONArray
import org.json.JSONObject

class MainRecyclerViewAdapter(private val data: JSONArray, private val onImageClickListener: OnImageClickListener) :
    RecyclerView.Adapter<MainRecyclerViewAdapter.PhotoViewHolder>() {

    companion object {
        const val JSON_KEY_IMAGE_URL = "URL"
        const val JSON_KEY_IMAGE_TITLE = "TITLE"
        const val JSON_KEY_IMAGE_DATE = "DATE"
        const val JSON_KEY_IMAGE_TAGS = "TAGS"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_image_item, parent, false) as View

        return PhotoViewHolder(inflatedView, onImageClickListener)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val image = data.getJSONObject(position)

        holder.view.tag = data.get(position)
        holder.apply {
            setURL(image.getString(JSON_KEY_IMAGE_URL))
            setTitle(image.getString(JSON_KEY_IMAGE_TITLE))
            setDate(image.getString(JSON_KEY_IMAGE_DATE))
            setTags(image.getString(JSON_KEY_IMAGE_TAGS))
        }
    }

    override fun getItemCount() = data.length()

    fun addImage(url: String, title: String, date: String, tags: String) {
        data.put(createImageJSONObject(url, title, date, tags))
        notifyDataSetChanged()
    }

    private fun createImageJSONObject(url: String, title: String, date: String, tags: String): JSONObject {
        return JSONObject().apply {
            put(JSON_KEY_IMAGE_URL, url)
            put(JSON_KEY_IMAGE_TITLE, title)
            put(JSON_KEY_IMAGE_DATE, date)
            put(JSON_KEY_IMAGE_TAGS, tags)
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
//            Picasso.get().load(url).placeholder(R.drawable.ic_action_emo_err).into(object : Target {
//                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
//                    if (bitmap != null) {
//                        view.image_main.setImageBitmap(bitmap)
//                        Toast.makeText(view.context, "Image loaded", Toast.LENGTH_SHORT).show()
//                    } else {
//                        view.image_main.setImageDrawable(view.context.getDrawable(R.drawable.ic_action_emo_err))
//                    }
//                }
//
//                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
//                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
//                    Toast.makeText(view.context, R.string.error_loading_image, Toast.LENGTH_SHORT).show()
//                }
//            })
        }

        fun setTitle(title: String) {
            view.text_image_title.text = title
        }

        fun setDate(date: String) {
            view.text_image_date.text = date
        }

        fun setTags(tags: String) {
            val threeTags = splitTagsToList(tags).take(3)
            val tagsJoined = joinTags(threeTags)

            view.text_image_tags.text = tagsJoined
        }

        override fun onClick(view: View?) {
            onImageClickListener.onImageClick(adapterPosition)
        }

        private fun splitTagsToList(tags: String): List<String> {
            return tags.substring(1).split(" #")
        }

        private fun joinTags(tags: List<String>): String {
            return "#${tags.joinToString(" #")}"
        }
    }

    interface OnImageClickListener {
        fun onImageClick(position: Int)
    }
}