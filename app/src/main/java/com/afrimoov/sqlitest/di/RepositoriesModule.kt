package com.afrimoov.sqlitest.di

import com.afrimoov.sqlitest.repositories.EmployeesRepository
import com.afrimoov.sqlitest.repositories.EmployeesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Binds
    fun provideAEmployeesRepository(
        repositoryImpl: EmployeesRepositoryImpl
    ) : EmployeesRepository

}