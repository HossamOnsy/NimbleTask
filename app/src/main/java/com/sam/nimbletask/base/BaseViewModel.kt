package com.sam.nimbletask.base

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

open class BaseViewModel : ViewModel() {


    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()


    fun onRetrieveDataError(error: Throwable?) {
        Timber.d("accessToken ${error!!.message}")

        loadingVisibility.value = View.GONE
        if (error.message != null)
            errorMessage.value = error.message
        else
            errorMessage.value = "Error Occured , Please try again later ..."
    }


    fun onRetrieveDataFinish() {

        loadingVisibility.value = View.GONE
    }

    fun onRetrieveDataStart() {
        errorMessage.value = null
        loadingVisibility.value = View.VISIBLE
    }


}