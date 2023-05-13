package com.avicodes.mylinks.data.api

import com.avicodes.mylinks.data.prefs.TokenPrefs
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiInterceptor (private val tokenPrefs: TokenPrefs) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val token = tokenPrefs.getToken()
        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }
}