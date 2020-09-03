package com.michaeludjiawan.testproject.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.michaeludjiawan.testproject.data.api.ApiService
import com.michaeludjiawan.testproject.data.local.AppDb
import com.michaeludjiawan.testproject.data.model.RemoteKeys
import com.michaeludjiawan.testproject.data.model.User
import retrofit2.HttpException
import java.io.IOException

const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val apiService: ApiService,
    private val appDb: AppDb
) : RemoteMediator<Int, User>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, User>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                if (remoteKeys == null) {
                    STARTING_PAGE_INDEX
                } else {
                    remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                    remoteKeys.prevKey
                }
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys?.nextKey == null) {
                    STARTING_PAGE_INDEX
                }
                remoteKeys?.nextKey ?: STARTING_PAGE_INDEX
            }
        }

        try {
            val apiResponse = apiService.getUsers(page)

            val users = apiResponse.data
            val endOfPaginationReached = users.isEmpty()
            appDb.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    appDb.remoteKeysDao().clearRemoteKeys()
                    appDb.userDao().clearUsers()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = users.map {
                    RemoteKeys(userId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                appDb.remoteKeysDao().insertAll(keys)
                appDb.userDao().insertAll(users)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, User>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { user ->
                appDb.remoteKeysDao().getRemoteKeysUserById(user.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, User>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { user ->
                appDb.remoteKeysDao().getRemoteKeysUserById(user.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, User>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { userId ->
                appDb.remoteKeysDao().getRemoteKeysUserById(userId)
            }
        }
    }
}