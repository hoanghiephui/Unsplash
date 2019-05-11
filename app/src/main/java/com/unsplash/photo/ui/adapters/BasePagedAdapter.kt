package com.unsplash.photo.ui.adapters

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.unsplash.photo.repository.base.NetworkState
import com.unsplash.photo.ui.adapters.viewholder.BaseViewHolder
import com.unsplash.photo.ui.adapters.viewholder.LoadingViewHolder

abstract class BasePagedAdapter<M>(
    diffCallback: DiffUtil.ItemCallback<M?>,
    private val retryCallback: () -> Unit
) : PagedListAdapter<M, BaseViewHolder<M>>(diffCallback) {
    private var networkState: NetworkState? = null

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            FOOTER_VIEW_TYPE
        } else {
            CONTENT_VIEW_TYPE
        }
    }

    abstract fun contentViewHolder(parent: ViewGroup): BaseViewHolder<M>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<M> {
        return if (viewType == CONTENT_VIEW_TYPE) contentViewHolder(parent) else LoadingViewHolder(
            parent,
            retryCallback
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<M>, position: Int) {
        if (getItemViewType(position) == CONTENT_VIEW_TYPE) {
            getItem(position)?.let { holder.bind(it) }
        } else {
            (holder as LoadingViewHolder).onBind(networkState)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun getValue(position: Int) = getItem(position)

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        const val CONTENT_VIEW_TYPE = 1
        const val FOOTER_VIEW_TYPE = 2
    }
}

enum class CurrentState {
    DONE, LOADING, ERROR
}