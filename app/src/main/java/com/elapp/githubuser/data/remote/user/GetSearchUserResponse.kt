package com.elapp.githubuser.data.remote.user

import com.elapp.githubuser.data.entity.User
import com.google.gson.annotations.SerializedName

data class GetSearchUserResponse(
    @field:SerializedName("total_count")
    val totalCount: Int,
    val items: List<User>
)
