package com.elapp.githubuser.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_favorite")
data class Favorite(
    @ColumnInfo(name = "login")
    @PrimaryKey
    val login: String,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "company")
    val company: String?,

    @ColumnInfo(name = "location")
    val location: String?,

    @ColumnInfo(name = "followers")
    val followers: Int,

    @ColumnInfo(name = "following")
    val following: Int,

    @ColumnInfo(name = "public_repos")
    val publicRepos: Int
)
