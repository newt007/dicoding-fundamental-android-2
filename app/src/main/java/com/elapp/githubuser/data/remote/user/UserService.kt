package com.elapp.githubuser.data.remote.user

import com.elapp.githubuser.data.entity.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("search/users")
    suspend fun getSearchUsers(
        @Query("q") query: String
    ) : GetSearchUserResponse

    @GET("users/{login}")
    suspend fun getDetailUser(
        @Path("login") login: String
    ) : User

    @GET("users/{login}/followers")
    suspend fun getFollowers(
        @Path("login") login: String,
    ) : List<User>

    @GET("users/{login}/following")
    suspend fun getFollowing(
        @Path("login") login: String
    ) : List<User>

}