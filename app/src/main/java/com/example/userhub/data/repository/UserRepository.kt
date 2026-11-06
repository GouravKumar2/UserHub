package com.example.userhub.data.repository

import com.example.userhub.data.api.RetrofitInstance
import com.example.userhub.data.model.User
import com.example.userhub.data.model.Post

class UserRepository {

    private val api = RetrofitInstance.api

    suspend fun getUsers(): List<User> {
        return api.getUsers()
    }

    suspend fun getUserById(id: Int): User {
        return api.getUserById(id)
    }

    suspend fun getUserPosts(id: Int): List<Post> {
        return api.getUserPosts(id)
    }
}