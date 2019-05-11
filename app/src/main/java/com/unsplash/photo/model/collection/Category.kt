package com.unsplash.photo.model.collection

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Category(
    @PrimaryKey
    var id: Int?,
    var title: String?,
    var photo_count: Int?,
    var links: CategoryLinks?
) : Parcelable

@Parcelize
@Entity
data class CategoryLinks(
    @PrimaryKey
    var self: String = "",
    var photos: String
) : Parcelable