package com.elapp.githubuser.presentation.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elapp.githubuser.R
import com.elapp.githubuser.data.entity.Favorite
import com.elapp.githubuser.databinding.UserItemRowBinding
import com.elapp.githubuser.presentation.ui.favorite.listener.FavoriteItemListener

class FavoriteAdapter(private val favoriteList: List<Favorite>): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private lateinit var favoriteItemListener: FavoriteItemListener

    fun onItemClicked(mListener: FavoriteItemListener) {
        this.favoriteItemListener = mListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val binding = UserItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        favoriteList[position].let { favorite ->
            holder.bind(favorite)
            holder.itemView.setOnClickListener {
                favoriteItemListener.onItemClicked(favorite)
            }
        }
    }

    override fun getItemCount(): Int = favoriteList.size

    inner class ViewHolder(private val binding: UserItemRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            with(binding) {
                txUsername.text = favorite.login
                Glide.with(imgAvatar.context)
                    .load(favorite.avatarUrl)
                    .error(R.color.shimmer_color)
                    .override(50, 50)
                    .placeholder(R.color.shimmer_color)
                    .into(imgAvatar)
            }
        }
    }

}