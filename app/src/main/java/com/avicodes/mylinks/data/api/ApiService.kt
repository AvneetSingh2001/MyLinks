package com.avicodes.mylinks.data.api

import com.avicodes.mylinks.BuildConfig
import com.avicodes.mylinks.data.models.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {
    @GET("{endpoint}")
    suspend fun getData(
        @Path("endpoint") endpoint: String,
    ): Response<ApiResponse>
}




