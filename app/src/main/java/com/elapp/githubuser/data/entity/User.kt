package com.elapp.githubuser.data.entity

import com.google.gson.annotations.SerializedName

data class User(
    val login: String,
    @field: SerializedName("avatar_url")
    val avatarUrl: String,
    val name: String,
    val company: String?,
    val location: String?,
    val followers: Int,
    val following: Int,
    @field: SerializedName("public_repos")
    val publicRepos: Int
)
