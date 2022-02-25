package com.elapp.githubuser.presentation.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elapp.githubuser.data.entity.User
import com.elapp.githubuser.data.remote.ApiResponse
import com.elapp.githubuser.data.remote.user.GetSearchUserResponse
import com.elapp.githubuser.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    fun getSearchUser(query: String): LiveData<ApiResponse<GetSearchUserResponse>> {
        val result = MutableLiveData<ApiResponse<GetSearchUserResponse>>()
        viewModelScope.launch {
            userRepository.getSearchUser(query).collect {
                result.postValue(it)
            }
        }
        return result
    }

    fun getDetailUser(login: String): LiveData<ApiResponse<User>> {
        val result = MutableLiveData<ApiResponse<User>>()
        viewModelScope.launch {
            userRepository.getDetailUser(login).collect {
                result.postValue(it)
            }
        }
        return result
    }

    fun getFollowers(login: String): LiveData<ApiResponse<List<User>>> {
        val result = MutableLiveData<ApiResponse<List<User>>>()
        viewModelScope.launch {
            userRepository.getFollowers(login).collect {
                result.postValue(it)
            }
        }
        return result
    }

    fun getFollowing(login: String): LiveData<ApiResponse<List<User>>> {
        val result = MutableLiveData<ApiResponse<List<User>>>()
        viewModelScope.launch {
            userRepository.getFollowing(login).collect {
                result.postValue(it)
            }
        }
        return result
    }

}