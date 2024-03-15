package id.project.githubuser.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.project.githubuser.adapter.ListGithubUserAdapter
import id.project.githubuser.data.response.ItemsItem
import id.project.githubuser.databinding.ActivityMainBinding
import id.project.githubuser.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvGithubUser.layoutManager = layoutManager

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    mainViewModel.getListGithubUser(searchBar.text.toString())
                    false
                }
        }

        mainViewModel.listGithubUser.observe(this) { listGithubUser ->
            if (listGithubUser.isEmpty()) {
                binding.errorMessage.visibility = View.VISIBLE
                binding.rvGithubUser.visibility = View.GONE
            } else {
                binding.errorMessage.visibility = View.GONE
                binding.rvGithubUser.visibility = View.VISIBLE
                setListGithubUser(listGithubUser)
            }
        }

        mainViewModel.isLoading.observe(this) {
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

    private fun setListGithubUser(listGithubUser: List<ItemsItem>) {
        val adapter = ListGithubUserAdapter()
        adapter.submitList(listGithubUser)
        binding.rvGithubUser.adapter = adapter
        adapter.setOnItemClickback(object : ListGithubUserAdapter.OnItemClickCallback {
            override fun onItemClicked(username: String) {
                val intentToDetailActivity = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetailActivity.putExtra(DetailActivity.DETAIL_GITHUB_USER, username)
                startActivity(intentToDetailActivity)
            }
        })
    }
}