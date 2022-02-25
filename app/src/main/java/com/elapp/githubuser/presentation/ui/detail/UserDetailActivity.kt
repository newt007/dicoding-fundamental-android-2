package com.elapp.githubuser.presentation.ui.detail

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.elapp.githubuser.R
import com.elapp.githubuser.data.remote.ApiResponse
import com.elapp.githubuser.databinding.ActivityUserDetailBinding
import com.elapp.githubuser.presentation.ui.user.UserViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following,
            R.string.followers
        )
    }

    private val userViewModel: UserViewModel by viewModels()

    private var _activityUserDetailBinding: ActivityUserDetailBinding? = null
    private val binding get() = _activityUserDetailBinding!!

    lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityUserDetailBinding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(_activityUserDetailBinding?.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Detail User"

        username = intent.getStringExtra("user_login").toString()

        getUserDetail(username)
        setupTabLayout()
    }

    private fun setupTabLayout() {
        val adapter = SectionPageAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun getUserDetail(login: String) {
        userViewModel.getDetailUser(login).observe(this) { response ->
            when(response) {
                is ApiResponse.Loading -> {
                    Log.d("detail_user", "Detail loading....")
                }
                is ApiResponse.Success -> {
                    val user = response.data
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
                    Toast.makeText(this, response.errorMessage, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Log.d("detail_user", "Error unknown")
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}