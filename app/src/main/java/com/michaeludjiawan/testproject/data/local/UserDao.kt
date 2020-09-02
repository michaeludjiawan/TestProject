package com.michaeludjiawan.testproject.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.michaeludjiawan.testproject.data.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Query("SELECT * FROM User")
    fun getUsers(): PagingSource<Int, User>

    @Query("DELETE FROM User")
    suspend fun clearUsers()
}