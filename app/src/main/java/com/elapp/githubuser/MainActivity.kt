package com.elapp.githubuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elapp.githubuser.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_activityMainBinding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }

}