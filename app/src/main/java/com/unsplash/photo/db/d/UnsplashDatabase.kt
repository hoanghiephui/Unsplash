package com.unsplash.photo.db.d

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.unsplash.photo.db.dao.PhotoDao
import com.unsplash.photo.model.PhotoResponse
import com.unsplash.photo.model.collection.*

const val VERSION = 2
const val DATABASE_NAME = "UnsplashDB.db"

@Database(
    version = VERSION, entities = [PhotoResponse::class, Category::class,
        CategoryLinks::class, CollectionResponse::class, CoverPhoto::class,
        Links::class, PreviewPhotosItem::class,
        ProfileImage::class, TagsItem::class,
        Urls::class, User::class], exportSchema = false
)
@TypeConverters(
    CollectionConverter::class, CategoryConverter::class,
    TagsItemConverter::class, PreviewPhotosItemConverter::class, UrlsConverter::class, LinksConverter::class,
    CoverPhotoConverter::class, UserConverter::class, CategoryLinksConverter::class, ProfileImageConverter::class
)
abstract class UnsplashDatabase : RoomDatabase() {
    abstract fun getPhotoDao(): PhotoDao
}