package com.unsplash.photo.model.collection

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Links(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @field:SerializedName("followers")
    var followers: String? = null,

    @field:SerializedName("portfolio")
    var portfolio: String? = null,

    @field:SerializedName("following")
    var following: String? = null,

    @field:SerializedName("self")
    var self: String? = null,

    @field:SerializedName("html")
    var html: String? = null,

    @field:SerializedName("photos")
    var photos: String? = null,

    @field:SerializedName("likes")
    var likes: String? = null,

    @field:SerializedName("related")
    var related: String? = null,

    @field:SerializedName("download")
    var download: String? = null,

    @field:SerializedName("download_location")
    var downloadLocation: String? = null
) : Parcelable