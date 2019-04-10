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
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var presenter: MainContract.Presenter

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

//    private lateinit var currImageUrls: JSONArray

    private val addImageRequestCode: Int = 666

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this)

        viewManager = LinearLayoutManager(this)
        viewAdapter = MainRecyclerViewAdapter(presenter.fetchImageUrls(fetchSharedPref()))

        image_recycler_view.layoutManager = viewManager
        image_recycler_view.adapter = viewAdapter

        addDeleteOnSwipe()
    }

    private fun addDeleteOnSwipe() {
        val swipeHandler = object : MainSwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = viewAdapter as MainRecyclerViewAdapter
                adapter.removeImageAt(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(image_recycler_view)
    }

    override fun onStop() {
        super.onStop()
        saveImageUrls()
    }

    private fun saveImageUrls() {
        with(fetchSharedPref().edit()) {
//            putString("image_urls_data", currImageUrls.toString())
            putString("image_urls_data", (viewAdapter as MainRecyclerViewAdapter).getData().toString())
            apply()
        }
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
                addImageRequestCode -> {
                    val imageUrl = data?.getStringExtra("imageUrl")

                    if (imageUrl != null && imageUrl.isNotEmpty()) {
                        val adapter = viewAdapter as MainRecyclerViewAdapter
                        adapter.addImage(imageUrl)
                    }
                }
            }
        }
    }

    override fun addImage() {
        val intent = Intent(this, AddImageActivity::class.java)
        startActivityForResult(intent, addImageRequestCode)
    }

    override fun fetchSharedPref(): SharedPreferences {
        return getSharedPreferences(
            getString(R.string.fkr_image_urls_sharedpref),
            Context.MODE_PRIVATE
        )
    }
}
