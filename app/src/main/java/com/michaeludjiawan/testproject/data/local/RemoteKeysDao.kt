package com.michaeludjiawan.testproject.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.michaeludjiawan.testproject.data.model.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM RemoteKeys WHERE userId = :userId")
    suspend fun getRemoteKeysUserById(userId: Int): RemoteKeys?

    @Query("DELETE FROM RemoteKeys")
    suspend fun clearRemoteKeys()
}