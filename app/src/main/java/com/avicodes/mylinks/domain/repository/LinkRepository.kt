package com.avicodes.mylinks.domain.repository

import com.avicodes.mylinks.data.models.ApiResponse
import com.avicodes.mylinks.data.utils.Result
import com.bumptech.glide.load.engine.Resource
import kotlinx.coroutines.flow.Flow

interface LinkRepository {
    fun getLinkData(): Flow<Result<ApiResponse>>
}