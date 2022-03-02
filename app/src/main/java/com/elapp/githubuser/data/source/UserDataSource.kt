package com.elapp.githubuser.data.source

import android.util.Log
import com.elapp.githubuser.data.entity.User
import com.elapp.githubuser.data.remote.ApiResponse
import com.elapp.githubuser.data.remote.user.GetSearchUserResponse
import com.elapp.githubuser.data.remote.user.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataSource @Inject constructor(
    private val userService: UserService
) {

    suspend fun getSearchUsers(query: String): Flow<ApiResponse<GetSearchUserResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = userService.getSearchUsers(query)
                if (response.items.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                    Log.d("user_search", "Search user success")
                } else {
                    emit(ApiResponse.Empty)
                    Log.d("user_search", "Search user empty")
                }
            } catch (ex: Exception) {
                emit(ApiResponse.Error(ex.toString()))
            }
        }
    }

    suspend fun getDetailUser(login: String): Flow<ApiResponse<User>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = userService.getDetailUser(login)
                emit(ApiResponse.Success(response))
            } catch (ex: Exception) {
                emit(ApiResponse.Error(ex.toString()))
            }
        }
    }

    suspend fun getFollowers(login: String): Flow<ApiResponse<List<User>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = userService.getFollowers(login)
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (ex: Exception) {
                emit(ApiResponse.Error(ex.toString()))
            }
        }
    }

    suspend fun getFollowing(login: String): Flow<ApiResponse<List<User>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = userService.getFollowing(login)
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (ex: Exception) {
                emit(ApiResponse.Error(ex.toString()))
            }
        }
    }

}