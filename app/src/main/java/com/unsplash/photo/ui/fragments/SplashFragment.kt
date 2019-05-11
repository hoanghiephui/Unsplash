package com.unsplash.photo.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.unsplash.photo.R
import com.unsplash.photo.base.BaseFragment
import com.unsplash.photo.extensions.observeNotNull
import com.unsplash.photo.ui.activitys.MainActivity
import com.unsplash.photo.viewmodels.SplashViewModel

class SplashFragment : BaseFragment() {
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[SplashViewModel::class.java]
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_splash
    }

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.data.observeNotNull(viewLifecycleOwner) {
            (requireActivity() as MainActivity).onOpenHome(it)
        }
        viewModel.networkState.observeNotNull(viewLifecycleOwner) {
            (requireActivity() as MainActivity).onOpenHome(null)
        }
        viewModel.loadSplash()
    }
}