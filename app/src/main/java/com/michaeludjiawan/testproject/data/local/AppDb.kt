package com.michaeludjiawan.testproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.michaeludjiawan.testproject.data.model.RemoteKeys
import com.michaeludjiawan.testproject.data.model.User

@Database(
    entities = [User::class, RemoteKeys::class],
    version = 1
)
abstract class AppDb : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}