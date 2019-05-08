package com.unsplash.photo.model.collection

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Urls(

    @field:SerializedName("small")
    val small: String? = null,

    @field:SerializedName("thumb")
    val thumb: String? = null,

    @field:SerializedName("raw")
    val raw: String? = null,

    @field:SerializedName("regular")
    val regular: String? = null,

    @field:SerializedName("full")
    val full: String? = null
) : Parcelable