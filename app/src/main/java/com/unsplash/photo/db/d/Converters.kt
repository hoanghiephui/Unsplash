package com.unsplash.photo.db.d

import androidx.room.TypeConverter
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.unsplash.photo.model.collection.Category
import com.unsplash.photo.model.collection.CollectionResponse
import com.unsplash.photo.model.collection.PreviewPhotosItem
import com.unsplash.photo.model.collection.TagsItem
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
    fun toDbValue(data: CollectionResponse? = null): String? = data?.toJson()
    @TypeConverter
    fun fromDbToValue(data: String? = null): CollectionResponse? = data.fromJson(CollectionResponse::class.java)
}

class CategoryConverter {
    @TypeConverter
    fun toDbValue(data: Category? = null): String? = data?.toJson()
    @TypeConverter
    fun fromDbToValue(data: String? = null): Category? = data.fromJson(Category::class.java)
}

class TagsItemConverter {
    @TypeConverter
    fun toDbValue(data: TagsItem? = null): String? = data?.toJson()
    @TypeConverter
    fun fromDbToValue(data: String? = null): TagsItem? = data.fromJson(TagsItem::class.java)
}

class PreviewPhotosItemConverter {
    @TypeConverter
    fun toDbValue(data: PreviewPhotosItem? = null): String? = data?.toJson()
    @TypeConverter
    fun fromDbToValue(data: String? = null): PreviewPhotosItem? = data.fromJson(PreviewPhotosItem::class.java)
}