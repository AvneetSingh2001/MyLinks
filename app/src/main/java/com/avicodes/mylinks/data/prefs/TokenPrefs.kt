package com.avicodes.mylinks.data.prefs

import android.content.Context
import com.avicodes.mylinks.data.utils.Constants.PREFS_TOKEN_FILE
import com.avicodes.mylinks.data.utils.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext

class TokenPrefs (context: Context) {

    private var prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        saveToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI")
        return prefs.getString(USER_TOKEN, null)
    }
}