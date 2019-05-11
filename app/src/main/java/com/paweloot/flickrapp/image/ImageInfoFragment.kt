package com.paweloot.flickrapp.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paweloot.flickrapp.R
import com.paweloot.flickrapp.common.*
import kotlinx.android.synthetic.main.fragment_image_info.view.*
import org.json.JSONArray
import org.json.JSONObject

class ImageInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image_info, container, false)

        loadImageInfo(view)

        return view
    }

    private fun loadImageInfo(view: View) {
        getCurrentImageData().apply {
            view.text_image_info_title.text = getString(IMAGE_TITLE)
            view.text_image_info_date.text = getString(IMAGE_DATE)
            view.text_image_info_tags.text = getString(IMAGE_TAGS)
            view.text_image_info_url.text = getString(IMAGE_URL)
        }
    }

    private fun getCurrentImageData(): JSONObject {
        val intent = activity?.intent

        val imageDataRaw = intent?.getStringExtra(IMAGE_DATA)
        val imageData = JSONArray(imageDataRaw)
        val position = intent?.getIntExtra(IMAGE_POSITION, 0)


        return if (position != null) {
            imageData.getJSONObject(position)
        } else {
            JSONObject()
        }
    }
}
