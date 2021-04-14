package com.example.githubtrendingrepo.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubtrendingrepo.domain.RepoProperty

/**
 * Database entities go in this file. These are responsible for reading and writing from the
 * database.
 */
@Entity
data class DatabaseRepo constructor(
        @PrimaryKey
        val author: String,
        val avatar: String,
        val description: String,
        val language: String)


/**
 * Map DatabaseRepos to domain entities
 */
fun List<DatabaseRepo>.asDomainModel(): List<RepoProperty> {
    return map {
        RepoProperty(
                author = it.author,
                avatar = it.avatar,
                description = it.description,
                language = it.language
        )
    }
}

