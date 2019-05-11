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
    var totalPhotos: Int? = null,

    @field:SerializedName("accepted_tos")
    var acceptedTos: Boolean? = null,

    @field:SerializedName("twitter_username")
    var twitterUsername: String? = null,

    @field:SerializedName("last_name")
    var lastName: String? = null,

    @field:SerializedName("bio")
    var bio: String? = null,

    @field:SerializedName("total_likes")
    var totalLikes: Int? = null,

    @field:SerializedName("portfolio_url")
    var portfolioUrl: String? = null,

    @field:SerializedName("profile_image")
    var profileImage: ProfileImage? = null,

    @field:SerializedName("updated_at")
    var updatedAt: String? = null,

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("location")
    var location: String? = null,

    @field:SerializedName("links")
    var links: Links? = null,

    @field:SerializedName("total_collections")
    var totalCollections: Int? = null,

    @PrimaryKey
    @field:SerializedName("id")
    var id: String,

    @field:SerializedName("first_name")
    var firstName: String? = null,

    @field:SerializedName("instagram_username")
    var instagramUsername: String? = null,

    @field:SerializedName("username")
    var username: String? = null
) : Parcelable