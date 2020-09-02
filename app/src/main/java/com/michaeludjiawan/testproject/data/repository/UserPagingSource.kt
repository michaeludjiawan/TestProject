package com.michaeludjiawan.testproject.data.repository

import androidx.paging.PagingSource
import com.michaeludjiawan.testproject.data.Result
import com.michaeludjiawan.testproject.data.api.ApiService
import com.michaeludjiawan.testproject.data.model.User
import com.michaeludjiawan.testproject.data.safeApiCall
import retrofit2.HttpException
import java.io.IOException

class UserPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, User>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val position = params.key ?: 1
        return try {
            val response = safeApiCall { apiService.getUsers(position) }
            if (response is Result.Success) {
                val users = response.data?.data.orEmpty()
                LoadResult.Page(
                    data = users,
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = if (users.isEmpty()) null else position + 1
                )
            } else {
                return LoadResult.Error(Exception())
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}