package com.unsplash.photo.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.unsplash.photo.OnLoadImageListener
import com.unsplash.photo.R
import com.unsplash.photo.Utils.backgroundColor
import com.unsplash.photo.Utils.onLoadImageCover
import com.unsplash.photo.Utils.startSaturationAnimation
import com.unsplash.photo.extensions.inflate
import com.unsplash.photo.model.PhotoResponse
import com.unsplash.photo.ui.adapters.viewholder.BaseViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_photo.*

class PhotoAdapter(
    retryCallback: () -> Unit,
    private val onClickPhoto: (photo: PhotoResponse) -> Unit,
    private val onLike: (photo: PhotoResponse, positons: Int) -> Unit,
    private val onAddToColections: (photo: PhotoResponse) -> Unit,
    private val onDownload: (photo: PhotoResponse) -> Unit
) : BasePagedAdapter<PhotoResponse>(DIFF_CALLBACK, retryCallback) {

    override fun contentViewHolder(parent: ViewGroup): BaseViewHolder<PhotoResponse> {
        return PhotoViewHolder(
            parent.inflate(R.layout.item_photo),
            onClickPhoto = onClickPhoto,
            onLike = onLike,
            onAddToColections = onAddToColections,
            onDownload = onDownload
        )
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PhotoResponse?>() {
            override fun areItemsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse): Boolean =
                oldItem.id == newItem.id && oldItem.progressLike == newItem.progressLike

            override fun areContentsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse): Boolean =
                oldItem == newItem && oldItem.likedByUser == oldItem.likedByUser
        }
    }

    class PhotoViewHolder(
        itemView: View,
        private val onClickPhoto: (photo: PhotoResponse) -> Unit,
        private val onLike: (photo: PhotoResponse,
                             positions: Int) -> Unit,
        private val onAddToColections: (photo: PhotoResponse) -> Unit,
        private val onDownload: (photo: PhotoResponse) -> Unit
    ) : BaseViewHolder<PhotoResponse>(itemView), LayoutContainer {
        override val containerView: View?
            get() = itemView

        override fun bind(item: PhotoResponse) {
            imgPhoto.setSize(item.width, item.height)
            item.color?.let { backgroundColor(itemView.context, it) }?.let { imgPhoto.setBackgroundColor(it) }
            imgPhoto.setShowShadow(false)
            onLoadImageCover(
                imgPhoto,
                item,
                object : OnLoadImageListener {
                    override fun onCompleted() {
                        startSaturationAnimation(itemView.context, imgPhoto)
                        imgPhoto.setShowShadow(true)
                    }
                }
            )

            Glide.with(imgAvatar).apply {
                load(item.user?.profileImage?.medium)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgAvatar)
            }
            val colorLiked = ContextCompat.getColorStateList(itemView.context, R.color.colorAccent)
            val colorLike = ContextCompat.getColorStateList(itemView.context, R.color.colorGray)
            btnLike.apply {
                if (item.likedByUser == true) {
                    iconTint = colorLiked
                    setTextColor(colorLiked)
                } else {
                    iconTint = colorLike
                    setTextColor(colorLike)
                }
            }
            tvName.text = item.user?.name
            btnLike.text = item.likes?.toString()
            imgPhoto.setOnClickListener {
                onClickPhoto(item)
            }
            if (!item.progressLike) {
                progressLike.isVisible = false
                btnLike.icon = ContextCompat.getDrawable(itemView.context, R.drawable.ic_favorite_grey_500_24dp)
                btnLike.text = item.likes?.toString()
                btnLike.isEnabled = true
            }
            btnLike.setOnClickListener {
                progressLike.isVisible = true
                btnLike.icon = null
                btnLike.text = null
                it.isEnabled = false
                item.progressLike = true
                onLike(item, layoutPosition)
            }
            btnAdd.setOnClickListener {
                onAddToColections(item)
            }
            btnDownload.setOnClickListener {
                onDownload(item)
            }
        }

        override fun onDetached() {
            Glide.get(itemView.context)
                .clearMemory()
        }
    }

    override fun onViewRecycled(holder: BaseViewHolder<PhotoResponse>) {
        holder.onDetached()
    }
}