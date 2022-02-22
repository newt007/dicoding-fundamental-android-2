package com.elapp.githubuser.presentation.ui.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elapp.githubuser.databinding.FragmentFollowersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowerFragment: Fragment() {

    private var _fragmentFollowerBinding: FragmentFollowersBinding? = null
    private val binding get() = _fragmentFollowerBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentFollowerBinding = FragmentFollowersBinding.inflate(inflater)
        return _fragmentFollowerBinding?.root
    }



}