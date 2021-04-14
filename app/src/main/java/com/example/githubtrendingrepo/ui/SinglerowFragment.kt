package com.example.githubtrendingrepo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubtrendingrepo.R
import com.example.githubtrendingrepo.databinding.FragmentSingleRowBinding
import com.example.githubtrendingrepo.databinding.SingleRowBinding
import com.example.githubtrendingrepo.domain.RepoProperty
import com.example.githubtrendingrepo.viewmodels.SinglerowViewModel

class SinglerowFragment : Fragment() {

    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onActivityCreated, which we
     * do in this Fragment.
     */
    private val viewModel: SinglerowViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, SinglerowViewModel.Factory(activity.application))
            .get(SinglerowViewModel::class.java)
    }

    /**
     * RecyclerView Adapter for converting a list of repos to cards.
     */
    private var viewModelAdapter: GithubAdapter? = null

    /**
     * Called immediately after onCreateView() has returned, and fragment's
     * view hierarchy has been created. It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.repolist.observe(viewLifecycleOwner, Observer<List<RepoProperty>> { repos ->
            repos?.apply {
                viewModelAdapter?.repos = repos
            }
        })
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentSingleRowBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_single_row,
            container,
            false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.setLifecycleOwner(viewLifecycleOwner)

        binding.viewModel = viewModel


        binding.root.findViewById<RecyclerView>(R.id.rclview).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }


        // Observer for the network error.
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        return binding.root
    }

    /**
     * Method for displaying a Toast error message for network errors.
     */
    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

}


/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 */
class GithubAdapter() : RecyclerView.Adapter<SinglerowViewHolder>() {

    /**
     * The repos that our Adapter will show
     */
    var repos: List<RepoProperty> = emptyList()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SinglerowViewHolder {
        val withDataBinding: SingleRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            SinglerowViewHolder.LAYOUT,
            parent,
            false)
        return SinglerowViewHolder(withDataBinding)
    }

    override fun getItemCount() = repos.size

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: SinglerowViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.repo = repos[position]
        }
    }

}

/**
 * ViewHolder for DevByte items. All work is done by data binding.
 */
class SinglerowViewHolder(val viewDataBinding: SingleRowBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.single_row
    }
}