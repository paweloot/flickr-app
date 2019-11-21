package com.paweloot.flickrapp.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.paweloot.flickrapp.api.FlickrFetcher
import com.paweloot.flickrapp.api.GalleryItem

class MainRecyclerViewViewModel: ViewModel() {

    val galleryItemLiveData: LiveData<List<GalleryItem>> = FlickrFetcher().fetchPhotos()

}