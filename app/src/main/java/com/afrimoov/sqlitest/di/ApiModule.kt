package com.afrimoov.sqlitest.di

import com.afrimoov.sqlitest.api.ApiDataSources
import com.afrimoov.sqlitest.api.ApiNetworkImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ApiModule {

    @Binds
    fun bindsApiNetworkImpl(
        api: ApiNetworkImpl
    ) : ApiDataSources

}