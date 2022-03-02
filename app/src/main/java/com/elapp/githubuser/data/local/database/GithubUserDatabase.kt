package com.elapp.githubuser.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elapp.githubuser.data.entity.Favorite
import com.elapp.githubuser.data.local.dao.FavoriteDao

@Database(
    entities = [Favorite::class],
    version = 1,
    exportSchema = false
)
abstract class GithubUserDatabase: RoomDatabase() {

    abstract fun getUserDao(): FavoriteDao

}