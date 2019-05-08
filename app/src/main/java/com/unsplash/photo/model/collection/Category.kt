package com.unsplash.photo.model.collection

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Category(
    @PrimaryKey
    val id: Int?,
    val title: String?,
    val photo_count: Int?,
    val links: CategoryLinks?
) : Parcelable

@Parcelize
@Entity
data class CategoryLinks(
    val self: String?,
    val photos: String
) : Parcelable