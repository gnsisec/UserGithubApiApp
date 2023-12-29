package com.example.restaurantreview.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantreview.data.response.ItemsItem
import com.example.restaurantreview.databinding.FragmentFollowStatsBinding
import com.example.restaurantreview.ui.adapter.SearchResultAdapter
import com.example.restaurantreview.viewmodel.ProfileViewModel


class FollowStatsFragment : Fragment() {

    private val profileViewModel by viewModels<ProfileViewModel>()
    private var _binding: FragmentFollowStatsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "FollowStatsFragment"
        var ARG_POSITION = "position"
        var ARG_USERNAME = "username"
    }

    private var position: Int = 0
    private var username: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFollowStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.listFollower.observe(viewLifecycleOwner) {
            displayFollowList(it)
        }

        profileViewModel.listFollowing.observe(viewLifecycleOwner) {
            displayFollowList(it)
        }

        profileViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()!!
        }

        if (position == 1) {
            profileViewModel.showFollowers(username!!)
            // TODO: remove log nya sebelum submit
            Log.d(TAG, "showFollowers with $username")
        } else {
            profileViewModel.showFollowing(username!!)
            // TODO: remove log nya sebelum submit
            Log.d(TAG, "showFollowing with $username")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun displayFollowList(followList: List<ItemsItem>) {
        binding.listFollow.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = SearchResultAdapter()
        adapter.submitList(followList)
        binding.listFollow.adapter = adapter
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}