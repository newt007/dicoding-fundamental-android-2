package com.elapp.githubuser.data.remote.user

import com.elapp.githubuser.data.entity.User
import com.elapp.githubuser.data.remote.user.GetSearchUserResponse
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

}