package com.unsplash.photo.db.d

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.unsplash.photo.model.PhotoResponse
import com.unsplash.photo.model.collection.*

const val VERSION = 1
const val DATABASE_NAME = "UnsplashDB"

@Database(
    version = VERSION, entities = [PhotoResponse::class, Category::class,
        CategoryLinks::class, CollectionResponse::class, CoverPhoto::class,
        Links::class, PreviewPhotosItem::class,
        ProfileImage::class, TagsItem::class,
        Urls::class, User::class], exportSchema = false
)
@TypeConverters(
    CollectionConverter::class, CategoryConverter::class,
    TagsItemConverter::class, PreviewPhotosItemConverter::class
)
abstract class UnsplashDatabase : RoomDatabase() {
}