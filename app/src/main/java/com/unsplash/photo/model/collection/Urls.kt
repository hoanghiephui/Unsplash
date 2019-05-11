package com.unsplash.photo.model.collection

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Urls(
    @PrimaryKey
    @field:SerializedName("small")
    var small: String = "",

    @field:SerializedName("thumb")
    var thumb: String? = null,

    @field:SerializedName("raw")
    var raw: String? = null,

    @field:SerializedName("regular")
    var regular: String? = null,

    @field:SerializedName("full")
    var full: String? = null
) : Parcelable