package com.unsplash.photo.model.collection

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Links(

    @field:SerializedName("followers")
    val followers: String? = null,

    @field:SerializedName("portfolio")
    val portfolio: String? = null,

    @field:SerializedName("following")
    val following: String? = null,

    @field:SerializedName("self")
    val self: String? = null,

    @field:SerializedName("html")
    val html: String? = null,

    @field:SerializedName("photos")
    val photos: String? = null,

    @field:SerializedName("likes")
    val likes: String? = null,

    @field:SerializedName("related")
    val related: String? = null,

    @field:SerializedName("download")
    val download: String? = null,

    @field:SerializedName("download_location")
    val downloadLocation: String? = null
) : Parcelable