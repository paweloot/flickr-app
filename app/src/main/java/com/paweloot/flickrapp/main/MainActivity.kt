package com.paweloot.flickrapp.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paweloot.flickrapp.R
import com.paweloot.flickrapp.add_image.AddImageActivity
import kotlinx.android.synthetic.main.activity_photo.*

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var presenter: MainContract.Presenter

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    private val ADD_IMAGE_REQUEST_CODE: Int = 666

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        presenter = MainPresenter(this)

//        sampleData.put("https://images.unsplash.com/photo-1485199433301-8b7102e86995?ixlib=rb-" +
//                "1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1380&q=80")

        viewManager = LinearLayoutManager(this)
        viewAdapter = MainRecyclerViewAdapter(presenter.fetchImageUrls(fetchSharedPref()))

        photo_recycler_view.layoutManager = viewManager
        photo_recycler_view.adapter = viewAdapter

        val swipeHandler = object : MainSwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = photo_recycler_view.adapter as MainRecyclerViewAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(photo_recycler_view)
    }

    override fun onResume() {
        super.onResume()

        viewAdapter = MainRecyclerViewAdapter(presenter.fetchImageUrls(fetchSharedPref()))
        photo_recycler_view.adapter = viewAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        supportActionBar?.title = "Flickr"

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_action_add -> {
                addImage()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_IMAGE_REQUEST_CODE -> {
                    val imageUrl = data?.getStringExtra("imageUrl")

                    if (imageUrl != null) {
                        presenter.saveImageUrl(
                            getSharedPreferences(
                                getString(R.string.fkr_image_urls_sharedpref),
                                Context.MODE_PRIVATE
                            ), imageUrl
                        )
                    }
                }
            }
        }
    }

    override fun addImage() {
        val intent = Intent(this, AddImageActivity::class.java)
        startActivityForResult(intent, ADD_IMAGE_REQUEST_CODE)
    }

    override fun fetchSharedPref(): SharedPreferences {
        return getSharedPreferences(
            getString(R.string.fkr_image_urls_sharedpref),
            Context.MODE_PRIVATE
        )
    }
}
