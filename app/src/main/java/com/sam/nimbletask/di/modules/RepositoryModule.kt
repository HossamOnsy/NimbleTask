package com.sam.nimbletask.di.modules

import com.sam.nimbletask.network.RestApi
import com.sam.nimbletask.repository.SurveysRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideRepository(restApi: RestApi): SurveysRepository {
        return SurveysRepository(restApi)
    }

}