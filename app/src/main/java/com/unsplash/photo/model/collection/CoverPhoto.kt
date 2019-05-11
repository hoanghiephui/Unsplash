package com.unsplash.photo.model.collection

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class CoverPhoto(
    @field:SerializedName("color")
    var color: String? = null,

    @field:SerializedName("created_at")
    var createdAt: String? = null,

    @field:SerializedName("description")
    var description: String? = null,

    @field:SerializedName("sponsored")
    var sponsored: Boolean? = null,

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
    var id: String = "",

    @field:SerializedName("categories")
    var categories: List<Category>? = null,

    @field:SerializedName("user")
    var user: User? = null,

    @field:SerializedName("height")
    var height: Int? = null,

    @field:SerializedName("likes")
    var likes: Int? = null
) : Parcelable