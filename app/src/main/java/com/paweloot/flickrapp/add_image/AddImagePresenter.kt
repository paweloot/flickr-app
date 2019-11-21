package com.paweloot.flickrapp.add_image

class AddImagePresenter(private val view: AddImageContract.View): AddImageContract.Presenter {
    override fun onEditDateClicked() {
        view.showDatePickerDialog()
    }

    override fun onAddImageButtonClicked() {
        view.addNewImage()
    }
}