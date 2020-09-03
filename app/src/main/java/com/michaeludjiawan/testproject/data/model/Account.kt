package com.michaeludjiawan.testproject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(
    val firstName: String,
    val lastName: String,
    val email: String
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}