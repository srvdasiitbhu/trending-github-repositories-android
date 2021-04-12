package com.example.githubtrendingrepo.singlerow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubtrendingrepo.databinding.SingleRowBinding
import com.example.githubtrendingrepo.network.RepoProperty

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class CardAdapter : ListAdapter<RepoProperty, CardAdapter.RepoPropertyViewHolder>(DiffCallback) {

    /**
     * The RepoPropertyViewHolder constructor takes the binding variable from the associated
     * CardViewItem, which nicely gives it access to the full [RepoProperty] information.
     */
    class RepoPropertyViewHolder(private var binding: SingleRowBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repoProperty: RepoProperty) {
            binding.property = repoProperty
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [RepoProperty]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<RepoProperty>() {
        override fun areItemsTheSame(oldItem: RepoProperty, newItem: RepoProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: RepoProperty, newItem: RepoProperty): Boolean {
            return oldItem.author == newItem.author
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RepoPropertyViewHolder {
        return RepoPropertyViewHolder(SingleRowBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: RepoPropertyViewHolder, position: Int) {
        val repoProperty = getItem(position)
        holder.bind(repoProperty)
    }
}
