package com.paweloot.flickrapp.api

import retrofit2.Call
import retrofit2.http.GET

interface FlickrApi {
    @GET(
        "services/rest/?method=flickr.interestingness.getList" +
                "&api_key=e9179c205502f3a8b2d7c66d9c4fd1bf" +
                "&format=json" +
                "&nojsoncallback=1" +
                "&extras=url_c,date_upload"
    )
    fun fetchPhotos(): Call<FlickrResponse>
}