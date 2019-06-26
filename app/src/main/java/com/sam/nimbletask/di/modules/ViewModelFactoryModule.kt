package com.sam.nimbletask.di.modules

import androidx.lifecycle.ViewModelProvider
import com.sam.nimbletask.factory.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory
}