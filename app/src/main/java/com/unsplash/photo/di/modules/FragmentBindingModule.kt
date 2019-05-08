package com.unsplash.photo.di.modules

import com.unsplash.photo.di.scopes.PerFragment
import com.unsplash.photo.ui.fragments.DashboardFragment
import com.unsplash.photo.ui.fragments.HomeFragment
import com.unsplash.photo.ui.fragments.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindFragment(): HomeFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindSplashFragment(): SplashFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindDashboardFragment(): DashboardFragment
}