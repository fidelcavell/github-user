package id.project.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.project.githubuser.data.response.ItemsItem
import id.project.githubuser.databinding.ItemRowUserBinding

class ListGithubUserAdapter : ListAdapter<ItemsItem, ListGithubUserAdapter.ListViewHolder>(
    DIFF_CALLBACK
) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ListViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gitUser: ItemsItem) {
            Glide.with(binding.profilePictureUser.context)
                .load(gitUser.avatarUrl)
                .circleCrop()
                .into(binding.profilePictureUser)
            binding.userName.text = gitUser.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val githubUser = getItem(position)
        holder.bind(githubUser)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(githubUser.login)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(username: String)
    }
}