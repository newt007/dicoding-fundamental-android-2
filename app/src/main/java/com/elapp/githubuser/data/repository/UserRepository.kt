package com.elapp.githubuser.data.repository

import com.elapp.githubuser.data.entity.User
import com.elapp.githubuser.data.remote.ApiResponse
import com.elapp.githubuser.data.remote.user.GetSearchUserResponse
import com.elapp.githubuser.data.source.UserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val userDataSource: UserDataSource)  {

    suspend fun getSearchUser(query: String): Flow<ApiResponse<GetSearchUserResponse>> {
        return userDataSource.getSearchUsers(query).flowOn(Dispatchers.IO)
    }

    suspend fun getDetailUser(login: String): Flow<ApiResponse<User>> {
        return userDataSource.getDetailUser(login).flowOn(Dispatchers.IO)
    }

}