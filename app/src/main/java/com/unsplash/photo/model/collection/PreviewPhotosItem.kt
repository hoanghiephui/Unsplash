package com.unsplash.photo.model.collection

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class PreviewPhotosItem(

    @field:SerializedName("urls")
    val urls: Urls? = null,

    @PrimaryKey
    @field:SerializedName("id")
    var id: String = ""
) : Parcelable