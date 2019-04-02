package com.paweloot.flickrapp.photo

import android.os.Bundle
import android.view.MenuItem
import android.widget.SimpleAdapter
import androidx.core.app.NavUtils
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paweloot.flickrapp.R
import com.paweloot.flickrapp.common.BaseView
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : BaseView(), PhotoContract.View {
    private lateinit var presenter: PhotoContract.Presenter

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        setUpActionBar(false, "Flickr")

        presenter = PhotoPresenter(this)

        val sampleData: ArrayList<String> = arrayListOf("Oreo", "KitKat", "Marshmallow")

        viewManager = LinearLayoutManager(this)
        viewAdapter = PhotoRecyclerViewAdapter(sampleData)

        photo_recycler_view.layoutManager = viewManager
        photo_recycler_view.adapter = viewAdapter

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = photo_recycler_view.adapter as PhotoRecyclerViewAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(photo_recycler_view)
    }
}
