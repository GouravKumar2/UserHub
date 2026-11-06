package com.example.userhub.data.api

import com.example.userhub.data.model.User
import com.example.userhub.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") userId: Int
    ): User

    @GET("users/{id}/posts")
    suspend fun getUserPosts(
        @Path("id") userId: Int
    ): List<Post>
}