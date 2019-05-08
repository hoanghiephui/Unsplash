package com.unsplash.photo.model.collection

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class User(

    @field:SerializedName("total_photos")
    val totalPhotos: Int? = null,

    @field:SerializedName("accepted_tos")
    val acceptedTos: Boolean? = null,

    @field:SerializedName("twitter_username")
    val twitterUsername: String? = null,

    @field:SerializedName("last_name")
    val lastName: String? = null,

    @field:SerializedName("bio")
    val bio: String? = null,

    @field:SerializedName("total_likes")
    val totalLikes: Int? = null,

    @field:SerializedName("portfolio_url")
    val portfolioUrl: String? = null,

    @field:SerializedName("profile_image")
    val profileImage: ProfileImage? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("links")
    val links: Links? = null,

    @field:SerializedName("total_collections")
    val totalCollections: Int? = null,

    @PrimaryKey
    @field:SerializedName("id")
    val id: String = "",

    @field:SerializedName("first_name")
    val firstName: String? = null,

    @field:SerializedName("instagram_username")
    val instagramUsername: String? = null,

    @field:SerializedName("username")
    val username: String? = null
) : Parcelable