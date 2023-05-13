package com.avicodes.mylinks.data.utils

import java.text.SimpleDateFormat

object Constants {
    const val BASE_URL = "https://api.inopenapp.com/api/v1/"
    const val PREFS_TOKEN_FILE = "PREFS_TOKEN_FILE"
    const val USER_TOKEN = "USER_TOKEN"
    const val LINK_ENDPOINT = "dashboardNew"

    val remoteDateFormatter = SimpleDateFormat("yyyy-MM-dd")
    val localDateFormatter = SimpleDateFormat("dd/MM")
}