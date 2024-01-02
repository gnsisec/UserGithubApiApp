package com.example.restaurantreview.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantreview.data.response.UserAttributes
import com.example.restaurantreview.databinding.FragmentFollowBinding
import com.example.restaurantreview.ui.main.SearchResultAdapter


class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private var position: Int = 0
    private var username = ""

    companion object {
        var ARG_POSITION = "position"
        var ARG_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }

        val followViewModelFactory = FollowViewModelFactory(username)
        val followViewModel =
            ViewModelProvider(this, followViewModelFactory)[FollowViewModel::class.java]

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        if (position == 1) {
            followViewModel.listFollower.observe(viewLifecycleOwner) {
                showListFollow(it)
            }
        } else {
            followViewModel.listFollowing.observe(viewLifecycleOwner) {
                showListFollow(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showListFollow(followList: List<UserAttributes>) {
        val adapter = SearchResultAdapter()
        adapter.submitList(followList)
        binding.listFollow.layoutManager = LinearLayoutManager(requireActivity())
        binding.listFollow.adapter = adapter
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}