package com.sam.nimbletask.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sam.nimbletask.repository.SurveysRepository
import com.sam.nimbletask.viewmodels.SurveriesViewModel
import javax.inject.Inject

class SurveriesViewModelFactory @Inject constructor(private val repository: SurveysRepository): ViewModelProvider.Factory {

     override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SurveriesViewModel::class.java!!)) {
            SurveriesViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}