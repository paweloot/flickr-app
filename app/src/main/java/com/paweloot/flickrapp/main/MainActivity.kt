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
import com.paweloot.flickrapp.image.ImageActivity
import kotlinx.android.synthetic.main.activity_main.*
import com.paweloot.flickrapp.common.*

class MainActivity : AppCompatActivity(), MainContract.View, MainRecyclerViewAdapter.OnImageClickListener {
    companion object {
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
        addSampleImages()
    }

    private fun addSampleImages() {
        val date = "16/5/2019"
        presenter.generateTagsAndAddImage("https://images.unsplash.com/photo-1489278353717-f64c6ee8a4d2?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
             "One", date)
        presenter.generateTagsAndAddImage("https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
            "Two", date)
        presenter.generateTagsAndAddImage("https://images.unsplash.com/photo-1521225099409-8e1efc95321d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
            "Three", date)
        presenter.generateTagsAndAddImage("https://images.unsplash.com/photo-1492633423870-43d1cd2775eb?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
            "Cuatro", date)
        presenter.generateTagsAndAddImage("https://images.unsplash.com/photo-1544507888-56d73eb6046e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
            "Cinco", date)
        presenter.generateTagsAndAddImage("https://images.unsplash.com/photo-1535982606227-475c9bf94018?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
            "Seis", date)
        presenter.generateTagsAndAddImage("https://images.unsplash.com/photo-1508280756091-9bdd7ef1f463?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
            "Sept", date)
        presenter.generateTagsAndAddImage("https://images.unsplash.com/photo-1512485694743-9c9538b4e6e0?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
            "Huit", date)
    }

    override fun onStop() {
        super.onStop()
        saveImageUrls()
    }

    private fun addDeleteOnSwipe() {
        val swipeHandler = object : MainSwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = viewAdapter as MainRecyclerViewAdapter
                adapter.removeImageAt(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(image_recycler_view)
    }

    private fun saveImageUrls() {
        with(fetchSharedPref().edit()) {
            putString(IMAGE_DATA, (viewAdapter as MainRecyclerViewAdapter).getData().toString())
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

    override fun addImage() {
        val intent = Intent(this, AddImageActivity::class.java)
        startActivityForResult(intent, ADD_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_IMAGE_REQUEST_CODE -> {
                    val imageUrl = data?.getStringExtra(IMAGE_URL)
                    val imageTitle = data?.getStringExtra(IMAGE_TITLE)
                    val imageDate = data?.getStringExtra(IMAGE_DATE)

                    if (imageUrl != null && imageTitle != null && imageDate != null) {
                        presenter.generateTagsAndAddImage(imageUrl, imageTitle, imageDate)
                    } else {
                        Toast.makeText(this, R.string.error_oops, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun addImageToAdapter(url: String, title: String, date: String, tags: String) {
        val adapter = viewAdapter as MainRecyclerViewAdapter
        adapter.addImage(url, title, date, tags)
    }

    override fun fetchSharedPref(): SharedPreferences {
        return getSharedPreferences(
            PREF_IMAGES,
            Context.MODE_PRIVATE
        )
    }

    override fun onImageClick(position: Int) {
        val adapter = viewAdapter as MainRecyclerViewAdapter

        val intent = Intent(this, ImageActivity::class.java)
        intent.putExtra(IMAGE_DATA, adapter.getData().toString())
        intent.putExtra(IMAGE_POSITION, position)
        startActivity(intent)
    }
}
