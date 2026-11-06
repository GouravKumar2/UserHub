package com.example.userhub.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userhub.data.model.User
import com.example.userhub.data.repository.UserRepository
import com.example.userhub.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersViewModel : ViewModel() {

    private val repository = UserRepository()

    private val _usersState =
        MutableStateFlow<UiState<List<User>>>(UiState.Loading)

    val usersState: StateFlow<UiState<List<User>>> = _usersState

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _usersState.value = UiState.Loading

            try {
                    val users = withContext(Dispatchers.IO) {
                        repository.getUsers()
                    }

                _usersState.value = UiState.Success(users)

            } catch (e: CancellationException) {
                throw e

            } catch (e: IOException) {
                _usersState.value =
                    UiState.Error("Network error. Check your internet connection.")

            } catch (e: HttpException) {
                _usersState.value =
                    UiState.Error("Server error: ${e.code()}")

            } catch (e: Exception) {
                _usersState.value =
                    UiState.Error("Something went wrong: ${e.message}")
            }
        }
    }
}