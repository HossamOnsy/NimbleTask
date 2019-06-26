package com.sam.nimbletask.di.modules

import androidx.lifecycle.ViewModel
import com.sam.nimbletask.viewmodels.SurveriesViewModel
import com.sam.nimbletask.factory.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MyViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SurveriesViewModel::class)
    abstract fun bindMyViewModel(myViewModel: SurveriesViewModel): ViewModel
}