package com.paweloot.flickrapp.image

import org.json.JSONArray

interface SimilarImagesContract {
    interface Presenter {
        fun pickSimilarImages(n: Int, imageData: JSONArray, currentPosition: Int)
    }

    interface View {
        fun displaySimilarImages(positions: List<Int>)
    }
}