package com.unsplash.photo.model.collection

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class TagsItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @field:SerializedName("title")
    var title: String? = null
) : Parcelable