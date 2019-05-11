package com.unsplash.photo.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.unsplash.photo.di.annotations.ForApplication
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class ApplicationModule {
    @Provides
    @ForApplication
    fun providesApplicationContext(application: Application): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideDiskIOExecutors(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    @Provides
    fun sharedPreferences(@ForApplication context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}