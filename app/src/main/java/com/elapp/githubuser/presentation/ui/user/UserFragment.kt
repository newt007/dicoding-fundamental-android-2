package com.elapp.githubuser.presentation.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.elapp.githubuser.data.entity.User
import com.elapp.githubuser.data.remote.ApiResponse
import com.elapp.githubuser.databinding.FragmentUserBinding
import com.elapp.githubuser.presentation.ui.user.listener.UserItemListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment(), UserItemListener {

    private val userViewModel: UserViewModel by viewModels()

    private var _userFragmentBinding: FragmentUserBinding? = null
    private val binding get() = _userFragmentBinding!!

    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _userFragmentBinding = FragmentUserBinding.inflate(inflater)
        return _userFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.svUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchUser(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun searchUser(query: String) {
        userViewModel.getSearchUser(query).observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    isLoading(true)
                }
                is ApiResponse.Success -> {
                    isLoading(false)
                    adapter = UserAdapter(response.data.items)
                    adapter.onItemClicked(this)
                    binding.rvUser.adapter = adapter
                    binding.rvUser.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }
                is ApiResponse.Empty -> {
                    isLoading(false)
                    val list = emptyList<User>()
                    adapter = UserAdapter(list)
                    binding.rvUser.adapter = adapter
                    Log.d("search_user", "Empty user....")
                }
                else -> {
                    isLoading(false)
                    Log.d("search_user", "unknown error")
                }
            }
        }
    }

    private fun isLoading(loading: Boolean) {
        if (loading) {
            binding.shimmerLoading.visibility = View.VISIBLE
            binding.shimmerLoading.startShimmer()
            binding.rvUser.visibility = View.INVISIBLE
        } else {
            binding.shimmerLoading.visibility = View.GONE
            binding.rvUser.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _userFragmentBinding = null
    }

    override fun onClicked(user: User) {
        val action = UserFragmentDirections.actionUserFragmentToUserDetailFragment(user.login)
        findNavController().navigate(action)
    }

}