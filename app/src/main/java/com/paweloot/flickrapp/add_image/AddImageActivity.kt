package com.paweloot.flickrapp.add_image

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paweloot.flickrapp.R
import kotlinx.android.synthetic.main.activity_add_image.*

class AddImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image)

        addImageBTN.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("imageUrl", imageAddrET.text.toString())
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}