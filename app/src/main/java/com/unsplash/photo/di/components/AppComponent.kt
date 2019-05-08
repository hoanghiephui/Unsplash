package com.unsplash.photo.di.components

import android.app.Application
import com.unsplash.photo.UnsplashApp
import com.unsplash.photo.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        ActivityBindingModule::class,
        FragmentBindingModule::class,
        AndroidSupportInjectionModule::class,
        NetworkModule::class]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun databaseModule(databaseModule: DatabaseModule): Builder

        @BindsInstance
        fun networkModule(networkModule: NetworkModule): Builder

        fun build(): AppComponent
    }


    fun inject(app: UnsplashApp)

    override fun inject(instance: DaggerApplication)

    companion object {
        fun getComponent(app: UnsplashApp): AppComponent = DaggerAppComponent.builder()
            .application(app)
            .databaseModule(DatabaseModule())
            .networkModule(NetworkModule())
            .build()
    }
}