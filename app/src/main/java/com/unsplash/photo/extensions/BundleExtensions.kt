/*
 * Created by Hoang Hiep on 9/14/18 9:00 AM
 * Copyright (c) 2018 INVOVN SOLUTIONS. All rights reserved.
 * Last modified 9/12/18 9:39 AM
 */

package com.unsplash.photo.extensions

import android.os.Bundle
import android.os.Parcelable


inline fun Bundle(copyFrom: Bundle? = null, action: Bundle.() -> Unit): Bundle {
    val bundle = Bundle()
    if (copyFrom != null) {
        bundle.putAll(copyFrom)
    }
    action(bundle)
    return bundle
}

operator fun Bundle.set(key: String, value: Boolean) {
    return putBoolean(key, value)
}

operator fun Bundle.set(key: String, value: Int) {
    return putInt(key, value)
}

operator fun Bundle.set(key: String, value: Long) {
    return putLong(key, value)
}

operator fun Bundle.set(key: String, value: String?) {
    return putString(key, value)
}

operator fun Bundle.set(key: String, value: IntArray?) {
    return putIntArray(key, value)
}

operator fun Bundle.set(key: String, value: Parcelable?) {
    return putParcelable(key, value)
}

operator fun Bundle.set(key: String, value: Array<out Parcelable>?) {
    return putParcelableArray(key, value)
}

operator fun Bundle.set(key: String, value: Array<String>?) {
    return putStringArray(key, value)
}

inline fun <reified T : Parcelable> Bundle.getTypedArray(key: String): Array<T> {
    val parcelable = getParcelableArray(key)
    return Array(parcelable.size) { parcelable[it] as T }
}

inline fun <reified T : Parcelable> Bundle.getNullableTypedArray(key: String): Array<T>? {
    val parcelable = getParcelableArray(key) ?: return null
    return Array(parcelable.size) { parcelable[it] as T }
}

@JvmName("putIntegerArrayList")
operator fun Bundle.set(key: String, value: ArrayList<Int>): ArrayList<Int> = value.apply { putIntegerArrayList(key, value) }

@JvmName("putStringArrayList")
operator fun Bundle.set(key: String, value: ArrayList<String>): ArrayList<String> = value.apply { putStringArrayList(key, value) }

@JvmName("putCharSequenceArrayList")
operator fun Bundle.set(key: String, value: ArrayList<CharSequence>): ArrayList<CharSequence> = value.apply { putCharSequenceArrayList(key, value) }

@JvmName("putParcelableArrayList")
operator fun Bundle.set(key: String, value: ArrayList<Parcelable>): ArrayList<Parcelable> = value.apply { putParcelableArrayList(key, value) }