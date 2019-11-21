package com.paweloot.flickrapp.api

import com.google.gson.annotations.SerializedName

data class GalleryItem(
    var id: String = "",
    var title: String = "",
    @SerializedName("url_c") var url: String = "",
    @SerializedName("dateupload") var date: String = ""
) {
}