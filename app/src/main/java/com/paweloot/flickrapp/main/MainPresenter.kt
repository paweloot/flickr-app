package com.paweloot.flickrapp.main

class MainPresenter() {

    companion object {
        const val TAG = "ImageLabeler"
    }

//    override fun generateTagsAndAddImage(url: String, title: String, date: String) {
//        Picasso.get().load(url).into(object : Target {
//            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
//                if (bitmap != null) {
//                    val vision = FirebaseVisionImage.fromBitmap(bitmap)
//                    val labeler = FirebaseVision.getInstance().onDeviceImageLabeler
//
//                    labeler.processImage(vision)
//                        .addOnSuccessListener { labels ->
//                            val joinedTags = "#${labels?.joinToString(" #") {
//                                it.text.toLowerCase()
//                            }}"
//
//                            view.addImageToAdapter(url, title, date, joinedTags)
//                        }
//                        .addOnFailureListener { e -> Log.wtf(TAG, e.message) }
//                }
//            }
//
//            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
//            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
//                Toast.makeText(view as Context, R.string.error_loading_image, Toast.LENGTH_SHORT).show()
//            }
//        })
//
//    }
}
