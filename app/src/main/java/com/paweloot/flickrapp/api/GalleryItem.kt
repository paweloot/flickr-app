package com.paweloot.flickrgallery

import com.google.gson.annotations.SerializedName

data class GalleryItem(
    var id: String = "",
    var title: String = "",
    @SerializedName("url_s") var url: String = ""
) {
}