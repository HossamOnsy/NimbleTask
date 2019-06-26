package com.sam.nimbletask.di.modules

import android.content.Context
import com.sam.nimbletask.MyApplication
import com.sam.nimbletask.models.MyServiceInterceptor
import com.sam.nimbletask.network.RestApi
import com.sam.nimbletask.utils.BASE_URL
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule(val app: MyApplication) {


    @Provides
    @Singleton
    fun app(): MyApplication {
        return app
    }

    @Provides
    @Singleton
    fun context(): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideRestApi(retrofit: Retrofit): RestApi {
        return retrofit.create(RestApi::class.java)
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(interceptor).connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .followRedirects(true)
            .followSslRedirects(true)
            .addInterceptor(MyServiceInterceptor(context()))
        return client.build()
    }


    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Singleton
    internal fun provideRetrofitInterface(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

}