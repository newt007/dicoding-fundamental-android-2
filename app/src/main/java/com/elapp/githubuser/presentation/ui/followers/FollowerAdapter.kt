package com.elapp.githubuser.presentation.ui.followers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elapp.githubuser.R
import com.elapp.githubuser.data.entity.User
import com.elapp.githubuser.databinding.UserItemRowBinding

class FollowerAdapter(private val followerList: List<User>) :

    RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerAdapter.ViewHolder {
        val binding = UserItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowerAdapter.ViewHolder, position: Int) {
        followerList[position].let { user ->
            holder.bind(user)
        }
    }

    override fun getItemCount(): Int = followerList.size

    inner class ViewHolder(private val binding: UserItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                txUsername.text = user.login
                Glide.with(imgAvatar.context)
                    .load(user.avatarUrl)
                    .error(R.color.shimmer_color)
                    .override(50, 50)
                    .placeholder(R.color.shimmer_color)
                    .into(imgAvatar)
            }
        }
    }

}