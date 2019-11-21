package com.paweloot.flickrgallery.api

import com.google.gson.annotations.SerializedName
import com.paweloot.flickrgallery.GalleryItem

class PhotoResponse {
    @SerializedName("photo")
    lateinit var galleryItems: List<GalleryItem>
}