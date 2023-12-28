package com.example.restaurantreview.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantreview.data.response.ItemsItem
import com.example.restaurantreview.databinding.ActivityDetailUserBinding
import com.example.restaurantreview.databinding.FragmentFollowStatsBinding
import com.example.restaurantreview.viewmodel.ProfileViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowStatsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowStatsFragment : Fragment() {

    private val profileViewModel by viewModels<ProfileViewModel>()
    private var _binding: FragmentFollowStatsBinding? = null
    private val binding get() = _binding!!
    companion object {
        var ARG_POSITION = "position"
        var ARG_USERNAME = "username"
    }

    private var position : Int = 0
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFollowStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listFollow.layoutManager = LinearLayoutManager(requireActivity())

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
            username = it.getString(ARG_USERNAME).toString()
        }

        if (position == 1){
            profileViewModel.showFollowers(username!!)
        } else {
            profileViewModel.showFollowing(username!!)
        }
    }

    private fun displayFollowList(followList: List<ItemsItem>) {
        val adapter = SearchResultAdapter()
        adapter.submitList(followList)
        binding.listFollow.adapter = adapter
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}