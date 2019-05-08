package com.unsplash.photo.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.unsplash.photo.model.PhotoResponse.Companion.TABLE_NAME_PHOTO
import com.unsplash.photo.model.collection.*
import kotlinx.android.parcel.Parcelize

@Entity(tableName = TABLE_NAME_PHOTO)
@Parcelize
data class PhotoResponse(

    @field:SerializedName("current_user_collections")
    val currentUserCollections: List<CollectionResponse>? = null,

    @field:SerializedName("color")
    val color: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("sponsored")
    val sponsored: Boolean? = null,

    @field:SerializedName("sponsored_impressions_id")
    val sponsoredImpressionsId: Int? = null,

    @field:SerializedName("liked_by_user")
    val likedByUser: Boolean? = null,

    @field:SerializedName("urls")
    val urls: Urls? = null,

    @field:SerializedName("alt_description")
    val altDescription: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("width")
    val width: Int? = null,

    @field:SerializedName("links")
    val links: Links? = null,

    @PrimaryKey
    @field:SerializedName("id")
    var id: String = "",

    @field:SerializedName("categories")
    val categories: List<Category>? = null,

    @field:SerializedName("user")
    val user: User? = null,

    @field:SerializedName("height")
    val height: Int? = null,

    @field:SerializedName("likes")
    val likes: Int? = null
) : Parcelable {
    companion object {
        const val TABLE_NAME_PHOTO = "photo_tbl"
    }
}