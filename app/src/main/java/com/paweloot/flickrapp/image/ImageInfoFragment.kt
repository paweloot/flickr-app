package com.paweloot.flickrapp.image

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.paweloot.flickrapp.R
import com.paweloot.flickrapp.main.MainActivity
import com.paweloot.flickrapp.main.MainActivity.Companion.INTENT_IMAGE_DATA
import com.paweloot.flickrapp.main.MainActivity.Companion.INTENT_IMAGE_POSITION
import com.paweloot.flickrapp.main.MainRecyclerViewAdapter
import com.paweloot.flickrapp.main.MainRecyclerViewAdapter.Companion.JSON_KEY_IMAGE_DATE
import com.paweloot.flickrapp.main.MainRecyclerViewAdapter.Companion.JSON_KEY_IMAGE_TAGS
import com.paweloot.flickrapp.main.MainRecyclerViewAdapter.Companion.JSON_KEY_IMAGE_TITLE
import com.paweloot.flickrapp.main.MainRecyclerViewAdapter.Companion.JSON_KEY_IMAGE_URL
import kotlinx.android.synthetic.main.fragment_image_info.*
import kotlinx.android.synthetic.main.fragment_image_info.view.*
import org.json.JSONArray
import org.json.JSONObject

class ImageInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image_info, container, false)
//        activity?.intent?.get
        displayImageInfo(view)

        return view
    }

    private fun displayImageInfo(view: View) {
        val image = getCurrentImageData()

        view.text_image_info_title.text = image.getString(JSON_KEY_IMAGE_TITLE)
        view.text_image_info_date.text = image.getString(JSON_KEY_IMAGE_DATE)
        view.text_image_info_tags.text = image.getString(JSON_KEY_IMAGE_TAGS)
        view.text_image_info_url.text = image.getString(JSON_KEY_IMAGE_URL)
    }

    private fun getCurrentImageData(): JSONObject {
        val imageDataRaw = activity?.intent?.getStringExtra(INTENT_IMAGE_DATA)
        val imageData = JSONArray(imageDataRaw)
        val position = activity?.intent?.getIntExtra(INTENT_IMAGE_POSITION, 0) ?: 0

        return imageData.getJSONObject(position)
    }
}
