package com.paweloot.flickrapp.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

        photoRecyclerView = findViewById(R.id.image_recycler_view)

        photoRecyclerView.layoutManager = LinearLayoutManager(this)
        mainRecyclerViewViewModel.galleryItemLiveData.observe(
            this,
            Observer { galleryItems ->
                photoRecyclerView.adapter = PhotoAdapter(galleryItems)
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        supportActionBar?.title = getString(R.string.app_name)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
        }

        return super.onOptionsItemSelected(item)
    }

//    override fun onImageClick(position: Int) {
//        val adapter = viewAdapter as MainRecyclerViewAdapter
//
//        val intent = Intent(this, ImageActivity::class.java)
//        intent.putExtra(IMAGE_DATA, adapter.getData().toString())
//        intent.putExtra(IMAGE_POSITION, position)
//        startActivity(intent)
//    }
}
