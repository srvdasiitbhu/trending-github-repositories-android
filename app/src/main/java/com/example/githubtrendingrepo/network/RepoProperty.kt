package com.example.githubtrendingrepo.network

import com.example.githubtrendingrepo.database.DatabaseRepo
import com.squareup.moshi.Json
import com.example.githubtrendingrepo.domain.RepoProperty
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkRepoContainer(val repos: List<NetworkRepo>)

@JsonClass(generateAdapter = true)
data class NetworkRepo(
    val author: String,
    // used to map img_src from the JSON to imgSrcUrl in our class
    @Json(name = "avatar") val imgSrcUrl: String,
    val language: String,
    val description: String)

fun NetworkRepoContainer.asDomainModel(): List<RepoProperty> {
    return repos.map {
        RepoProperty(
            author = it.author,
            avatar = it.imgSrcUrl,
            description = it.description,
            language = it.language)
    }
}

fun NetworkRepoContainer.asDatabaseModel(): List<DatabaseRepo> {
    return repos.map {
        DatabaseRepo(
            author = it.author,
            avatar = it.imgSrcUrl,
            description = it.description,
            language = it.language)
    }
}

