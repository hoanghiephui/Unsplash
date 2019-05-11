package com.unsplash.photo.di.modules

import com.unsplash.photo.di.scopes.PerActivity
import com.unsplash.photo.ui.activitys.LoginActivity
import com.unsplash.photo.ui.activitys.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @PerActivity
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun loginActivity(): LoginActivity
}