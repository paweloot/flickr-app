package com.paweloot.flickrapp.add_image

interface AddImageContract {
    interface Presenter {
        fun onEditDateClicked()
        fun onAddImageButtonClicked()
    }

    interface View {
        fun showDatePickerDialog()
        fun addNewImage()
    }
}