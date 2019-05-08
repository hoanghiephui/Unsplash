package com.unsplash.photo.di.modules

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.unsplash.photo.db.d.DATABASE_NAME
import com.unsplash.photo.db.d.UnsplashDatabase
import com.unsplash.photo.di.annotations.DataBase
import com.unsplash.photo.di.annotations.ForApplication
import dagger.Module
import dagger.Provides
import java.lang.reflect.Modifier
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ForApplication context: Context): UnsplashDatabase = Room.databaseBuilder(
        context,
        UnsplashDatabase::class.java, DATABASE_NAME
    )
        .allowMainThreadQueries() // allow querying on MainThread (this useful in some cases)
        .fallbackToDestructiveMigration() //  this mean that it will delete all tables and recreate them after version is changed
        .build()

    @DataBase
    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
        .disableHtmlEscaping()
        .setPrettyPrinting()
        .create()
}