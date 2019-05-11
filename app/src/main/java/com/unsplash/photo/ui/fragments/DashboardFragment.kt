package com.unsplash.photo.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.SparseArray
import android.view.View
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import com.annimon.stream.Collectors
import com.annimon.stream.Stream
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.unsplash.photo.R
import com.unsplash.photo.base.BaseFragment
import com.unsplash.photo.extensions.observeNotNull
import com.unsplash.photo.model.PhotoResponse.Companion.TYPE_LATEST
import com.unsplash.photo.model.PhotoResponse.Companion.TYPE_OLDEST
import com.unsplash.photo.model.PhotoResponse.Companion.TYPE_POPULAR
import com.unsplash.photo.repository.base.NetworkState
import com.unsplash.photo.ui.activitys.LoginActivity
import com.unsplash.photo.ui.activitys.MainActivity
import com.unsplash.photo.ui.adapters.PhotoAdapter
import com.unsplash.photo.ui.widget.TopLinearLayoutManager
import com.unsplash.photo.usecase.SplashUseCase
import com.unsplash.photo.viewmodels.EditorialViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.util.*


class DashboardFragment : BaseFragment() {
    private val splashBundle: SplashUseCase.SplashModel by lazy {
        arguments?.getParcelable(HomeFragment.DATA) ?: SplashUseCase.SplashModel(
            mutableListOf(), ""
        )
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[EditorialViewModel::class.java]
    }
    private val adapter by lazy {
        PhotoAdapter(retryCallback = {
            viewModel.retry()
        }, onClickPhoto = {

        }, onDownload = {

        }, onAddToColections = {

        }, onLike = { photo, positions ->
            if (baseActivity().isAuthorized()) {
                SparseArray<Any>().apply {
                    put(0, photo.id)
                    put(1, photo.likedByUser)
                    viewModel.onLikePhoto(this, positions, photo)
                }
            } else {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                baseActivity().overridePendingTransition(R.anim.activity_slide_in, R.anim.none)
            }
        })
    }
    private var positionMenu = 0

    override fun layoutRes(): Int {
        return R.layout.fragment_dashboard
    }

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        baseActivity().setSupportActionBar(toolbar)
        Glide.with(app_bar_image)
            .load(splashBundle.urlImage)
            .into(app_bar_image)
        val tag = Stream.of(splashBundle.listTag).collect(Collectors.joining(", "))
        tvTag.text = getString(R.string.trending_search, tag)
        recyclerView.apply {
            layoutManager = TopLinearLayoutManager(context)
            adapter = this@DashboardFragment.adapter
        }
        initSwipeToRefresh()
        val login = arguments?.getBoolean("login")
        if (login == true) {
            Handler().postDelayed({
                viewModel.refresh()
            }, 1000)
            swRefresh.isRefreshing = true
        }
        viewModel.showPhotos(TYPE_LATEST)
        viewModel.photoList.observeNotNull(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.networkState.observeNotNull(viewLifecycleOwner) {
            adapter.setNetworkState(it)
        }

        viewModel.likePhoto.observeNotNull(viewLifecycleOwner) {
            viewModel.currentPhoto()?.likedByUser = it.photo?.likedByUser
            viewModel.currentPhoto()?.likes = it.photo?.likes
            viewModel.currentPhoto()?.progressLike = false
            viewModel.currentPosition()?.let { it1 -> recyclerView.adapter?.notifyItemChanged(it1) }
            viewModel.currentPhoto()?.let { it1 -> viewModel.onUpdatePhotoToDb(it1) }
        }
        viewModel.networkLikeState.observeNotNull(viewLifecycleOwner) {

        }

        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            //  Vertical offset == 0 indicates appBar is fully expanded.
            tvTag.isVisible = Math.abs(verticalOffset) <= 200
            (requireActivity() as MainActivity).viewModel.scrollLiveData.postValue(Math.abs(verticalOffset) <= 200)
        })
        collapseActionView.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
        }

        btnMore.setOnClickListener {
            PopupMenu(requireContext(), it).apply {
                inflate(R.menu.menu_editorial)
                this.menu.getItem(positionMenu).isChecked = true
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.menu_latest -> {
                            menuItem.isChecked = !menuItem.isChecked
                            positionMenu = 0
                            viewModel.showPhotos(TYPE_LATEST)
                            return@setOnMenuItemClickListener true
                        }
                        R.id.menu_oldest -> {
                            menuItem.isChecked = !menuItem.isChecked
                            positionMenu = 1
                            viewModel.showPhotos(TYPE_OLDEST)
                            return@setOnMenuItemClickListener true
                        }
                        R.id.menu_popular -> {
                            menuItem.isChecked = !menuItem.isChecked
                            positionMenu = 2
                            viewModel.showPhotos(TYPE_POPULAR)
                            return@setOnMenuItemClickListener true
                        }
                    }
                    return@setOnMenuItemClickListener false
                }
            }.show()
        }
    }

    private fun initSwipeToRefresh() {
        viewModel.refreshState.observeNotNull(viewLifecycleOwner) {
            swRefresh.isRefreshing = it == NetworkState.LOADING
        }
        swRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    companion object {
        fun newInstance(data: SplashUseCase.SplashModel, isLogin: Boolean) = DashboardFragment().apply {
            arguments =
                bundleOf(HomeFragment.DATA to data, "login" to isLogin)
        }
    }
}