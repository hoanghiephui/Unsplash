package com.unsplash.photo.model

import android.content.Context
import android.os.Parcelable
import androidx.annotation.Size
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.unsplash.photo.model.PhotoResponse.Companion.TABLE_NAME_PHOTO
import com.unsplash.photo.model.collection.*
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Entity(tableName = TABLE_NAME_PHOTO,
    indices = [Index(value = ["type"], unique = false)])
@Parcelize
data class PhotoResponse(

    @field:SerializedName("current_user_collections")
    var currentUserCollections: List<CollectionResponse>? = null,

    @field:SerializedName("color")
    var color: String? = null,

    @field:SerializedName("created_at")
    var createdAt: String? = null,

    @field:SerializedName("description")
    var description: String? = null,

    @field:SerializedName("sponsored")
    var sponsored: Boolean? = null,

    @field:SerializedName("sponsored_impressions_id")
    var sponsoredImpressionsId: String? = null,

    @field:SerializedName("liked_by_user")
    var likedByUser: Boolean? = null,

    @field:SerializedName("urls")
    var urls: Urls? = null,

    @field:SerializedName("alt_description")
    var altDescription: String? = null,

    @field:SerializedName("updated_at")
    var updatedAt: String? = null,

    @field:SerializedName("width")
    var width: Int? = null,

    @field:SerializedName("links")
    var links: Links? = null,

    @PrimaryKey
    @field:SerializedName("id")
    var id: String,

    @field:SerializedName("categories")
    var categories: List<Category>? = null,

    @field:SerializedName("user")
    var user: User? = null,

    @field:SerializedName("height")
    var height: Int? = null,

    @field:SerializedName("likes")
    var likes: Int? = null,
    var type: Int = TYPE_LATEST
) : Parcelable {
    companion object {
        const val TABLE_NAME_PHOTO = "photo_tbl"
        const val TYPE_LATEST = 0
        const val TYPE_OLDEST = 1
        const val TYPE_POPULAR = 2
    }
    // to be consistent w/ changing backend order, we need to keep a data like this
    @IgnoredOnParcel
    var indexInResponse: Int = -1
    @IgnoredOnParcel
    var pageNumber = 1
    @IgnoredOnParcel
    var progressLike = false

    @Size(2)
    fun getRegularSize(context: Context): IntArray {
        val screenWidth = context.resources.displayMetrics.widthPixels
        val screenHeight = context.resources.displayMetrics.heightPixels

        val screenRatio = (1.0 * screenWidth / screenHeight).toFloat()
        val imageRatio = (1.0 * width!! / height!!).toFloat()

        if (imageRatio > screenRatio) {
            val h = Math.min(screenHeight / 2, height!!)
            val w = (1.0 * width!! / height!! * h).toInt()
            return intArrayOf(w, h)
        } else {
            val w = Math.min(screenWidth / 2, width!!)
            val h = (1.0 * height!! / width!! * w).toInt()
            return intArrayOf(w, h)
        }
    }

    fun getRegularSizeUrl(@Size(2) size: IntArray): String {
        return (urls?.raw
                + "?q=75&fm=jpg"
                + "&w=" + size[0]
                + "&h=" + size[1]
                + "&fit=max")
    }
}