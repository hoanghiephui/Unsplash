package com.unsplash.photo.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.annimon.stream.Collectors
import com.annimon.stream.Stream
import com.bumptech.glide.Glide
import com.unsplash.photo.R
import com.unsplash.photo.base.BaseFragment
import com.unsplash.photo.usecase.SplashUseCase
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : BaseFragment() {
    private val splashBundle: SplashUseCase.SplashModel by lazy {
        arguments?.getParcelable(HomeFragment.DATA) ?: SplashUseCase.SplashModel(
            mutableListOf(), ""
        )
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_dashboard
    }

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        Glide.with(app_bar_image)
            .load(splashBundle.urlImage)
            .into(app_bar_image)
        val tag = Stream.of(splashBundle.listTag).collect(Collectors.joining(", "))
        tvTag.text = getString(R.string.trending_search, tag)
    }

    companion object {
        fun newInstance(data: SplashUseCase.SplashModel) = DashboardFragment().apply {
            arguments =
                bundleOf(HomeFragment.DATA to data)
        }
    }
}