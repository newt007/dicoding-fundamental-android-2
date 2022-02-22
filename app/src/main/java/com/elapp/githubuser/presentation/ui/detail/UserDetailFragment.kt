package com.elapp.githubuser.presentation.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.elapp.githubuser.R
import com.elapp.githubuser.data.remote.ApiResponse
import com.elapp.githubuser.databinding.FragmentDetailUserBinding
import com.elapp.githubuser.presentation.ui.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModels()

    private var _fragmentUserDetailBinding: FragmentDetailUserBinding? = null
    private val binding get() = _fragmentUserDetailBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentUserDetailBinding = FragmentDetailUserBinding.inflate(inflater)
        return _fragmentUserDetailBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val userLogin = arguments?.getString("user_login")
        getUserDetail(userLogin!!)
    }

    private fun getUserDetail(login: String) {
        userViewModel.getDetailUser(login).observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    Log.d("detail_user", "Detail loading....")
                }
                is ApiResponse.Success -> {
                    val user = response.data
                    with(binding) {
                        txFullName.text = user.name
                        txUsername.text = user.company
                        txFollowingCount.text = user.following.toString()
                        txFollowerCount.text = user.followers.toString()
                        txRepositoryCount.text = user.publicRepos.toString()
                        txLocation.text = user.location ?: "--"
                        txCompany.text = user.company ?: "--"

                        Glide.with(requireContext())
                            .load(user.avatarUrl)
                            .override(100, 100)
                            .placeholder(R.drawable.bg_img_placeholder)
                            .error(R.drawable.bg_img_placeholder)
                            .into(imgAvatar)
                    }
                }
                else -> {
                    Log.d("detail_user", "Error unknown")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentUserDetailBinding = null
    }

}