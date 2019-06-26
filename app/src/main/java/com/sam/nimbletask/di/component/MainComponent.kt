package com.sam.nimbletask.di.component

import android.content.Context
import com.sam.nimbletask.MyApplication
import com.sam.nimbletask.di.modules.MyViewModelModule
import com.sam.nimbletask.di.modules.NetworkModule
import com.sam.nimbletask.di.modules.RepositoryModule
import com.sam.nimbletask.di.modules.ViewModelFactoryModule
import com.sam.nimbletask.ui.activities.MainActivity
import com.sam.nimbletask.viewmodels.SurveriesViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class), (RepositoryModule::class),(ViewModelFactoryModule::class), (MyViewModelModule::class)])
interface MainComponent {


    fun inject(app: MyApplication)
    fun inject(mainActivity: MainActivity)

}