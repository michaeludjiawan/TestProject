package com.michaeludjiawan.testproject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeys(
    @PrimaryKey val userId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)