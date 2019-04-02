package com.paweloot.flickrapp.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paweloot.flickrapp.R

class MainActivity : AppCompatActivity(), MainContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
    }
}
