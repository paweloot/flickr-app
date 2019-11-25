package com.paweloot.flickrapp

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.paweloot.flickrapp.api.FlickrFetcher

class PollWorker(val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        val query = QueryPreferences.getStoredQuery(context)
        val lastResultId = QueryPreferences.getLastResultId(context)
        val items = if (query.isBlank()) {
            FlickrFetcher().fetchPhotosRequest()
                .execute()
                .body()
                ?.photos
                ?.galleryItems
        } else {
            FlickrFetcher().searchPhotosRequest(query)
                .execute()
                .body()
                ?.photos
                ?.galleryItems
        } ?: emptyList()

        if (items.isEmpty())
            return Result.success()

        val resultId = items.first().id
        if (resultId != lastResultId) {
            QueryPreferences.setLastResultId(context, resultId)
        }

        return Result.success()
    }
}