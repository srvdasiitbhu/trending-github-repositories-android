package com.example.githubtrendingrepo.singlerow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubtrendingrepo.network.Api
import com.example.githubtrendingrepo.network.RepoProperty
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }

/**
 * The [ViewModel] that is attached to the [SinglerowFragment].
 */
class SinglerowViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus>
        get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of RepoProperty
    // with new values
    private val _properties = MutableLiveData<List<RepoProperty>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val properties: LiveData<List<RepoProperty>>
        get() = _properties

    /**
     * Call getTrendingGithubRepos() on init so we can display status immediately.
     */
    init {
        getTrendingGithubRepos()
    }

    /**
     * Gets trending github repo information from the API Retrofit service and updates the
     * [RepoProperty] [List] [LiveData]. The Retrofit service returns a coroutine Deferred, which we
     * await to get the result of the transaction.
     */
    private fun getTrendingGithubRepos() {

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _properties.value = Api.retrofitService.getProperties()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }
}
