package com.paweloot.flickrapp.image

import com.paweloot.flickrapp.main.MainRecyclerViewAdapter.Companion.JSON_KEY_IMAGE_TAGS
import org.json.JSONArray

class SimilarImagesPresenter(private val view: SimilarImagesContract.View) : SimilarImagesContract.Presenter {

    companion object {
        const val MIN_THE_SAME_TAGS = 3
    }

    override fun pickSimilarImages(n: Int, imageData: JSONArray, currentPosition: Int) {
        val currentImage = imageData.getJSONObject(currentPosition)
        val currentTags = tagsToList(currentImage.getString(JSON_KEY_IMAGE_TAGS))

        val similarImagesPositions = ArrayList<Int>()

        for (i in 0 until imageData.length()) {
            if (i != currentPosition && similarImagesPositions.size < 6) {
                val image = imageData.getJSONObject(i)
                val tags = tagsToList(image.getString(JSON_KEY_IMAGE_TAGS))

                if (hasAtLeastTheSameNTags(MIN_THE_SAME_TAGS, tags, currentTags)) {
                    similarImagesPositions.add(i)
                }
            }
        }

        view.displaySimilarImages(similarImagesPositions)
    }

    private fun tagsToList(tags: String): List<String> {
        return ArrayList<String>(tags.substring(1).split(" #"))
    }

    private fun hasAtLeastTheSameNTags(n: Int, tags: List<String>, currentTags: List<String>): Boolean {
        return tags.count { tag -> currentTags.contains(tag) } >= n
    }
}