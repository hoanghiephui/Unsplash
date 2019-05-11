package com.unsplash.photo.ui.adapters.viewholder

import android.view.View
import android.view.ViewGroup
import com.unsplash.photo.R
import com.unsplash.photo.extensions.inflate
import com.unsplash.photo.repository.base.NetworkState
import com.unsplash.photo.repository.base.Status
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.network_state_item.*

class LoadingViewHolder<T>(
    parent: ViewGroup,
    private val retryCallback: () -> Unit
) : BaseViewHolder<T>(
    parent.inflate(R.layout.network_state_item)
), LayoutContainer {
    override val containerView: View?
        get() = itemView

    init {
        retry.setOnClickListener {
            retryCallback()
        }
    }

    override fun bind(item: T) = Unit

    fun onBind(networkState: NetworkState?) {
        progressBar.visibility = toVisibility(networkState?.status == Status.RUNNING)
        retry.visibility = toVisibility(networkState?.status == Status.FAILED)
        errorMsg.visibility = toVisibility(networkState?.msg != null)
        errorMsg.text = networkState?.msg
    }

    companion object {
        fun toVisibility(constraint: Boolean): Int {
            return if (constraint) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}