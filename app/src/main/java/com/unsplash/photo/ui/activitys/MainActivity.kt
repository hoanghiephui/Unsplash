package com.unsplash.photo.ui.activitys

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.unsplash.photo.R
import com.unsplash.photo.base.BaseActivity
import com.unsplash.photo.extensions.Bundle
import com.unsplash.photo.extensions.replace
import com.unsplash.photo.ui.fragments.HomeFragment
import com.unsplash.photo.ui.fragments.HomeFragment.Companion.DATA
import com.unsplash.photo.ui.fragments.SplashFragment
import com.unsplash.photo.usecase.SplashUseCase
import com.unsplash.photo.viewmodels.HomeViewModel

class MainActivity : BaseActivity() {
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[HomeViewModel::class.java]
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        replace(R.id.container, SplashFragment(), SplashFragment::class.java.name)
    }

    fun onOpenHome(data: SplashUseCase.SplashModel) {
        val homeFragment = HomeFragment().apply {
            arguments = Bundle {
                putParcelable(DATA, data)
            }
        }
        replace(R.id.container, homeFragment, HomeFragment::class.java.name)
    }
}
