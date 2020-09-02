package com.michaeludjiawan.testproject.data.api

import com.google.gson.annotations.SerializedName

data class ListResponse<T>(
    @SerializedName("page") val page: Int,
    @SerializedName("per_page") val pageSize: Int,
    @SerializedName("total") val dataTotal: Int,
    @SerializedName("total_pages") val pageTotal: Int,
    @SerializedName("data") val data: List<T>
)