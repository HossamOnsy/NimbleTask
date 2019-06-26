package com.sam.nimbletask


import android.app.Application
import com.sam.nimbletask.di.component.DaggerMainComponent
import com.sam.nimbletask.di.modules.NetworkModule
import timber.log.Timber
import timber.log.Timber.DebugTree


class MyApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null

        fun applicationContext() : MyApplication {
            return instance as MyApplication
        }
    }

    override fun onCreate() {
        super.onCreate()

        val component = DaggerMainComponent.builder().networkModule(NetworkModule(applicationContext())).build()
        component.inject(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

    }


}