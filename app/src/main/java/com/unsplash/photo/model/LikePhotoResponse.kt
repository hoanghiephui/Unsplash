package com.unsplash.photo.model

import com.google.gson.annotations.SerializedName
import com.unsplash.photo.model.collection.User

data class LikePhotoResponse(

    @field:SerializedName("photo")
    val photo: PhotoResponse? = null,

    @field:SerializedName("user")
    val user: User? = null
)