package com.elapp.githubuser.data.remote.user

import com.elapp.githubuser.data.entity.User
import retrofit2.http.*

interface UserService {

    @GET("search/users")
    @Headers("Authorization: token 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")
    suspend fun getSearchUsers(
        @Query("q") query: String
    ) : GetSearchUserResponse

    @GET("users/{login}")
    @Headers("Authorization: token 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")
    suspend fun getDetailUser(
        @Path("login") login: String
    ) : User

    @GET("users/{login}/followers")
    @Headers("Authorization: token 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")
    suspend fun getFollowers(
        @Path("login") login: String,
    ) : List<User>

    @GET("users/{login}/following")
    @Headers("Authorization: token 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")
    suspend fun getFollowing(
        @Path("login") login: String
    ) : List<User>

}