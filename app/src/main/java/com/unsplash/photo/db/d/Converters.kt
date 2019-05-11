package com.unsplash.photo.db.d

import androidx.room.TypeConverter
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.unsplash.photo.model.collection.*
import java.lang.reflect.Modifier
import java.lang.reflect.Type

private fun Any.toJson() = gson.toJson(this)

private fun <T> String?.fromJson(clazz: Class<T>) = kotlin.runCatching { gson.fromJson(this, clazz) }.getOrElse {
    gsonFallback.fromJson(this, clazz)
}

private fun <T> String?.fromJson(type: Type) = kotlin.runCatching { gson.fromJson<T>(this, type) }.getOrElse {
    gsonFallback.fromJson<T>(this, type)
}

private val gson = GsonBuilder()
    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
    .setDateFormat("yyyy-MM-dd HH:mm:ss")
    .disableHtmlEscaping()
    .setPrettyPrinting()
    .create()

private val gsonFallback = GsonBuilder()
    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
    .setDateFormat("MMM d, yyyy HH:mm:ss")
    .disableHtmlEscaping()
    .setPrettyPrinting()
    .create()

class CollectionConverter {
    @TypeConverter
    fun toDbValue(data: List<CollectionResponse>? = null): String? = data?.toJson()
    @TypeConverter
    fun fromDbToValue(data: String? = null): List<CollectionResponse>? = data.fromJson(object :
        TypeToken<List<CollectionResponse>>() {}.type)
}

class CategoryConverter {
    @TypeConverter
    fun toDbValue(data: List<Category>? = null): String? = data?.toJson()
    @TypeConverter
    fun fromDbToValue(data: String? = null): List<Category>? = data.fromJson(object :
        TypeToken<List<Category>>() {}.type)
}

class TagsItemConverter {
    @TypeConverter
    fun toDbValue(data: List<TagsItem>? = null): String? = data?.toJson()
    @TypeConverter
    fun fromDbToValue(data: String? = null): List<TagsItem>? = data.fromJson(object :
        TypeToken<List<TagsItem>>() {}.type)
}

class PreviewPhotosItemConverter {
    @TypeConverter
    fun toDbValue(data: List<PreviewPhotosItem>? = null): String? = data?.toJson()
    @TypeConverter
    fun fromDbToValue(data: String? = null): List<PreviewPhotosItem>? = data.fromJson(object :
        TypeToken<List<PreviewPhotosItem>>() {}.type)
}

class UrlsConverter {
    @TypeConverter
    fun toDbValue(data: Urls? = null): String? = data?.toJson()

    @TypeConverter
    fun fromDbToValue(data: String? = null): Urls? = data.fromJson(Urls::class.java)
}

class LinksConverter {
    @TypeConverter
    fun toDbValue(data: Links? = null): String? = data?.toJson()

    @TypeConverter
    fun fromDbToValue(data: String? = null): Links? = data.fromJson(Links::class.java)
}

class CoverPhotoConverter {
    @TypeConverter
    fun toDbValue(data: CoverPhoto? = null): String? = data?.toJson()

    @TypeConverter
    fun fromDbToValue(data: String? = null): CoverPhoto? = data.fromJson(CoverPhoto::class.java)
}

class UserConverter {
    @TypeConverter
    fun toDbValue(data: User? = null): String? = data?.toJson()

    @TypeConverter
    fun fromDbToValue(data: String? = null): User? = data.fromJson(User::class.java)
}

class CategoryLinksConverter {
    @TypeConverter
    fun toDbValue(data: CategoryLinks? = null): String? = data?.toJson()

    @TypeConverter
    fun fromDbToValue(data: String? = null): CategoryLinks? = data.fromJson(CategoryLinks::class.java)
}

class ProfileImageConverter {
    @TypeConverter
    fun toDbValue(data: ProfileImage? = null): String? = data?.toJson()

    @TypeConverter
    fun fromDbToValue(data: String? = null): ProfileImage? = data.fromJson(ProfileImage::class.java)
}