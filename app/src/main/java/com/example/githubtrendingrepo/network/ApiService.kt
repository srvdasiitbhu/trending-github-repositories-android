package com.example.githubtrendingrepo.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://private-anon-ca53c3f1c7-githubtrendingapi.apiary-mock.com/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getProperties] method
 */
interface ApiService {
    /**
     * Returns a Coroutine [List] of [RepoProperty] which can be fetched in a Coroutine scope.
     * The @GET annotation indicates that the "repositories" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("repositories")
    suspend fun getProperties(): List<RepoProperty>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object Api {
    val retrofitService : ApiService by lazy { retrofit.create(ApiService::class.java) }
}
