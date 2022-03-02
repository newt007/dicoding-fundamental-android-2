package com.elapp.githubuser.presentation.ui.followers

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
import com.elapp.githubuser.databinding.FragmentFollowersBinding
import com.elapp.githubuser.presentation.ui.detail.UserDetailActivity
import com.elapp.githubuser.presentation.ui.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowerFragment : Fragment() {

    private var _fragmentFollowerBinding: FragmentFollowersBinding? = null
    private val binding get() = _fragmentFollowerBinding!!

    private val userViewModel: UserViewModel by viewModels()

    private lateinit var followerAdapter: FollowerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentFollowerBinding = FragmentFollowersBinding.inflate(inflater)
        return _fragmentFollowerBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFollower.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val username = (activity as UserDetailActivity).username
        getFollower(username)
    }

    private fun getFollower(login: String) {
        userViewModel.getFollowers(login).observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    isLoading(true)
                    Log.d("follower_user", "Loading....")
                }
                is ApiResponse.Success -> {
                    isLoading(false)
                    isEmpty(false)
                    followerAdapter = FollowerAdapter(response.data)
                    binding.rvFollower.adapter = followerAdapter
                }
                is ApiResponse.Empty -> {
                    isLoading(false)
                    isEmpty(true)
                    val list = emptyList<User>()
                    followerAdapter = FollowerAdapter(list)
                    binding.rvFollower.adapter = followerAdapter
                }
                else -> {
                    isLoading(false)
                    Log.d("follower_user", "Unknown error")
                }
            }
        }
    }

    private fun isEmpty(empty: Boolean) {
        if (empty) {
            binding.txEmpty.visibility = View.VISIBLE
        } else {
            binding.txEmpty.visibility = View.GONE
        }
    }

    private fun isLoading(loading: Boolean) {
        if (loading) {
            binding.shimmerLoading.visibility = View.VISIBLE
        } else {
            binding.shimmerLoading.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentFollowerBinding = null
    }

}