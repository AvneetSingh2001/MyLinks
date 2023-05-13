package com.avicodes.mylinks.data.repository

import com.avicodes.mylinks.data.api.ApiService
import com.avicodes.mylinks.data.models.ApiResponse
import com.avicodes.mylinks.data.utils.Constants.LINK_ENDPOINT
import com.avicodes.mylinks.data.utils.Result
import com.avicodes.mylinks.domain.repository.LinkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class LinkRepositoryImpl(private val apiService: ApiService) : LinkRepository {

    override fun getLinkData() = flow<Result<ApiResponse>> {
        emit(Result.Loading("Fetching"))
        try {
            val response = apiService.getData(LINK_ENDPOINT)
            if (response.isSuccessful) {
                emit(Result.Success(response.body()))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

}