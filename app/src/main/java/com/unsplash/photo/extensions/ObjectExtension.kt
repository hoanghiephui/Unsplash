package com.unsplash.photo.extensions

import android.graphics.Color
import android.text.format.DateUtils
import androidx.annotation.ColorInt
import java.text.SimpleDateFormat
import java.util.*

fun Any.getSimpleName(): String = this::class.java.simpleName

fun Boolean.isTrue(body: (() -> Unit)?): Boolean {
    if (this) body?.invoke()
    return this
}

fun Boolean.isFalse(body: (() -> Unit)?): Boolean {
    if (!this) body?.invoke()
    return this
}

fun Date.timeAgo(): CharSequence {
    val now = System.currentTimeMillis()
    return DateUtils.getRelativeTimeSpanString(this.time, now, DateUtils.SECOND_IN_MILLIS)
}

fun String.replaceAllNewLines(prefix: String = " "): String {
    return this.replace("\\r?\\n|\\r".toRegex(), prefix)
}

fun Long.formatNumber(): String {
    if (this < 999) return this.toString()
    val count = this.toDouble()
    val exp = (Math.log(count) / Math.log(1000.0)).toInt()
    return String.format("%.1f%c", count / Math.pow(1000.0, exp.toDouble()), "kMGTPE"[exp - 1])
}

fun Int.formatNumber(): String {
    if (this < 999) return this.toString()
    val count = this.toDouble()
    val exp = (Math.log(count) / Math.log(1000.0)).toInt()
    return String.format("%.1f%c", count / Math.pow(1000.0, exp.toDouble()), "kMGTPE"[exp - 1])
}

fun getDateByDays(days: Int): String {
    val cal = Calendar.getInstance()
    val s = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
    cal.add(Calendar.DAY_OF_YEAR, days)
    return s.format(Date(cal.timeInMillis))
}

fun getLastWeekDate(): String {
    return getDateByDays(-7)
}

@ColorInt
fun Int.generateTextColor(): Int {
    val a = 1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
    return if (a < 0.5) Color.BLACK else Color.WHITE
}