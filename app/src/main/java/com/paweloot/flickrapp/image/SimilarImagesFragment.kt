package com.paweloot.flickrapp.image


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paweloot.flickrapp.R
import com.paweloot.flickrapp.common.IMAGE_DATA
import com.paweloot.flickrapp.common.IMAGE_POSITION
import com.paweloot.flickrapp.common.IMAGE_URL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_similar_images.view.*
import org.json.JSONArray

class SimilarImagesFragment : Fragment(), SimilarImagesContract.View {

    private lateinit var presenter: SimilarImagesContract.Presenter
    private lateinit var inflatedView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflatedView = inflater.inflate(R.layout.fragment_similar_images, container, false)

        presenter = SimilarImagesPresenter(this)

        pickSimilarImages()

        return inflatedView
    }

    private fun pickSimilarImages() {
        val intent = activity?.intent
        val imageData = JSONArray(intent?.getStringExtra(IMAGE_DATA))
        val imagePosition = intent?.getIntExtra(IMAGE_POSITION, 0) ?: 0

        presenter.pickSimilarImages(6, imageData, imagePosition)
    }

    override fun displaySimilarImages(positions: List<Int>) {
        val imageData = JSONArray(activity?.intent?.getStringExtra(IMAGE_DATA))
        val tiles = listOf(
            inflatedView.similar_image_1, inflatedView.similar_image_2, inflatedView.similar_image_3,
            inflatedView.similar_image_4, inflatedView.similar_image_5, inflatedView.similar_image_6
        )

        for (i in 0 until positions.size) {
            val url = imageData.getJSONObject(positions[i]).getString(IMAGE_URL)

            Picasso.get().load(url).into(tiles[i])
        }
    }
}
