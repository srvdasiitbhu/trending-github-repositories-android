package com.example.githubtrendingrepo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.githubtrendingrepo.database.ReposDatabase
import com.example.githubtrendingrepo.database.asDomainModel
import com.example.githubtrendingrepo.domain.RepoProperty
import com.example.githubtrendingrepo.network.RepoNetwork
import com.example.githubtrendingrepo.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Repository for fetching github trending repos from the network and storing them on disk
 */
class GithubRepository(private val database: ReposDatabase) {

    val repos: LiveData<List<RepoProperty>> = Transformations.map(database.repoDao.getRepos()) {
        it.asDomainModel()
    }

    /**
     * Refresh the data stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     */
    suspend fun refreshRepos() {
        withContext(Dispatchers.IO) {
            Timber.d("refresh repos is called");
            val repolist = RepoNetwork.githubrepo.getRepolist()
            database.repoDao.insertAll(repolist.asDatabaseModel())
        }
    }

}
