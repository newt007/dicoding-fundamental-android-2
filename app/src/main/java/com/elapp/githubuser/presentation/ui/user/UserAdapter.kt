package com.elapp.githubuser.presentation.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elapp.githubuser.R
import com.elapp.githubuser.data.entity.User
import com.elapp.githubuser.databinding.UserItemRowBinding
import com.elapp.githubuser.presentation.ui.user.listener.UserItemListener

class UserAdapter(private val userList: List<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private lateinit var userItemListener: UserItemListener

    fun onItemClicked(mListener: UserItemListener) {
        this.userItemListener = mListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val binding = UserItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        userList[position].let { user ->
            holder.bind(user)
            holder.itemView.setOnClickListener {
                userItemListener.onClicked(user)
            }
        }
    }

    override fun getItemCount(): Int = userList.size

    class ViewHolder(private val binding: UserItemRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (user: User) {
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