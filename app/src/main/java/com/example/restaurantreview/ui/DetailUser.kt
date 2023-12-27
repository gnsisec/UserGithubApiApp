package com.example.restaurantreview.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantreview.databinding.ActivityDetailUserBinding
import com.example.restaurantreview.viewmodel.ProfileViewModel

class DetailUser : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val profileViewModel by viewModels<ProfileViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: This should be DRY. Create a helper / util for this
        profileViewModel.isLoading.observe(this) {
            showLoading(it)
        }


    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}