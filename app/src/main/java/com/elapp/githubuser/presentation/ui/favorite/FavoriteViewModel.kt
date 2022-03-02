package com.elapp.githubuser.presentation.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elapp.githubuser.data.entity.Favorite
import com.elapp.githubuser.data.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
): ViewModel() {

    fun getAllFavorites(): LiveData<List<Favorite>> {
        val favorites = MutableLiveData<List<Favorite>>()
        viewModelScope.launch(Dispatchers.IO) {
            favorites.postValue(favoriteRepository.getAllFavorites())
        }
        return favorites
    }

}