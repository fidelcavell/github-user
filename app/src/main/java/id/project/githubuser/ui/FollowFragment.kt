package id.project.githubuser.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.project.githubuser.adapter.ListGithubUserAdapter
import id.project.githubuser.data.response.ItemsItem
import id.project.githubuser.databinding.FragmentFollowBinding
import id.project.githubuser.viewmodel.ListFollowViewModel

class FollowFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME)
        val position = arguments?.getInt(ARG_POSITION)

        val listFollowViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        )[ListFollowViewModel::class.java]

        if (position == 0) {
            listFollowViewModel.getGithubUserListFollower(username.toString())
            listFollowViewModel.githubUserListFollower.observe(viewLifecycleOwner) { listFollower ->
                setListFollowGithubUser(listFollower)
            }
        } else {
            listFollowViewModel.getGithubUserListFollowing(username.toString())
            listFollowViewModel.githubUserListFollowing.observe(viewLifecycleOwner) { listFollowing ->
                setListFollowGithubUser(listFollowing)
            }
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvListFollow.layoutManager = layoutManager

        listFollowViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setListFollowGithubUser(listFollowGithubUser: List<ItemsItem>) {
        val adapter = ListGithubUserAdapter()
        adapter.submitList(listFollowGithubUser)
        binding.rvListFollow.adapter = adapter
        adapter.setOnItemClickback(object : ListGithubUserAdapter.OnItemClickCallback {
            override fun onItemClicked(username: String) {
                val intentToDetailActivity = Intent(requireActivity(), DetailActivity::class.java)
                intentToDetailActivity.putExtra(DetailActivity.DETAIL_GITHUB_USER, username)
                startActivity(intentToDetailActivity)
            }
        })
    }
}