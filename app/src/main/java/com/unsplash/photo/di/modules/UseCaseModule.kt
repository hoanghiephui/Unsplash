package com.unsplash.photo.di.modules

import com.unsplash.photo.di.scopes.PerFragment
import com.unsplash.photo.usecase.SplashUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class, DatabaseModule::class])
class UseCaseModule {
    @PerFragment
    @Provides
    fun provideNotificationUseCase(
    ): SplashUseCase {
        return SplashUseCase()
    }
}