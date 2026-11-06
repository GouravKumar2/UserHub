package com.example.userhub.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userhub.data.model.User
import com.example.userhub.data.model.Post
import com.example.userhub.data.repository.UserRepository
import com.example.userhub.ui.common.UiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class UserDetailData(
    val user: User,
    val posts: List<Post>
)

class UserDetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val repository = UserRepository()

    private val userId: Int = checkNotNull(savedStateHandle["userId"])

    private val _detailState =
        MutableStateFlow<UiState<UserDetailData>>(UiState.Loading)

    val detailState: StateFlow<UiState<UserDetailData>> = _detailState

    init {
        loadUserDetails()
    }

    fun loadUserDetails() {
        viewModelScope.launch {
            _detailState.value = UiState.Loading

            try {
                val user = repository.getUserById(userId)
                val posts = repository.getUserPosts(userId)

                _detailState.value = UiState.Success(
                    UserDetailData(user, posts)
                )

            } catch (e: CancellationException) {
                throw e

            } catch (e: IOException) {
                _detailState.value =
                    UiState.Error("Network error. Check your connection.")

            } catch (e: HttpException) {
                _detailState.value =
                    UiState.Error("Server error: ${e.code()}")

            } catch (e: Exception) {
                _detailState.value =
                    UiState.Error("Something went wrong: ${e.message}")
            }
        }
    }
}