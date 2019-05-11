package com.paweloot.flickrapp.add_image

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.paweloot.flickrapp.R
import com.paweloot.flickrapp.common.IMAGE_DATE
import com.paweloot.flickrapp.common.IMAGE_TITLE
import com.paweloot.flickrapp.common.IMAGE_URL
import kotlinx.android.synthetic.main.activity_add_image.*
import java.util.*

class AddImageActivity : AppCompatActivity(), AddImageContract.View, DatePickerDialog.OnDateSetListener {
    companion object {
        const val FRAGMENT_DATE_PICKER = "datePicker"
    }

    private lateinit var presenter: AddImageContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image)

        presenter = AddImagePresenter(this)

        setDefaultImageDateAsCurrent()
        edit_image_date.setOnClickListener {
            presenter.onEditDateClicked()
        }

        button_add_image.setOnClickListener {
            presenter.onAddImageButtonClicked()
        }
    }

    private fun setDefaultImageDateAsCurrent() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH) + 1
        val day = c.get(Calendar.DAY_OF_MONTH)

        val currentDate = "$day/$month/$year"
        edit_image_date.setText(currentDate)
    }

    override fun showDatePickerDialog() {
        val datePicker = DatePickerFragment(this)
        datePicker.show(supportFragmentManager, FRAGMENT_DATE_PICKER)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        val pickedDate = "$day/${month + 1}/$year"
        edit_image_date.setText(pickedDate)
    }

    override fun addNewImage() {
        if (isInputNotEmpty()) {
            returnNewImageData()
        } else {
            displayErrorMessage()
        }
    }

    private fun isInputNotEmpty(): Boolean {
        return (edit_image_url.text.isNotEmpty() &&
                edit_image_title.text.isNotEmpty())
    }

    private fun returnNewImageData() {
        val resultIntent = Intent()
        resultIntent.putExtra(IMAGE_URL, edit_image_url.text.toString())
        resultIntent.putExtra(IMAGE_TITLE, edit_image_title.text.toString())
        resultIntent.putExtra(IMAGE_DATE, edit_image_date.text.toString())
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
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