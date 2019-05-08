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
    val featured: Boolean? = null,

    @field:SerializedName("private")
    val jsonMemberPrivate: Boolean? = null,

    @field:SerializedName("cover_photo")
    val coverPhoto: CoverPhoto? = null,

    @field:SerializedName("total_photos")
    val totalPhotos: Int? = null,

    @field:SerializedName("share_key")
    val shareKey: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("tags")
    val tags: List<TagsItem>? = null,

    @field:SerializedName("preview_photos")
    val previewPhotos: List<PreviewPhotosItem>? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("curated")
    val curated: Boolean? = null,

    @field:SerializedName("links")
    val links: Links? = null,

    @PrimaryKey
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("published_at")
    val publishedAt: String? = null,

    @field:SerializedName("user")
    val user: User? = null
) : Parcelable