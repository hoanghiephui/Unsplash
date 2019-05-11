package com.unsplash.photo.model.collection

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class CollectionResponse(

    @field:SerializedName("featured")
    var featured: Boolean? = null,

    @field:SerializedName("private")
    var jsonMemberPrivate: Boolean? = null,

    @field:SerializedName("cover_photo")
    var coverPhoto: CoverPhoto? = null,

    @field:SerializedName("total_photos")
    var totalPhotos: Int? = null,

    @field:SerializedName("share_key")
    var shareKey: String? = null,

    @field:SerializedName("description")
    var description: String? = null,

    @field:SerializedName("title")
    var title: String? = null,

    @field:SerializedName("tags")
    var tags: List<TagsItem>? = null,

    @field:SerializedName("preview_photos")
    var previewPhotos: List<PreviewPhotosItem>? = null,

    @field:SerializedName("updated_at")
    var updatedAt: String? = null,

    @field:SerializedName("curated")
    var curated: Boolean? = null,

    @field:SerializedName("links")
    var links: Links? = null,

    @PrimaryKey
    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("published_at")
    var publishedAt: String? = null,

    @field:SerializedName("user")
    var user: User? = null
) : Parcelable