package id.project.githubuser.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import id.project.githubuser.R
import id.project.githubuser.adapter.SectionPagerAdapter
import id.project.githubuser.data.response.DetailGithubUserResponse
import id.project.githubuser.databinding.ActivityDetailBinding
import id.project.githubuser.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        val githubUsername = intent.getStringExtra(DETAIL_GITHUB_USER)
        detailViewModel.getGithubUserDetail(githubUsername.toString())

        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.githubUsername = githubUsername.toString()
        binding.viewPager.adapter = sectionPagerAdapter

        TabLayoutMediator(binding.tabIndicator, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.githubUserDetail.observe(this) { detailGithubUser ->
            setDetailGithubUser(detailGithubUser)
        }

        detailViewModel.isLoadingDetail.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setDetailGithubUser(githubUser: DetailGithubUserResponse) {
        Glide.with(binding.detailProfilePicture.context)
            .load(githubUser.avatarUrl)
            .circleCrop()
            .into(binding.detailProfilePicture)
        binding.detailUsername.text = githubUser.login
        binding.detailName.text = githubUser.name
        binding.detailFollowers.text = resources.getString(R.string.followers, githubUser.followers)
        binding.detailFollowing.text = resources.getString(R.string.following, githubUser.following)
    }

    companion object {
        const val DETAIL_GITHUB_USER = "detail_github_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_name_1,
            R.string.tab_name_2
        )
    }
}