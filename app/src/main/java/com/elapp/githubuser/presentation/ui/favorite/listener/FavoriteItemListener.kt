package com.elapp.githubuser.presentation.ui.favorite.listener

import com.elapp.githubuser.data.entity.Favorite

interface FavoriteItemListener {

    fun onItemClicked(favorite: Favorite)

}