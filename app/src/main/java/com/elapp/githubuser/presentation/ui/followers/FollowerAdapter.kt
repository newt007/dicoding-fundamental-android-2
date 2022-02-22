package com.elapp.githubuser.presentation.ui.followers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elapp.githubuser.data.entity.User
import com.elapp.githubuser.databinding.UserItemRowBinding

class FollowerAdapter(private val userList: List<User>) :

    RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerAdapter.ViewHolder {
        val binding = UserItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowerAdapter.ViewHolder, position: Int) {
        userList[position].let { user ->
            holder.bind(user)
        }
    }

    override fun getItemCount(): Int = userList.size

    class ViewHolder(private val binding: UserItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                txUsername.text = user.login
                Glide.with(imgAvatar.context)
                    .load(user.avatarUrl)
                    .override(50, 50)
                    .into(imgAvatar)
            }
        }
    }

}