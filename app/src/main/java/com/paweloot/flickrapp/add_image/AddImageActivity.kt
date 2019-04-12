package com.paweloot.flickrapp.add_image

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paweloot.flickrapp.R
import kotlinx.android.synthetic.main.activity_add_image.*

class AddImageActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_IMAGE_URL = "imageUrl"
        const val EXTRA_IMAGE_TITLE = "imageTitle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image)

        button_add_image.setOnClickListener {
            if (isInputNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_IMAGE_URL, edit_image_url.text.toString())
                resultIntent.putExtra(EXTRA_IMAGE_TITLE, edit_image_title.text.toString())
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                displayErrorMessage()
            }
        }
    }

    private fun isInputNotEmpty(): Boolean {
        return (edit_image_url.text.isNotEmpty() &&
                edit_image_title.text.isNotEmpty())
    }

    private fun displayErrorMessage() {
        edit_image_url.error = when {
            edit_image_url.text.isEmpty() -> getString(R.string.error_empty_input)
            else -> null
        }

        edit_image_title.error = when {
            edit_image_title.text.isEmpty() -> getString(R.string.error_empty_input)
            else -> null
        }
    }
}