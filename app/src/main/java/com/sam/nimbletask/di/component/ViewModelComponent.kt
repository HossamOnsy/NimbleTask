package com.sam.nimbletask.di.component

import com.sam.nimbletask.ui.LoginActivity
import com.sam.nimbletask.di.modules.NetworkModule
import com.sam.nimbletask.di.modules.RepositoryModule
import com.sam.nimbletask.viewmodels.SurveriesViewModel
import dagger.Component


@Component(modules = [(NetworkModule::class),(RepositoryModule::class)])
interface ViewModelComponent {

    fun inject(surveriesViewModel: SurveriesViewModel)

}