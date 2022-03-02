package com.elapp.githubuser.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elapp.githubuser.data.entity.Favorite

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteUser(favorite: Favorite)

    @Query("SELECT * from tbl_favorite")
    suspend fun getAllFavorites(): List<Favorite>

    @Query("SELECT EXISTS(SELECT * FROM tbl_favorite WHERE login = :username)")
    suspend fun isUserExist(username: String): Boolean

    @Query("DELETE FROM tbl_favorite WHERE login = :username ")
    suspend fun removeFavorite(username: String)

}