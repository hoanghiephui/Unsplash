package com.unsplash.photo.di.modules

import com.unsplash.photo.di.scopes.PerFragment
import com.unsplash.photo.ui.fragments.*
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

    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindColectionsFragment(): CollectionsFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindAddCollectionFragment(): AddCollectionFragment
}