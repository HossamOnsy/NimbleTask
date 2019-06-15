package com.sam.nimbletask.di.modules

import com.sam.nimbletask.network.RestApi
import com.sam.nimbletask.repository.SurveysRepository
import com.sam.nimbletask.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object RepositoryModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRepository(restApi: RestApi): SurveysRepository {
        return SurveysRepository(restApi)
    }

}