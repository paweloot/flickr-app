package com.paweloot.flickrapp.image

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.paweloot.flickrapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_image.*
import kotlinx.android.synthetic.main.fragment_image.view.*

class ImageFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_image, container, false)

        Picasso.get().load(activity?.intent?.getStringExtra("URL")).into(view.main_image)
        view.main_image.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_imageFragment_to_imageDetailsFragment)
        )
//        view.textview.setOnClickListener(
//            Navigation.createNavigateOnClickListener(R.id.action_imageFragment_to_imageDetailsFragment)
//        )
        return view
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }
}
