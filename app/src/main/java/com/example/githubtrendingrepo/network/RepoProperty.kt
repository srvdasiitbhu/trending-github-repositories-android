package com.example.githubtrendingrepo.network

import com.squareup.moshi.Json


data class RepoProperty(
    val author: String,
    // used to map img_src from the JSON to imgSrcUrl in our class
    @Json(name = "avatar") val imgSrcUrl: String,
    val language: String,
    val description: String)