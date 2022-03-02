package com.elapp.githubuser.presentation.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.elapp.githubuser.R
import com.elapp.githubuser.data.entity.Favorite
import com.elapp.githubuser.databinding.ActivityFavoriteBinding
import com.elapp.githubuser.presentation.ui.detail.UserDetailActivity
import com.elapp.githubuser.presentation.ui.favorite.listener.FavoriteItemListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity(), FavoriteItemListener {

    private val favoriteViewModel: FavoriteViewModel by viewModels()

    private var _activityFavorite: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavorite!!

    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavorite = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(_activityFavorite?.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = resources.getString(R.string.favorite_user)

        setupRv()
        getAllFavorites()
    }

    private fun setupRv() {
        binding.rvFavorites.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun getAllFavorites() {
        favoriteViewModel.getAllFavorites().observe(this) {
            favoriteAdapter = FavoriteAdapter(it)
            favoriteAdapter.onItemClicked(this)
            binding.rvFavorites.apply {
                adapter = favoriteAdapter
            }
            listIsEmpty(it)
        }
    }

    private fun listIsEmpty(list: List<Favorite>) {
        if (list.isEmpty()) {
            binding.txEmpty.visibility = View.VISIBLE
        } else {
            binding.txEmpty.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        getAllFavorites()
    }

    override fun onItemClicked(favorite: Favorite) {
        val intentToDetail = Intent(this, UserDetailActivity::class.java)
        intentToDetail.putExtra("user_login", favorite.login)
        startActivity(intentToDetail)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}