package com.paweloot.flickrapp.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paweloot.flickrapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var mainRecyclerViewViewModel: MainRecyclerViewViewModel

    private lateinit var photoRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainRecyclerViewViewModel =
            ViewModelProviders.of(this).get(MainRecyclerViewViewModel::class.java)

        photoRecyclerView = findViewById(R.id.photo_recycler_view)

        photoRecyclerView.layoutManager = LinearLayoutManager(this)
        mainRecyclerViewViewModel.galleryItemLiveData.observe(
            this,
            Observer { galleryItems ->
                photoRecyclerView.adapter = PhotoAdapter(galleryItems)
            }
        )
    }
}
