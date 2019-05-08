package com.unsplash.photo.model.collection

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class TagsItem(

    @field:SerializedName("title")
    val title: String? = null
) : Parcelable