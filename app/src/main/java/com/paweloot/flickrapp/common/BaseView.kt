package com.paweloot.flickrapp.common

import androidx.appcompat.app.AppCompatActivity

abstract class BaseView : AppCompatActivity() {
    fun setUpActionBar(showBackArrow: Boolean, title: String) {
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackArrow)
        supportActionBar?.title = title
    }
}