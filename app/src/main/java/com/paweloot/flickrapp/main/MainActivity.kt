package com.paweloot.flickrapp.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paweloot.flickrapp.R
import com.paweloot.flickrapp.add_image.AddImageActivity
import com.paweloot.flickrapp.add_image.AddImageActivity.Companion.EXTRA_IMAGE_TITLE
import com.paweloot.flickrapp.add_image.AddImageActivity.Companion.EXTRA_IMAGE_URL
import com.paweloot.flickrapp.image.ImageActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), MainContract.View, MainRecyclerViewAdapter.OnImageClickListener {
    companion object {
        const val PREF_IMAGES = "com.paweloot.flickrapp.IMAGES"
        const val PREF_IMAGE_DATA = "IMAGE_DATA"
        private const val ADD_IMAGE_REQUEST_CODE = 666
    }

    private lateinit var presenter: MainContract.Presenter

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this)

        viewManager = LinearLayoutManager(this)
        viewAdapter = MainRecyclerViewAdapter(presenter.fetchImageUrls(fetchSharedPref()), this)

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
            putString(PREF_IMAGE_DATA, (viewAdapter as MainRecyclerViewAdapter).getData().toString())
            apply()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        supportActionBar?.title = getString(R.string.app_name)

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
                    val imageUrl = data?.getStringExtra(EXTRA_IMAGE_URL)
                    val imageTitle = data?.getStringExtra(EXTRA_IMAGE_TITLE)
                    val currentDate: String = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())

                    if (imageUrl != null && imageTitle != null) {
                        val adapter = viewAdapter as MainRecyclerViewAdapter
                        adapter.addImage(imageUrl, imageTitle, currentDate)
                    } else {
                        Toast.makeText(this, R.string.error_oops, Toast.LENGTH_SHORT).show()
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
            PREF_IMAGES,
            Context.MODE_PRIVATE
        )
    }

    override fun onImageClick(position: Int) {
        val imageUrl = (viewAdapter as MainRecyclerViewAdapter).getImageUrlAt(position)

        val intent = Intent(this, ImageActivity::class.java)
        intent.putExtra("URL", imageUrl)
        intent
        startActivity(intent)
    }
}
