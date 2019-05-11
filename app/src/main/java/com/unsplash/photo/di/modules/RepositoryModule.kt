package com.unsplash.photo.di.modules

import com.google.gson.Gson
import com.unsplash.photo.UnsplashApp.Companion.PAGE_SIZE
import com.unsplash.photo.api.PhotoService
import com.unsplash.photo.db.d.UnsplashDatabase
import com.unsplash.photo.repository.EditorialRepository
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideFeedsRepositoryProvider(
        database: UnsplashDatabase,
        userService: PhotoService,
        gson: Gson,
        ioExecutor: Executor
    ): EditorialRepository {
        return EditorialRepository(database, gson, userService, PAGE_SIZE, ioExecutor)
    }
}