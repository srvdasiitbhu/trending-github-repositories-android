package com.example.githubtrendingrepo.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * A retrofit service to fetch a repolist.
 */
interface RepoService {
    @GET("repositories")
    suspend fun getRepolist(): NetworkRepoContainer
}

/**
 * Main entry point for network access. Call like `DevByteNetwork.devbytes.getPlaylist()`
 */
object RepoNetwork {

    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://private-anon-ca53c3f1c7-githubtrendingapi.apiary-mock.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val githubrepo = retrofit.create(RepoService::class.java)

}

