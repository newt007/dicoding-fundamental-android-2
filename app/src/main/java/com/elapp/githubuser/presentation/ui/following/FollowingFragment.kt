package com.elapp.githubuser.presentation.ui.following

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.elapp.githubuser.data.entity.User
import com.elapp.githubuser.data.remote.ApiResponse
import com.elapp.githubuser.databinding.FragmentFollowingBinding
import com.elapp.githubuser.presentation.ui.detail.UserDetailActivity
import com.elapp.githubuser.presentation.ui.user.UserAdapter
import com.elapp.githubuser.presentation.ui.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowingFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModels()

    private var _fragmentFollowingBinding: FragmentFollowingBinding? = null
    private val binding get() = _fragmentFollowingBinding!!

    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentFollowingBinding = FragmentFollowingBinding.inflate(inflater)
        return _fragmentFollowingBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFollowing.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val username = (activity as UserDetailActivity).username
        getFollowingUser(username)
    }

    private fun getFollowingUser(login: String) {
        userViewModel.getFollowing(login).observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    isLoading(true)
                }
                is ApiResponse.Success -> {
                    isLoading(false)
                    userAdapter = UserAdapter(response.data)
                    binding.rvFollowing.adapter = userAdapter
                }
                is ApiResponse.Empty -> {
                    isLoading(false)
                    val list = emptyList<User>()
                    userAdapter = UserAdapter(list)
                    binding.rvFollowing.adapter = userAdapter
                }
                else -> {
                    isLoading(false)
                    Log.d("follower_user", "Unkown error")
                }
            }
        }
    }

    private fun isLoading(loading: Boolean) {
        if (loading) {
            binding.shimmerLoading.visibility = View.VISIBLE
        } else {
            binding.shimmerLoading.visibility = View.INVISIBLE
        }
    }

}