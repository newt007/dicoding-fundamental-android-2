package com.elapp.githubuser.presentation.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.elapp.githubuser.R
import com.elapp.githubuser.data.entity.User
import com.elapp.githubuser.data.remote.ApiResponse
import com.elapp.githubuser.databinding.ActivityUserDetailBinding
import com.elapp.githubuser.presentation.ui.user.UserViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()

    private var _activityUserDetailBinding: ActivityUserDetailBinding? = null
    private val binding get() = _activityUserDetailBinding!!

    lateinit var username: String

    private lateinit var user: User

    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityUserDetailBinding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(_activityUserDetailBinding?.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = resources.getString(R.string.detail_user)

        username = intent.getStringExtra("user_login").toString()

        getUserDetail(username)
        setupTabLayout()
        isExist(username)

        binding.btnFavorite.setOnClickListener {
            favoriteAction(user)
        }
    }

    private fun setupTabLayout() {
        val adapter = SectionPageAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun favoriteAction(user: User) {
        if (isFavorite) {
            AlertDialog.Builder(this).apply {
                setTitle("Delete Favorite")
                setMessage("Are you sure to delete this user from favorite ?")
                setNegativeButton("Cancel") { p0, _ ->
                    p0.dismiss()
                }
                setPositiveButton("Delete") { _, _ ->
                    deleteFavorite(user.login)
                    isFavorite = false
                    binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border)
                }
            }.create().show()
        } else {
            addFavorite(user)
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
            isFavorite = true
        }
    }

    private fun isExist(login: String) {
        userViewModel.isUserExist(login).observe(this) {
            isFavorite = it
            if (it) {
                binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }

    private fun deleteFavorite(login: String) {
        userViewModel.deleteFavorite(login)
        Toast.makeText(this, "Success deleting user from favorite", Toast.LENGTH_SHORT).show()
    }

    private fun addFavorite(user: User) {
        userViewModel.addFavorite(user)
        Toast.makeText(this, "Success adding user to favorite", Toast.LENGTH_SHORT).show()
    }

    private fun getUserDetail(login: String) {
        userViewModel.getDetailUser(login).observe(this) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    isLoading(true)
                    Log.d("detail_user", "Detail loading....")
                }
                is ApiResponse.Success -> {
                    isLoading(false)
                    user = response.data
                    with(binding) {
                        txFullName.text = user.name
                        txUsername.text = user.login
                        txFollowingCount.text = user.following.toString()
                        txFollowerCount.text = user.followers.toString()
                        txRepositoryCount.text = user.publicRepos.toString()
                        txLocation.text = user.location ?: "--"
                        txCompany.text = user.company ?: "--"

                        Glide.with(imgAvatar.context)
                            .load(user.avatarUrl)
                            .override(100, 100)
                            .placeholder(R.drawable.bg_img_placeholder)
                            .error(R.drawable.bg_img_placeholder)
                            .into(imgAvatar)
                    }
                }
                is ApiResponse.Error -> {
                    isLoading(false)
                    Toast.makeText(this, response.errorMessage, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Log.d("detail_user", "Error unknown")
                }
            }
        }
    }

    private fun isLoading(loading: Boolean) {
        if (loading) {
            binding.apply {
                shimmerLoading.visibility = View.VISIBLE
                txFollowerCount.visibility = View.INVISIBLE
                txFollowerTitle.visibility = View.INVISIBLE
                txFollowingCount.visibility = View.INVISIBLE
                txFollowingTitle.visibility = View.INVISIBLE
                txCompany.visibility = View.INVISIBLE
                txLocation.visibility = View.INVISIBLE
                txRepositoryCount.visibility = View.INVISIBLE
                txRepositoryTitle.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                shimmerLoading.visibility = View.GONE
                txFollowerCount.visibility = View.VISIBLE
                txFollowerTitle.visibility = View.VISIBLE
                txFollowingCount.visibility = View.VISIBLE
                txFollowingTitle.visibility = View.VISIBLE
                txCompany.visibility = View.VISIBLE
                txLocation.visibility = View.VISIBLE
                txRepositoryCount.visibility = View.VISIBLE
                txRepositoryTitle.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityUserDetailBinding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following,
            R.string.followers
        )
    }

}