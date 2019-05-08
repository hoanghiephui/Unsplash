package com.unsplash.photo.ui.fragments

import android.os.Bundle
import android.view.View
import com.unsplash.photo.R
import com.unsplash.photo.base.BaseFragment
import com.unsplash.photo.ui.adapters.ViewStatePagerAdapter
import com.unsplash.photo.usecase.SplashUseCase
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {
    private val splashBundle: SplashUseCase.SplashModel by lazy {
        arguments?.getParcelable(DATA) ?: SplashUseCase.SplashModel(
            mutableListOf(), ""
        )
    }
    private val viewPageAdapter by lazy {
        ViewStatePagerAdapter(childFragmentManager)
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        viewPageAdapter.apply {
            listTitle.apply {
                add(getString(R.string.dashboard))
                add(getString(R.string.collections))
                add(getString(R.string.notify))
            }
            listFragment.apply {
                add(DashboardFragment.newInstance(splashBundle))
                add(DashboardFragment.newInstance(splashBundle))
                add(DashboardFragment.newInstance(splashBundle))
            }
        }
        viewPage.apply {
            offscreenPageLimit = 3
        }.adapter = viewPageAdapter
    }

    companion object {
        const val DATA = "data"
    }
}