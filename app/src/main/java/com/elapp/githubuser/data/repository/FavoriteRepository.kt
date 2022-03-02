package com.elapp.githubuser.data.repository

import com.elapp.githubuser.data.entity.Favorite
import com.elapp.githubuser.data.entity.User
import com.elapp.githubuser.data.source.FavoriteDataSource
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val favoriteDataSource: FavoriteDataSource) {

    suspend fun getAllFavorites(): List<Favorite> = favoriteDataSource.getAllFavorites()

    suspend fun addFavorites(user: User) {
        favoriteDataSource.addFavorites(user)
    }

    suspend fun deleteFavorites(username: String) {
        favoriteDataSource.deleteFavorite(username)
    }

    suspend fun isUserExist(login: String): Boolean = favoriteDataSource.isUserExist(login)

}