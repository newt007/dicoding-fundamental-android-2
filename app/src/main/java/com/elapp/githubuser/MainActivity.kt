package com.elapp.githubuser

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.elapp.githubuser.data.entity.User
import com.elapp.githubuser.data.remote.ApiResponse
import com.elapp.githubuser.databinding.ActivityMainBinding
import com.elapp.githubuser.presentation.ui.detail.UserDetailActivity
import com.elapp.githubuser.presentation.ui.user.UserAdapter
import com.elapp.githubuser.presentation.ui.user.UserViewModel
import com.elapp.githubuser.presentation.ui.user.listener.UserItemListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UserItemListener {

    private val userViewModel: UserViewModel by viewModels()

    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding!!

    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_activityMainBinding?.root)

        setupRv()

        binding.svUser.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                getSearchUser(query.toString())

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun setupRv() {
        binding.rvUser.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun getSearchUser(q: String) {
        userViewModel.getSearchUser(q).observe(this) { response ->
            when(response) {
                is ApiResponse.Loading -> {
                    isLoading(true)
                }
                is ApiResponse.Success -> {
                    isLoading(false)
                    userAdapter = UserAdapter(response.data.items)
                    userAdapter.onItemClicked(this)
                    binding.rvUser.adapter = userAdapter
                }
                is ApiResponse.Empty -> {
                    isLoading(false)
                    val emptyList = emptyList<User>()
                    userAdapter = UserAdapter(emptyList)
                    binding.rvUser.adapter = userAdapter
                }
                else -> {
                    isLoading(false)
                    Log.d("search_user", "Error unknown")
                }
            }
        }
    }

    private fun isLoading(loading: Boolean) {
        if (loading) {
            binding.shimmerLoading.visibility = View.VISIBLE
            binding.rvUser.visibility = View.INVISIBLE
        } else {
            binding.rvUser.visibility = View.VISIBLE
            binding.shimmerLoading.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }

    override fun onClicked(user: User) {
        val intent = Intent(this, UserDetailActivity::class.java)
        val loginUser = user.login
        intent.putExtra("user_login", loginUser)
        userViewModel.username.postValue(loginUser)
        Log.d("user_detail", userViewModel.username.value.toString())
        startActivity(intent)
    }

}