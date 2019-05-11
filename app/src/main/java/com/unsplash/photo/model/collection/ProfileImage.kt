package com.unsplash.photo.model.collection

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class ProfileImage(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @field:SerializedName("small")
    var small: String? = null,

    @field:SerializedName("large")
    var large: String? = null,

    @field:SerializedName("medium")
    var medium: String? = null
) : Parcelable