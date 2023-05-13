package com.avicodes.mylinks.data.utils

import android.util.Log
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil {
    fun convertTimestampToReadableDate(timestamp: String): String {
        val df1: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val result: Date? = df1.parse(timestamp)
        val requiredFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return requiredFormat.format(result)
    }


    fun getChartDateRange(first: String, sec: String): String {
        val df1: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val res1: Date? = df1.parse(first)
        val res2: Date? = df1.parse(sec)
        val requiredFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
        return requiredFormat.format(res1) + " - " + requiredFormat.format(res2)
    }
}