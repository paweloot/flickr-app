package com.paweloot.flickrapp.image

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.paweloot.flickrapp.R
import com.paweloot.flickrapp.main.MainRecyclerViewAdapter
import com.paweloot.flickrapp.main.MainRecyclerViewAdapter.Companion.JSON_KEY_IMAGE_URL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_image.*
import kotlinx.android.synthetic.main.fragment_image.view.*
import org.json.JSONArray

class ImageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_image, container, false)

        val imageDataRaw = activity?.intent?.getStringExtra("imageData")
        val imageData = JSONArray(imageDataRaw)
        val position = activity?.intent?.getIntExtra("imagePosition", 0)

        if (position != null) {
            val currImageUrl = imageData.getJSONObject(position).getString(JSON_KEY_IMAGE_URL)
            Picasso.get().load(currImageUrl).into(view.main_image)
        }

        view.main_image.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_imageFragment_to_imageDetailsFragment)
        )
        return view
    }
}
