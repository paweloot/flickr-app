package com.paweloot.flickrapp.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.paweloot.flickrapp.R
import com.paweloot.flickrapp.common.IMAGE_DATA
import com.paweloot.flickrapp.common.IMAGE_POSITION
import com.paweloot.flickrapp.common.IMAGE_URL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_image.view.*
import org.json.JSONArray

class ImageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_image, container, false)

        loadImageIntoView(view)
        setOnClickNavigation(view)

        return view
    }

    private fun loadImageIntoView(view: View) {
        val intent = activity?.intent

        val imageDataRaw = intent?.getStringExtra(IMAGE_DATA)
        val imageData = JSONArray(imageDataRaw)
        val position = intent?.getIntExtra(IMAGE_POSITION, 0)

        if (position != null) {
            val currImageUrl = imageData.getJSONObject(position).getString(IMAGE_URL)
            Picasso.get().load(currImageUrl).into(view.main_image)
        }
    }

    private fun setOnClickNavigation(view: View) {
        view.main_image.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_imageFragment_to_imageDetailsFragment)
        )
    }
}
